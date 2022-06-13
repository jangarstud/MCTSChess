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

class BishopTests {

	private static IBoard board;

	// Class 6 - Empty diagonal
	@Test
	void testFreeMoves() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(13, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.B, 1))));
		assertTrue(moves.contains(new Move(from, new Location(File.B, 7))));
		assertTrue(moves.contains(new Move(from, new Location(File.H, 1))));
		assertTrue(moves.contains(new Move(from, new Location(File.H, 7))));
	}

	// Class 1-5 - In front of a piece of an opposite color
	@Test
	void testFrontBishopPieceOppositeColor() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 5, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.D, 5, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.D, 3, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 3, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(4, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.F, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.D, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.F, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.D, 3))));
	}

	// Class 1-5 - In front of a piece of the same color
	@Test
	void testFrontPieceSameColor() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 5, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 5, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 3, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 3, PieceFactory.getRook(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(0, moves.size());
	}

	// EQUIVALENCE Class 7.1 - Check that cannot be solved by this piece
	@Test
	void testNotSolvableCheck() {
	}

	// EQUIVALENCE Class 7.2 - Check that can be solved by this piece [7.2.1:
	// Covering the king with the piece; 7.2.2: Replacing the rival's piece]
	@Test
	void testSolvableCheck() {
	}

	// EQUIVALENCE Class 8.1 - Move that keeps avoiding check and
	// EQUIVALENCE Class 8.2 - Move that enables invalid check
	@Test
	void testAvoidableCheck() {
		board = Board.createWithPieces(new LocationAndPiece(File.D, 5, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 4, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 2, PieceFactory.getBishop(PieceColor.BLACK)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(2, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.F, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.G, 2))));
	}

}
