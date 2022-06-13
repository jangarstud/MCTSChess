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

class RookTests {

	private static IBoard board;

	// Class 6 - Empty files and ranks
	@Test
	void testRookFreeMoves() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(14, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.D, 4))));
		assertTrue(moves.contains(new Move(from, new Location(File.A, 4))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 8))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 1))));
		assertTrue(moves.contains(new Move(from, new Location(File.F, 4))));
		assertTrue(moves.contains(new Move(from, new Location(File.H, 4))));
	}

	// Class 1-5 - In front of a piece of an opposite color
	@Test
	void testFrontRookPieceOppositeColor() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 4, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 5, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 3, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.D, 4, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.A, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(4, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.F, 4))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.D, 4))));
	}

	// Class 1-5 - In front of a piece of the same color
	@Test
	void testFrontPieceSameColor() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 4, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 5, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 3, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 4, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.A, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(0, moves.size());
	}

	// EQUIVALENCE Class 7.1 - Check that cannot be solved by this piece
	@Test
	void testNotSolvableCheck() {
		board = Board.createWithPieces(new LocationAndPiece(File.G, 5, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.C, 2, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 2, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.C, 2);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(0, moves.size());
		
	}

	// EQUIVALENCE Class 7.2 - Check that can be solved by this piece [7.2.1:
	// Covering the king with the piece; 7.2.2: Replacing the rival's piece]
	@Test
	void testSolvableCheck() {
		board = Board.createWithPieces(new LocationAndPiece(File.G, 5, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.G, 3, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 2, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.G, 3);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(2, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.E, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.G, 5))));
	}

}
