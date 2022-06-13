package test.com.mctschess.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mctschess.model.board.Board;
import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.LocationAndPiece;
import com.mctschess.model.location.Move;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.PieceFactory;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;

class MovesTests {
	private static IBoard board;
	
	@Test
	void testRegularMoves() {
		board = Board.createOnInitState();
		
		Location dWhitePawnFrom = new Location(File.D, 2);
		Location dWhitePawnTo = new Location(File.D, 4);
		Move moveToApply = new Move(dWhitePawnFrom, dWhitePawnTo);
		IBoard newBoard = board.applyMove(moveToApply);
		
		assertNull(newBoard.getPieceAt(dWhitePawnFrom));
		assertTrue(newBoard.getPieceAt(dWhitePawnTo) == PieceFactory.getPawn(PieceColor.WHITE));
	}
	
	@Test
	void testWhiteEnPassantMoves() {
		File whiteEnPassantPawnFile = File.G;
		Location whiteEnPassantPawnLocation = new Location(File.G, 4);
		board = Board.createWithPieces(whiteEnPassantPawnFile, PieceColor.BLACK,
				new LocationAndPiece(File.G, 4, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 4, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.BLACK)));

		Location leftBlackPawnFrom = new Location(File.F, 4);
		Location leftBlackPawnTo = new Location(File.G, 3);
		IBoard newBoard = board.applyMove(new Move(leftBlackPawnFrom, leftBlackPawnTo));
		
		assertNull(newBoard.getPieceAt(whiteEnPassantPawnLocation));
		assertNull(newBoard.getPieceAt(leftBlackPawnFrom));
		assertTrue(newBoard.getPieceAt(leftBlackPawnTo) == PieceFactory.getPawn(PieceColor.BLACK));
	}
	
	@Test
	void testBlackEnPassantMoves() {
		File blackEnPassantPawnFile = File.E;
		Location blackEnPassantPawnLocation = new Location(File.E, 5);
		board = Board.createWithPieces(blackEnPassantPawnFile, PieceColor.WHITE, 
				new LocationAndPiece(File.E, 5, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.D, 5, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location leftWhitePawnFrom = new Location(File.D, 5);
		Location leftWhitePawnTo = new Location(File.E, 6);
		IBoard newBoard = board.applyMove(new Move(leftWhitePawnFrom, leftWhitePawnTo));
		
		assertNull(newBoard.getPieceAt(blackEnPassantPawnLocation));
		assertNull(newBoard.getPieceAt(leftWhitePawnFrom));
		assertTrue(newBoard.getPieceAt(leftWhitePawnTo) == PieceFactory.getPawn(PieceColor.WHITE));
	}
	
	@Test
	void testBlackKingsideCastlingMoves() {
		board = Board.createWithPieces(PieceColor.BLACK, null, true, false, false, false,
				new LocationAndPiece(File.H, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.A, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.BLACK)));

		Location blackFrom = new Location(File.E, 8);
		Location blackToKingside = new Location(File.G, 8);
		IBoard newBoard = board.applyMove(new Move(blackFrom, blackToKingside));
		
		assertNull(newBoard.getPieceAt(blackFrom));
		assertTrue(newBoard.getPieceAt(blackToKingside) == PieceFactory.getKing(PieceColor.BLACK));
		
		assertNull(newBoard.getPieceAt(new Location(File.H, 8)));
		assertTrue(newBoard.getPieceAt(new Location(File.F, 8)) == PieceFactory.getRook(PieceColor.BLACK));
	}
	
	@Test
	void testBlackQueensideCastlingMoves() {
		board = Board.createWithPieces(PieceColor.BLACK, null, false, true, false, false,
				new LocationAndPiece(File.H, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.A, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.BLACK)));
		
		Location blackFrom = new Location(File.E, 8);
		Location blackToQueenside = new Location(File.C, 8);
		IBoard newBoard = board.applyMove(new Move(blackFrom, blackToQueenside));
		
		assertNull(newBoard.getPieceAt(blackFrom));
		assertTrue(newBoard.getPieceAt(blackToQueenside) == PieceFactory.getKing(PieceColor.BLACK));
		
		assertNull(newBoard.getPieceAt(new Location(File.A, 8)));
		assertTrue(newBoard.getPieceAt(new Location(File.D, 8)) == PieceFactory.getRook(PieceColor.BLACK));
	}
	
	@Test
	void testWhiteKingsideCastlingMoves() {
		board = Board.createWithPieces(PieceColor.WHITE, null, false, false, true, false, 
				new LocationAndPiece(File.H, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.A, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location whiteFrom = new Location(File.E, 1);
		Location whiteToKingside = new Location(File.G, 1);
		IBoard newBoard = board.applyMove(new Move(whiteFrom, whiteToKingside));
		
		assertNull(newBoard.getPieceAt(whiteFrom));
		assertTrue(newBoard.getPieceAt(whiteToKingside) == PieceFactory.getKing(PieceColor.WHITE));
		
		assertNull(newBoard.getPieceAt(new Location(File.H, 1)));
		assertTrue(newBoard.getPieceAt(new Location(File.F, 1)) == PieceFactory.getRook(PieceColor.WHITE));
	}
	
	@Test
	void testWhiteQueensideCastlingMoves() {
		board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, true, 
				new LocationAndPiece(File.H, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.A, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));
		
		Location whiteFrom = new Location(File.E, 1);
		Location whiteToQueenside = new Location(File.C, 1);
		IBoard newBoard = board.applyMove(new Move(whiteFrom, whiteToQueenside));
		
		assertNull(newBoard.getPieceAt(whiteFrom));
		assertTrue(newBoard.getPieceAt(whiteToQueenside) == PieceFactory.getKing(PieceColor.WHITE));
		
		assertNull(newBoard.getPieceAt(new Location(File.A, 1)));
		assertTrue(newBoard.getPieceAt(new Location(File.D, 1)) == PieceFactory.getRook(PieceColor.WHITE));
	}
	
	@Test
	void testPromotionMoves() {
		board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false, 
				new LocationAndPiece(File.G, 7, PieceFactory.getPawn(PieceColor.WHITE)));
		
		Location whiteFrom = new Location(File.G, 7);
		Location whitePromotionTo = new Location(File.G, 8);
		IBoard newBoard = board.applyMove(new Move(whiteFrom, whitePromotionTo, PieceFactory.getQueen(PieceColor.WHITE)));
		
		assertNull(newBoard.getPieceAt(whiteFrom));
		assertNotEquals(newBoard.getPieceAt(whitePromotionTo), PieceFactory.getPawn(PieceColor.WHITE));
		assertEquals(newBoard.getPieceAt(whitePromotionTo), PieceFactory.getQueen(PieceColor.WHITE));
		
		
		board = Board.createWithPieces(PieceColor.BLACK, null, false, false, false, false, 
				new LocationAndPiece(File.F, 2, PieceFactory.getPawn(PieceColor.BLACK)));
		
		Location blackFrom = new Location(File.F, 2);
		Location blackPromotionTo = new Location(File.F, 1);
		newBoard = board.applyMove(new Move(blackFrom, blackPromotionTo, PieceFactory.getRook(PieceColor.BLACK)));
		
		assertNull(newBoard.getPieceAt(blackFrom));
		assertNotEquals(newBoard.getPieceAt(blackPromotionTo), PieceFactory.getPawn(PieceColor.BLACK));
		assertEquals(newBoard.getPieceAt(blackPromotionTo), PieceFactory.getRook(PieceColor.BLACK));
	}
	
	@Test
	void testCapturedPieceMoves() {
		board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false, 
				new LocationAndPiece(File.D, 4, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 5, PieceFactory.getPawn(PieceColor.BLACK)));
		
		Location whiteFrom = new Location(File.D, 4);
		Location whiteTo = new Location(File.E, 5);
		IBoard newBoard = board.applyMove(new Move(whiteFrom, whiteTo));
		
		assertNull(newBoard.getPieceAt(whiteFrom));
		assertNotEquals(newBoard.getPieceAt(whiteTo), PieceFactory.getPawn(PieceColor.BLACK));
		assertEquals(newBoard.getPieceAt(whiteTo), PieceFactory.getPawn(PieceColor.WHITE));
	}

}
