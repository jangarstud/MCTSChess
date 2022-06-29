package com.mctschess.model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mctschess.dto.BoardDto;
import com.mctschess.dto.LocationAndPieceDto;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.location.LocationAndPiece;
import com.mctschess.model.location.Move;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;
import com.mctschess.model.pieces.AbstractPiece.PieceType;
import com.mctschess.model.pieces.Piece;
import com.mctschess.model.pieces.PieceFactory;

public class Board implements IBoard {
	public static final int BOARD_DIMENSION = 8;
		
	private final Map<Location, Piece> boardState = new HashMap<>();
	private final PieceColor currentColor;

	private final File enPassant;

	private final boolean whiteQueensideCastling; // Left
	private final boolean whiteKingsideCastling; // Right

	private final boolean blackQueensideCastling; // Left
	private final boolean blackKingsideCastling; // Right

	private boolean checkingIsOnCheck = false;

	private Board(PieceColor currentColor, File enPassant, boolean whiteQueensideCastling, boolean whiteKingsideCastling, boolean blackQueensideCastling, boolean blackKingsideCastling ) {
		this.currentColor = currentColor;
		this.enPassant = enPassant;
		this.whiteKingsideCastling = whiteKingsideCastling;
		this.whiteQueensideCastling = whiteQueensideCastling;
		this.blackKingsideCastling = blackKingsideCastling;
		this.blackQueensideCastling = blackQueensideCastling;
	}

	private void putPiecesInBoard(PieceColor pieceColor) {
		int startingRankPosition = pieceColor == PieceColor.WHITE ? 1 : 8;
		int pawnsRank = pieceColor == PieceColor.WHITE ? 2 : 7;

		boardState.put(new Location(File.A, startingRankPosition), PieceFactory.getRook(pieceColor));
		boardState.put(new Location(File.H, startingRankPosition), PieceFactory.getRook(pieceColor));

		boardState.put(new Location(File.B, startingRankPosition), PieceFactory.getKnight(pieceColor));
		boardState.put(new Location(File.G, startingRankPosition), PieceFactory.getKnight(pieceColor));

		boardState.put(new Location(File.C, startingRankPosition), PieceFactory.getBishop(pieceColor));
		boardState.put(new Location(File.F, startingRankPosition), PieceFactory.getBishop(pieceColor));

		boardState.put(new Location(File.D, startingRankPosition), PieceFactory.getQueen(pieceColor));

		boardState.put(new Location(File.E, startingRankPosition), PieceFactory.getKing(pieceColor));

		for (File d : File.values()) {
			boardState.put(new Location(d, pawnsRank), PieceFactory.getPawn(pieceColor));
		}

	}

	public static Board createOnInitState() {
		Board b = new Board(PieceColor.WHITE, null, true, true, true, true);

		b.putPiecesInBoard(PieceColor.WHITE);
		b.putPiecesInBoard(PieceColor.BLACK);

		return b;
	}
	
	public static Board createBoardCase(LocationAndPiece... locationAndPieces) {
		return createWithPieces(PieceColor.WHITE, null, false, false, false, false, locationAndPieces);
	}

	public static Board createWithPieces(LocationAndPiece... locationAndPieces) {
		return createWithPieces(PieceColor.WHITE, null, true, true, true, true, locationAndPieces);
	}
	
	public static Board createWithPieces(PieceColor currentColor, LocationAndPiece... locationAndPieces) {
		return createWithPieces(currentColor, null, true, true, true, true, locationAndPieces);
	}

	public static Board createWithPieces(File enPassant, LocationAndPiece... locationAndPieces) {
		return createWithPieces(PieceColor.WHITE, enPassant, true, true, true, true, locationAndPieces);
	}
	
	public static Board createWithPieces(File enPassant, PieceColor currentColor, LocationAndPiece... locationAndPieces) {
		return createWithPieces(currentColor, enPassant, true, true, true, true, locationAndPieces);
	}

	public static Board createWithPieces(PieceColor currentColor, File enPassant, boolean blackKingsideCastling,
			boolean blackQueensideCastling, boolean whiteKingsideCastling, boolean whiteQueensideCastling,
			LocationAndPiece... locationAndPieces) {
		Board b = new Board(currentColor, enPassant, whiteKingsideCastling, whiteQueensideCastling, blackKingsideCastling, blackQueensideCastling);

		for (LocationAndPiece piece : locationAndPieces) {
			b.boardState.put(piece.getLocation(), piece.getPiece());
		}

		return b;
	}

	public Piece getPieceAt(Location l) {
		return boardState.get(l);
	}

	public boolean isEmpty(Location l) {
		return !boardState.containsKey(l);
	}

	public boolean isEmptyOrCapturable(Location l, PieceColor pieceColor) {
		return !boardState.containsKey(l) || boardState.get(l).getColor() != pieceColor;
	}

	public boolean isOnCheck(Location location) {
		
		if (checkingIsOnCheck) return false;
		
		checkingIsOnCheck = true;
		List<Move> validMoves = new ArrayList<>();
		for (Map.Entry<Location, Piece> piece : boardState.entrySet()) {
			if (piece.getValue().getColor() != currentColor) {
				piece.getValue().getValidMoves(this, piece.getKey(), validMoves);
			}
		}
		checkingIsOnCheck = false;

		return validMoves.stream().anyMatch((move) -> move.getTo().equals(location));
	}

	private Location getCurrentKingLocation() {
		for (Map.Entry<Location, Piece> piece : boardState.entrySet()) {
			if (piece.getValue().getColor() == currentColor && piece.getValue().getType() == PieceType.KING) {
				return piece.getKey();
			}
		}
		throw new RuntimeException("King not found");
	}
	
	public boolean isKingOnCheck() {
		return isOnCheck(getCurrentKingLocation());
	}

	private boolean isMoveNotGeneratingOponentChecks(Move move) {
		
		Piece piece = boardState.get(move.getFrom());
		Piece capturedPiece = boardState.get(move.getTo());
		boardState.remove(move.getFrom());
		boardState.put(move.getTo(), piece);

		boolean check = isOnCheck(getCurrentKingLocation());

		boardState.put(move.getFrom(), piece);
		if (capturedPiece != null) {
			boardState.put(move.getTo(), capturedPiece);
		} else {
			boardState.remove(move.getTo());
		}

		return !check;
	}

	@Override
	public List<Move> getValidMoves() {
		
		List<Move> moves = new ArrayList<Move>();
		for (Map.Entry<Location, Piece> piece: boardState.entrySet()) {
			if (piece.getValue().getColor() == currentColor) {
				piece.getValue().getValidMoves(this, piece.getKey(), moves);
			}
		}

		moves = moves.stream().filter(this::isMoveNotGeneratingOponentChecks).toList();

		return moves;
	}

	@Override
	public List<Move> getValidMoves(Location l) {
		Piece p = boardState.get(l);
		List<Move> availableMoves = new ArrayList<Move>();
		if (p != null) {
			p.getValidMoves(this, l, availableMoves);
		}

		availableMoves = availableMoves.stream().filter(this::isMoveNotGeneratingOponentChecks).toList();

		return availableMoves;
	}
	
	
	
	private boolean isCapturedByMove(Move move, Location location, Piece piece) {
		
		if (piece.getColor() == currentColor) return false;
		
		if (location.equals(move.getTo())) return true;
		
		if (piece.getType() != PieceType.PAWN) return false;
		
		if (location.getFile() != enPassant) return false;
		
		if (move.getTo().getFile() != location.getFile()) return false;
		
		if (piece.getColor() == PieceColor.BLACK && move.getTo().getRank() == 6) return true;
		if (piece.getColor() == PieceColor.WHITE && move.getTo().getRank() == 3) return true;
		
		return false;		
	}
	
	public Location getNewLocation(Move move, Location location, Piece piece) {
		
		if (piece.getColor() != currentColor) return location;
		
		if (move.getFrom().equals(location)) return move.getTo();
		
		if (piece.getType() != PieceType.ROOK) return location;
		
		int castlingRank = currentColor == PieceColor.BLACK ? 8 : 1;
		if (location.getFile() == File.H && location.getRank() == castlingRank && move.isKingsideCastling(currentColor))
			return new Location(File.F, castlingRank);
		if (location.getFile() == File.A && location.getRank() == castlingRank && move.isQueensideCastling(currentColor))
			return new Location(File.D, castlingRank);
		return location;
	}
	
	public Piece getNewPiece(Move move, Location location, Piece piece) {
		if (piece.getType() == PieceType.PAWN && move.getFrom().equals(location)) {
			if (move.getTo().getRank() == 1 || move.getTo().getRank() == 8) {
				assert move.getPromotionPiece() != null : "No piece to promote is indicated";
				return move.getPromotionPiece();
			}			
			return piece;
		}
		else {
			return piece;
		}
	}

	@Override
	public IBoard applyMove(Move move) {
		Location from = move.getFrom();
		Location to = move.getTo();
		
		PieceColor newCurrentColor = currentColor == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK;
		
		
		File newEnPassant = null;
		if(getPieceAt(from).equals(PieceFactory.getPawn(currentColor)) && Math.abs(from.getRank() - to.getRank()) == 2) {
			newEnPassant = to.getFile();
		}
		
		boolean newBlackKingsideCastling = blackKingsideCastling;
		if (from.getFile() == File.H && from.getRank() == 8) newBlackKingsideCastling = false;
		
		boolean newBlackQueensideCastling = blackQueensideCastling;
		if (from.getFile() == File.A && from.getRank() == 8) newBlackQueensideCastling = false;
		
		if (from.getFile() == File.E && from.getRank() == 8) newBlackKingsideCastling = newBlackQueensideCastling = false;
		
		
		boolean newWhiteKingsideCastling = whiteKingsideCastling;
		if (from.getFile() == File.H && from.getRank() == 1) newWhiteKingsideCastling = false;
		
		boolean newWhiteQueensideCastling = whiteQueensideCastling;
		if (from.getFile() == File.A && from.getRank() == 1) newWhiteQueensideCastling = false;
		
		if (from.getFile() == File.E && from.getRank() == 1) newWhiteKingsideCastling = newWhiteQueensideCastling = false;
		
		
		List<LocationAndPiece> locations = new ArrayList<LocationAndPiece>();
		for(Map.Entry<Location, Piece> locationAndPiece : boardState.entrySet()) {
			Location location = getNewLocation(move,locationAndPiece.getKey(),locationAndPiece.getValue());
			Piece piece = getNewPiece(move,locationAndPiece.getKey(),locationAndPiece.getValue());
			if(!isCapturedByMove(move, location, piece)) {
				LocationAndPiece currentLocationAndPiece = new LocationAndPiece(location, piece);
				locations.add(currentLocationAndPiece);
			}
		}
		
		
		return Board.createWithPieces(newCurrentColor, newEnPassant, newBlackKingsideCastling, newBlackQueensideCastling, newWhiteKingsideCastling, newWhiteQueensideCastling, locations.toArray(new LocationAndPiece[locations.size()]));
	}
	
	@Override
	public boolean isEnPassantAllowed(File file) {
		return enPassant == file;
	}

	public boolean isCurrentQueensideCastling() {
		return currentColor == PieceColor.WHITE ? whiteQueensideCastling : blackQueensideCastling;
	}

	public boolean isCurrentKingsideCastling() {
		return currentColor == PieceColor.WHITE ? whiteKingsideCastling : blackKingsideCastling;
	}
	
	public PieceColor getCurrentColor() {
		return this.currentColor;
	}
	
	public int getAvailablePieces(){
		return boardState.size();
	}
	
	public boolean isOnDeadPosition() {
		//King versus king
		if(boardState.size() == 2) {
			return true;
		} else if(boardState.size() == 3) {
			//King and bishop versus king  || King and knight versus king
			if(boardContainsPiece(PieceType.BISHOP) || boardContainsPiece(PieceType.NKNIGHT))
				return true;
			
		} else if(boardState.size() == 4) {
			Location notWhiteKingLocation = findPieceLocation(PieceType.BISHOP, PieceColor.WHITE);
			Location notBlackKingLocation = findPieceLocation(PieceType.BISHOP, PieceColor.BLACK);
			
			//King and bishop versus king and bishop with the bishops on the same color
			if(notWhiteKingLocation != null && notBlackKingLocation != null) {
				if(notWhiteKingLocation.getSquareColor() == notBlackKingLocation.getSquareColor()) {
					return true;				
				}
				return false;
			}
		}
		return false;
	}
	
	private boolean boardContainsPiece(PieceType pieceType) {
		for(Piece piece : boardState.values()) {
			if(piece.getType().equals(pieceType))
				return true;
		}
		
		return false;
	}

	private Location findPieceLocation(PieceType pieceType, PieceColor color) {
		for(Map.Entry<Location, Piece> piece : boardState.entrySet()) {
			if(piece.getValue().getType() == pieceType && piece.getValue().getColor() == color)
				return piece.getKey();
		}
		
		return null;
	}
	
	
	@Override
	public String toString() {
		String boardString = "";
		
		for (int rank = BOARD_DIMENSION; rank >= 1; rank--) {
			boardString += rank + " ";
			for (File file : File.values()) {
				Location l = new Location(file, rank);
				Piece piece = getPieceAt(l);
				if (piece == null) {
					boardString += "- ";
				} else {
					if(piece.getColor() == PieceColor.WHITE) {
						boardString += String.valueOf(piece.getType().toString().charAt(0)).toUpperCase() + " ";						
					} else {						
						boardString += String.valueOf(piece.getType().toString().charAt(0)).toLowerCase() + " ";
					}
				}
			}
			boardString += "\n";
		}
		boardString += "  ";
		for (File file : File.values()) {
			boardString += file.name() + " ";
		}
		boardString += "\n";
		
		return boardString;
	}
	
	public BoardDto toDto() {
		List<LocationAndPieceDto> dtoBoardState = boardState.entrySet().stream()
				.map(entry -> new LocationAndPieceDto(entry.getKey().toDto(), entry.getValue().toDto()))
				.toList();
		
		return new BoardDto(dtoBoardState, currentColor, enPassant, whiteQueensideCastling, whiteKingsideCastling, blackQueensideCastling, blackKingsideCastling);		
	}
	
	public static Board fromDto(BoardDto dto) {
		
		LocationAndPiece [] boardState = dto.getBoardState().stream()
				.map(lp -> LocationAndPiece.fromDto(lp))
				.toArray((s) -> new LocationAndPiece[s]);
		
		return Board.createWithPieces(dto.getCurrentColor(), dto.getEnPassant(), dto.isBlackKingsideCastling(), dto.isBlackQueensideCastling(), dto.isWhiteKingsideCastling(), dto.isWhiteQueensideCastling(), boardState);
	}

}
