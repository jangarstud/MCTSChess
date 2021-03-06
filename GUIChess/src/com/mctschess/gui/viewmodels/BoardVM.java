package com.mctschess.gui.viewmodels;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import com.mctschess.gui.panels.PiecePromotionDialog;
import com.mctschess.gui.wsclient.WSClient;
import com.mctschess.model.board.Board;
import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.location.Move;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;
import com.mctschess.model.pieces.Piece;
import com.mctschess.model.pieces.PieceFactory;

public class BoardVM {

	private PropertyChangeSupport pCS = new PropertyChangeSupport(this);
	
	private WSClient wsClient = new WSClient();

	private static final Map<Piece, String> IMAGES_FOR_PIECE;
	private SquareState squares[] = new SquareState[64];
	private IBoard currentBoard;
	
	private PieceColor aiColor = null;
	
	private long whiteTime;
	private long blackTime;

	static {
		IMAGES_FOR_PIECE = new HashMap<Piece, String>();
		IMAGES_FOR_PIECE.put(PieceFactory.getBishop(PieceColor.WHITE), "white_bishop");
		IMAGES_FOR_PIECE.put(PieceFactory.getBishop(PieceColor.BLACK), "black_bishop");
		IMAGES_FOR_PIECE.put(PieceFactory.getKnight(PieceColor.WHITE), "white_knight");
		IMAGES_FOR_PIECE.put(PieceFactory.getKnight(PieceColor.BLACK), "black_knight");
		IMAGES_FOR_PIECE.put(PieceFactory.getRook(PieceColor.WHITE), "white_rook");
		IMAGES_FOR_PIECE.put(PieceFactory.getRook(PieceColor.BLACK), "black_rook");
		IMAGES_FOR_PIECE.put(PieceFactory.getPawn(PieceColor.WHITE), "white_pawn");
		IMAGES_FOR_PIECE.put(PieceFactory.getPawn(PieceColor.BLACK), "black_pawn");
		IMAGES_FOR_PIECE.put(PieceFactory.getKing(PieceColor.WHITE), "white_king");
		IMAGES_FOR_PIECE.put(PieceFactory.getKing(PieceColor.BLACK), "black_king");
		IMAGES_FOR_PIECE.put(PieceFactory.getQueen(PieceColor.WHITE), "white_queen");
		IMAGES_FOR_PIECE.put(PieceFactory.getQueen(PieceColor.BLACK), "black_queen");
	}

	public BoardVM() {
		for (int i = 0; i < squares.length; i++) {
			squares[i] = new SquareState();
		}
		reset(PieceColor.WHITE, true);
		
		new Timer().schedule( new TimerTask() {
			
			@Override
			public void run() {
				calculateNewTime();				
			}
		}, 1000, 1000);
	}

	public void reset(PieceColor playerColor, boolean aiPlaying) {
		currentBoard = Board.createOnInitState();
		whiteTime = 0;
		blackTime = 0;
		updateSquares();
		if (aiPlaying) {
			
			aiColor = playerColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
		}
		else aiColor = null;

		if (aiColor == PieceColor.WHITE) 
			wsClient.play(currentBoard)
				.thenAccept(this::makeAIMove);
	}

	private Location createLocationFromIndex(int index) {
		return new Location(File.values()[index % 8], 8 - index / 8);
	}

	private int calculateIndexFromLocation(Location l) {
		return (8 - l.getRank()) * 8 + l.getFile().ordinal();
	}

	private void updateSquares() {
		for (int i = 0; i < squares.length; i++) {
			Location l = createLocationFromIndex(i);
			Piece piece = currentBoard.getPieceAt(l);
			SquareState old = squares[i];
			squares[i] = squares[i].changeImageName(getImageFromPiece(piece)).changeSelected(false)
					.changePossibleMove(false);
			pCS.fireIndexedPropertyChange("squares", i, old, squares[i]);
		}
	}

	private String getImageFromPiece(Piece piece) {
		if (piece == null)
			return "empty";
		return IMAGES_FOR_PIECE.get(piece);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		pCS.addPropertyChangeListener(l);
	}

	public void unselect() {
		for (int i = 0; i < squares.length; i++) {
			if (squares[i].isSelected()) {
				SquareState old = squares[i];
				squares[i] = squares[i].changeSelected(false);
				pCS.fireIndexedPropertyChange("squares", i, old, squares[i]);
			}
			if (squares[i].isMovePossible()) {
				SquareState old = squares[i];
				squares[i] = squares[i].changePossibleMove(false);
				pCS.fireIndexedPropertyChange("squares", i, old, squares[i]);
			}
		}
	}
	
	private boolean checkEndGame() {
		if (currentBoard.isOnDeadPosition()) {
			JOptionPane.showMessageDialog(null, "The match has finished with a draw (insufficient material - dead position)");
			return true;			
		}
		else if (currentBoard.getValidMoves().isEmpty()) {
			if (currentBoard.isKingOnCheck()) {
				if (currentBoard.getCurrentColor() == PieceColor.WHITE) {
					JOptionPane.showMessageDialog(null, "The match has finished. Black wins");
				}
				else {
					JOptionPane.showMessageDialog(null, "The match has finished. White wins");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "The match has finished with a draw (stalemate)");
			}
			return true;
		}
		else return false;
	}
	
	private void calculateNewTime() {
		if (currentBoard.getCurrentColor() == PieceColor.WHITE) {
			whiteTime ++;
			pCS.firePropertyChange("whiteTime", whiteTime - 1, whiteTime);
		}
		else {
			blackTime ++;
			pCS.firePropertyChange("blackTime", blackTime - 1, blackTime);
		}
	}
	
	private void makeUserMove(Move move) {
		calculateNewTime();
		
		currentBoard = currentBoard.applyMove(move);
		updateSquares();
		
		if (!checkEndGame()) {
			if (aiColor != null)
				wsClient.play(currentBoard)
					.thenAccept(this::makeAIMove);
		}	
	}
	
	private void makeAIMove(Move move) {
		if (move != null) {
			calculateNewTime();
			
			currentBoard = currentBoard.applyMove(move);
			updateSquares();
			checkEndGame();
		}
		else {
			JOptionPane.showMessageDialog(null, "Server error");
		}
	}
	

	public void clickOnSquare(int i) {
		
		if (aiColor == currentBoard.getCurrentColor())  return;

		int selectedIndex = -1;
		for (int j = 0; j < squares.length; j++) {
			if (squares[j].isSelected()) {
				selectedIndex = j;
			}
		}

		PieceColor currentColor = currentBoard.getCurrentColor();
		boolean isCurrentColorSelected = currentColor == squares[i].getColor();

		if (squares[i].possibleMove && selectedIndex != -1) {
			Piece promotionPiece = null;
			if (currentColor.equals(PieceColor.WHITE) && squares[selectedIndex].getImageName().contains("pawn")
					&& i / 8 == 0) {
				promotionPiece = showPromotionDialog(currentColor);
			} else if (currentColor.equals(PieceColor.BLACK) && squares[selectedIndex].getImageName().contains("pawn")
					&& i / 8 == 7) {
				promotionPiece = showPromotionDialog(currentColor);
			}
			makeUserMove(new Move(createLocationFromIndex(selectedIndex), createLocationFromIndex(i), promotionPiece));
		} else {
			if (!squares[i].isEmpty() && isCurrentColorSelected) {
				unselect();
				SquareState old = squares[i];
				squares[i] = squares[i].changeSelected(true);
				pCS.fireIndexedPropertyChange("squares", i, old, squares[i]);

				List<Move> moves = currentBoard.getValidMoves(createLocationFromIndex(i));
				for (Move move : moves) {
					int moveIndex = calculateIndexFromLocation(move.getTo());
					old = squares[moveIndex];
					squares[moveIndex] = squares[moveIndex].changePossibleMove(true);
					pCS.fireIndexedPropertyChange("squares", moveIndex, old, squares[moveIndex]);
				}
			}
		}
	}

	private Piece showPromotionDialog(PieceColor currentColor) {
		PiecePromotionDialog promotionDialog = new PiecePromotionDialog(currentColor.equals(PieceColor.BLACK));
		promotionDialog.setVisible(true);
		return promotionDialog.getSelectedPromotionPiece();
	}

	public SquareState getSquare(int index) {
		return squares[index];
	}

	public static class SquareState {

		private String imageName;
		private boolean possibleMove;
		private boolean selected;

		public SquareState() {
			this.imageName = null;
			this.possibleMove = false;
			this.selected = false;
		}

		public SquareState changeImageName(String image) {
			SquareState value = new SquareState();
			value.imageName = image;
			value.possibleMove = this.possibleMove;
			value.selected = this.selected;
			return value;
		}

		public SquareState changeSelected(boolean selected) {
			SquareState value = new SquareState();
			value.imageName = this.imageName;
			value.possibleMove = this.possibleMove;
			value.selected = selected;
			return value;
		}

		public SquareState changePossibleMove(boolean possible) {
			SquareState value = new SquareState();
			value.imageName = this.imageName;
			value.selected = this.selected;
			value.possibleMove = possible;
			return value;
		}

		public String getImageName() {
			return imageName;
		}

		public boolean isMovePossible() {
			return possibleMove;
		}

		public boolean isSelected() {
			return selected;
		}

		public boolean isEmpty() {
			return imageName == null;
		}

		public PieceColor getColor() {
			if (imageName.startsWith("white_")) {
				return PieceColor.WHITE;
			} else if (imageName.startsWith("black_")) {
				return PieceColor.BLACK;
			} else {
				return null;
			}
		}

	}

}
