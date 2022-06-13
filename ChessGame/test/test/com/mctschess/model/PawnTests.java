package test.com.mctschess.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mctschess.model.board.Board;
import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.LocationAndPiece;
import com.mctschess.model.location.Move;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;
import com.mctschess.model.pieces.PieceFactory;

class PawnTests {

	private static IBoard board;

	// Class 1 - First move 2 positions
	@Test
	void testFirstMove() {
		board = Board.createWithPieces(null, PieceColor.WHITE,
				new LocationAndPiece(File.E, 2, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 2);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(2, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.E, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 4))));

	}

	@Test
	void testFirstMoveOppositePawn() {
		board = Board.createWithPieces(null, PieceColor.WHITE,
				new LocationAndPiece(File.E, 2, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 3, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 2);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(0, moves.size());
		assertFalse(moves.contains(new Move(from, new Location(File.E, 3))));
		assertFalse(moves.contains(new Move(from, new Location(File.E, 4))));

	}

	@Test
	void testFirstMove2PositionsOppositePawn() {
		board = Board.createWithPieces(null, PieceColor.WHITE,
				new LocationAndPiece(File.E, 2, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 4, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 2);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(1, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.E, 3))));
		assertFalse(moves.contains(new Move(from, new Location(File.E, 4))));

	}

	@Test
	void testEnPassantBlackPawn() {
		File blackEnPassantPawnFile = File.E;
		board = Board.createWithPieces(blackEnPassantPawnFile, PieceColor.WHITE, 
				new LocationAndPiece(File.E, 5, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.D, 5, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 5, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location fromLefttWhitePawn = new Location(File.D, 5);
		Location fromRightWhitePawn = new Location(File.F, 5);
		List<Move> movesLeftWhitePawn = board.getValidMoves(fromLefttWhitePawn);
		List<Move> movesRightWhitePawn = board.getValidMoves(fromRightWhitePawn);
		assertEquals(2, movesLeftWhitePawn.size());
		assertEquals(2, movesRightWhitePawn.size());

		assertTrue(movesLeftWhitePawn.contains(new Move(fromLefttWhitePawn, new Location(File.E, 6))));
		assertTrue(movesLeftWhitePawn.contains(new Move(fromLefttWhitePawn, new Location(File.D, 6))));

		assertTrue(movesRightWhitePawn.contains(new Move(fromRightWhitePawn, new Location(File.E, 6))));
		assertTrue(movesRightWhitePawn.contains(new Move(fromRightWhitePawn, new Location(File.F, 6))));

	}
	
	@Test
	void testEnPassantWhitePawn() {
		File whiteEnPassantPawnFile = File.G;
		board = Board.createWithPieces(whiteEnPassantPawnFile, PieceColor.BLACK,
				new LocationAndPiece(File.G, 4, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.H, 4, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 4, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.BLACK)));

		Location fromLefttWhitePawn = new Location(File.F, 4);
		Location fromRightWhitePawn = new Location(File.H, 4);
		List<Move> movesLeftWhitePawn = board.getValidMoves(fromLefttWhitePawn);
		List<Move> movesRightWhitePawn = board.getValidMoves(fromRightWhitePawn);
		assertEquals(2, movesLeftWhitePawn.size());
		assertEquals(2, movesRightWhitePawn.size());

		assertTrue(movesLeftWhitePawn.contains(new Move(fromLefttWhitePawn, new Location(File.G, 3))));
		assertTrue(movesLeftWhitePawn.contains(new Move(fromLefttWhitePawn, new Location(File.F, 3))));

		assertTrue(movesRightWhitePawn.contains(new Move(fromRightWhitePawn, new Location(File.G, 3))));
		assertTrue(movesRightWhitePawn.contains(new Move(fromRightWhitePawn, new Location(File.H, 3))));

	}
	
	@Test
	void testPromotionWhitePawn() {
		Location from = new Location(File.A, 7);
		Location to = new Location(File.A, 8);
		board = Board.createWithPieces(PieceColor.WHITE,
				new LocationAndPiece(File.A, 7, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		List<Move> moves = board.getValidMoves(from);
		assertTrue(moves.contains(new Move(from, to, PieceFactory.getBishop(PieceColor.WHITE))));
		assertTrue(moves.contains(new Move(from, to, PieceFactory.getRook(PieceColor.WHITE))));
		assertTrue(moves.contains(new Move(from, to, PieceFactory.getKnight(PieceColor.WHITE))));
		assertTrue(moves.contains(new Move(from, to, PieceFactory.getQueen(PieceColor.WHITE))));
	}
	
	@Test
	void testPromotionBlackPawn() {
		Location from = new Location(File.C, 2);
		Location to = new Location(File.C, 1);
		board = Board.createWithPieces(PieceColor.BLACK,
				new LocationAndPiece(File.C, 2, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 8, PieceFactory.getKing(PieceColor.BLACK)));

		List<Move> moves = board.getValidMoves(from);
		assertTrue(moves.contains(new Move(from, to, PieceFactory.getBishop(PieceColor.BLACK))));
		assertTrue(moves.contains(new Move(from, to, PieceFactory.getRook(PieceColor.BLACK))));
		assertTrue(moves.contains(new Move(from, to, PieceFactory.getKnight(PieceColor.BLACK))));
		assertTrue(moves.contains(new Move(from, to, PieceFactory.getQueen(PieceColor.BLACK))));
	}

}
