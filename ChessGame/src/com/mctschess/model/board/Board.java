package com.mctschess.model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//	private final Map<Location, Square> boardState;
	
	//	Square[][] boardSquares = new Square[BOARD_DIMENSION][BOARD_DIMENSION];
	
	private final Map<Location, Piece> boardState = new HashMap<>();
	private PieceColor currentColor;

	private File enPassant;

	private boolean whiteQueensideCastling; // Left
	private boolean whiteKingsideCastling; // Right

	private boolean blackQueensideCastling; // Left
	private boolean blackKingsideCastling; // Right

	private boolean checkingIsOnCheck = false;
	
	/*
	 * private final List<Piece> whitePieces = new ArrayList<Piece>();
	 * private final List<Piece> blackPieces = new ArrayList<Piece>();
	 */

	private Board() {
		/*
		 * boardState = new HashMap<Location, Square>();
		 * Map<Location, Piece> pieces = PieceFactory.initializer();
		 * for (int rank = 0; rank < boardSquares.length; rank++) { 
		 *		int column = 0;
		 *		SquareColor currentColor = (rank % 2 == 1) ? SquareColor.LIGHT : SquareColor.DARK;
		 *
		 * 		for (File file : File.values()) {
		 * 	  		Square newSquare = new Square(currentColor, new Location(file, rank));
		 * 		  	storeBlackAndWhitePieces(pieces, newSquare); 
		 * 			boardSquares[rank][column] = newSquare;
		 * 			currentColor = (currentColor == SquareColor.DARK) ? SquareColor.LIGHT : SquareColor.DARK;
		 * 			column++; 
		 * 		}
		 *  }
		 */
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
		Board b = new Board();
		b.currentColor = PieceColor.WHITE;

		b.putPiecesInBoard(PieceColor.WHITE);
		b.putPiecesInBoard(PieceColor.BLACK);

		b.enPassant = null;
		b.blackKingsideCastling = true;
		b.blackQueensideCastling = true;
		b.whiteKingsideCastling = true;
		b.whiteQueensideCastling = true;

		return b;
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
		Board b = new Board();

		for (LocationAndPiece piece : locationAndPieces) {
			b.boardState.put(piece.getLocation(), piece.getPiece());
		}

		b.currentColor = currentColor;
		b.blackKingsideCastling = blackKingsideCastling;
		b.blackQueensideCastling = blackQueensideCastling;
		b.whiteKingsideCastling = whiteKingsideCastling;
		b.whiteQueensideCastling = whiteQueensideCastling;
		b.enPassant = enPassant;

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

	
	/*
	  // Whites
	  
	  // rooks b.boardState.put(new Location(File.A, 1),
	  PieceFactory.getRook(PieceColor.WHITE)); 
	  b.boardState.put(new Location(File.H, 1), PieceFactory.getRook(PieceColor.WHITE));
	  
	  // knights b.boardState.put(new Location(File.B, 1),
	  PieceFactory.getKnight(PieceColor.WHITE)); 
	  b.boardState.put(new Location(File.G, 1), PieceFactory.getKnight(PieceColor.WHITE));
	  
	  // bishops b.boardState.put(new Location(File.C, 1),
	  PieceFactory.getBishop(PieceColor.WHITE)); b.boardState.put(new
	  Location(File.F, 1), PieceFactory.getBishop(PieceColor.WHITE));
	  
	  // queens b.boardState.put(new Location(File.D, 1),
	  PieceFactory.getQueen(PieceColor.WHITE));
	  
	  // kings b.boardState.put(new Location(File.E, 1),
	  PieceFactory.getKing(PieceColor.WHITE));
	  
	  // pawns b.boardState.put(new Location(File.A, 2),
	  PieceFactory.getPawn(PieceColor.WHITE));
	  b.boardState.put(new Location(File.B, 2), PieceFactory.getPawn(PieceColor.WHITE));
	  b.boardState.put(new Location(File.C, 2), PieceFactory.getPawn(PieceColor.WHITE));
	  b.boardState.put(new Location(File.D, 2), PieceFactory.getPawn(PieceColor.WHITE));
	  b.boardState.put(new Location(File.E, 2), PieceFactory.getPawn(PieceColor.WHITE)); 
	  b.boardState.put(new Location(File.F, 2), PieceFactory.getPawn(PieceColor.WHITE));
	  b.boardState.put(new Location(File.G, 2), PieceFactory.getPawn(PieceColor.WHITE));
	  b.boardState.put(new Location(File.H, 2), PieceFactory.getPawn(PieceColor.WHITE));
	  */	 
	  
	
	/*
	  // Blacks
	  
	  // rooks b.boardState.put(new Location(File.A, 8),
	  PieceFactory.getRook(PieceColor.BLACK)); 
	  b.boardState.put(new Location(File.H, 8), PieceFactory.getRook(PieceColor.BLACK));
	  
	  // knights b.boardState.put(new Location(File.B, 8),
	  PieceFactory.getKnight(PieceColor.BLACK)); 
	  b.boardState.put(new Location(File.G, 8), PieceFactory.getKnight(PieceColor.BLACK));
	  
	  // bishops b.boardState.put(new Location(File.C, 8),
	  PieceFactory.getBishop(PieceColor.BLACK));
	  b.boardState.put(new Location(File.F, 8), PieceFactory.getBishop(PieceColor.BLACK));
	  
	  // queens b.boardState.put(new Location(File.D, 8),
	  PieceFactory.getQueen(PieceColor.BLACK));
	  
	  // kings b.boardState.put(new Location(File.E, 8),
	  PieceFactory.getKing(PieceColor.BLACK));
	  
	  // pawns b.boardState.put(new Location(File.A, 7),
	  PieceFactory.getPawn(PieceColor.BLACK));
	  b.boardState.put(new Location(File.B, 7), PieceFactory.getPawn(PieceColor.BLACK));
	  b.boardState.put(new Location(File.C, 7), PieceFactory.getPawn(PieceColor.BLACK));
	  b.boardState.put(new Location(File.D, 7), PieceFactory.getPawn(PieceColor.BLACK));
	  b.boardState.put(new Location(File.E, 7), PieceFactory.getPawn(PieceColor.BLACK));
	  b.boardState.put(new Location(File.F, 7), PieceFactory.getPawn(PieceColor.BLACK));
	  b.boardState.put(new Location(File.G, 7), PieceFactory.getPawn(PieceColor.BLACK));
	  b.boardState.put(new Location(File.H, 7), PieceFactory.getPawn(PieceColor.BLACK));
	 */

	/*
	  private void storeBlackAndWhitePieces(Map<Location, AbstractPiece> setOfPieces, Square newSquare) { 
		  if (setOfPieces.containsKey(newSquare.getLocation())) { 
			  AbstractPiece piece = setOfPieces.get(newSquare.getLocation()); 
			  newSquare.setCurrentPiece(piece);
			  newSquare.setOccupied(true);
			  piece.setCurrentSquare(newSquare);
			  if (piece.getColor().equals(PieceColor.BLACK)) {
				  blackPieces.add(piece);
				  } 
			  else {
				  whitePieces.add(piece);
				  }
			  }
		  }
	  
	  public Map<Location, Square> getBoardState() { 
		  return this.boardState; 
	  }
	  
	  public List<AbstractPiece> getWhitePieces() { 
		  return this.whitePieces; 
	  }
	  
	  public List<AbstractPiece> getBlackPieces() { 
		  return this.blackPieces; 
	  }
	  
	  public void printBoard() { 
		  for (int rank = 0; rank < boardSquares.length; rank++) {
			  System.out.print(BOARD_DIMENSION - rank + " ");
			  for (int file = 0; file < boardSquares[rank].length; file++) { 
				  if (boardSquares[rank][file].isOccupied()) { 
					  AbstractPiece piece = boardSquares[rank][file].getCurrentPiece();
					  System.out.print(piece.getType().name().charAt(0) + " "); // Takes the initial of each piece 
				  }
				  else { 
				  // Empty square representation
					  System.out.print("- "); } } System.out.println(); 
				  }
  				System.out.print("  ");
  				
		  for (File file : File.values()) {
			  System.out.print(file.name() + " ");
		  }
		  System.out.println();
	  }
	 */
	
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

}
