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

class KnightTests {

	private static IBoard board;

	@Test
	void testKnightFreeMoves() {
		board = Board.createWithPieces(new LocationAndPiece(File.E, 4, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.E, 4);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(8, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.F, 2))));
		assertTrue(moves.contains(new Move(from, new Location(File.D, 2))));
		assertTrue(moves.contains(new Move(from, new Location(File.C, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.G, 3))));
		assertTrue(moves.contains(new Move(from, new Location(File.C, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.G, 5))));
		assertTrue(moves.contains(new Move(from, new Location(File.F, 6))));
		assertTrue(moves.contains(new Move(from, new Location(File.D, 6))));
	}
	
	@Test
	void testKnightRestrictedMoves() {
		board = Board.createWithPieces(new LocationAndPiece(File.H, 1, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 3, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 2, PieceFactory.getKnight(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location from = new Location(File.H, 1);
		List<Move> moves = board.getValidMoves(from);
		assertEquals(1, moves.size());
		assertTrue(moves.contains(new Move(from, new Location(File.F, 2))));
		
	}

}
