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

class QueenTests {

	private static IBoard board;

	// Class 6 - Empty files and ranks
	@Test
	void testQueenFreeMoves() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 8, PieceFactory.getKing(PieceColor.WHITE)));
		
		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		
		assertEquals(27, moves.size());
		
		assertTrue(moves.contains(new Move(from, new Location(File.F, 4))));
		assertTrue(moves.contains(new Move(from, new Location(File.F, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.F, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.D, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 7))));
	}

	// Class 1-5 - In front of a piece of an opposite color
	@Test
	void testFrontQueenPieceOppositeColor() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 5, PieceFactory.getQueen(PieceColor.BLACK)),
				new LocationAndPiece(File.D, 4, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.D, 3, PieceFactory.getKnight(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 3, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 3, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 4, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 5, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 5, PieceFactory.getKnight(PieceColor.BLACK)),
				new LocationAndPiece(File.B, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(8, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.D, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.D, 4))));
		assertTrue(moves.contains(new Move(from, new Location(File.D, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.F, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.F, 4))));
		assertTrue(moves.contains(new Move(from, new Location(File.F, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.E, 5))));
	}

	// Class 1-5 - In front of a piece of the same color
	@Test
	void testFrontPieceSameColor() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 5, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 4, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 3, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 3, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 3, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 4, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 5, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 5, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.B, 8, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(0, moves.size());
	}

}
