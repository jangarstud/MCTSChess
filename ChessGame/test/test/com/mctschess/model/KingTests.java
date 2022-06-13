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

class KingTests {

	private static IBoard board;

	// Class 1 - Kingside and Queenside castling are valid.
	@Test
	void testKingsideAndQueensideCastling() {
		// White
		board = Board.createWithPieces(PieceColor.WHITE, null, false, false, true, true, 
				new LocationAndPiece(File.H, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.A, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location whiteFrom = new Location(File.E, 1);
		List<Move> whiteMoves = board.getValidMoves(whiteFrom);
		assertEquals(7, whiteMoves.size());
		assertTrue(whiteMoves.contains(new Move(whiteFrom, new Location(File.G, 1))));
		assertTrue(whiteMoves.contains(new Move(whiteFrom, new Location(File.C, 1))));

		// Black
		board = Board.createWithPieces(PieceColor.BLACK, null, true, true, false, false,
				new LocationAndPiece(File.H, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.A, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.BLACK)));

		Location blackFrom = new Location(File.E, 8);
		List<Move> blackMoves = board.getValidMoves(blackFrom);
		assertEquals(7, blackMoves.size());
		assertTrue(blackMoves.contains(new Move(blackFrom, new Location(File.G, 8))));
		assertTrue(blackMoves.contains(new Move(blackFrom, new Location(File.C, 8))));
	}

	@Test
	void testKingOnCheck() {
		// White
		board = Board.createWithPieces(PieceColor.WHITE, null, false, false, true, true,
				new LocationAndPiece(File.H, 4, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.H, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.A, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location whiteFrom = new Location(File.E, 1);
		List<Move> whiteMoves = board.getValidMoves(whiteFrom);
		assertEquals(4, whiteMoves.size());

		// Black
		board = Board.createWithPieces(PieceColor.BLACK, null, true, true, false, false, 
				new LocationAndPiece(File.A, 4, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.H, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.A, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.BLACK)));

		Location blackFrom = new Location(File.E, 8);
		List<Move> blackMoves = board.getValidMoves(blackFrom);
		assertEquals(4, blackMoves.size());
	}

	@Test
	void testCastlingSquaresOnCheck() {
		// White
		board = Board.createWithPieces(PieceColor.WHITE, null, false, false, true, true,
				new LocationAndPiece(File.E, 3, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.H, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.A, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location whiteFrom = new Location(File.E, 1);
		List<Move> whiteMoves = board.getValidMoves(whiteFrom);
		assertEquals(3, whiteMoves.size());

		// Black
		board = Board.createWithPieces(PieceColor.BLACK, null, true, true, false, false, 
				new LocationAndPiece(File.E, 6, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.H, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.A, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.BLACK)));

		Location blackFrom = new Location(File.E, 8);
		List<Move> blackMoves = board.getValidMoves(blackFrom);
		assertEquals(3, blackMoves.size());
	}
	
	@Test
	void testCastlingSquaresOccupied() {
		// White
		board = Board.createWithPieces(PieceColor.WHITE, null, false, false, true, true,
				new LocationAndPiece(File.H, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.A, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 1, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 1, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)));

		Location whiteFrom = new Location(File.E, 1);
		List<Move> whiteMoves = board.getValidMoves(whiteFrom);
		assertTrue(whiteMoves.contains(new Move(whiteFrom, new Location(File.E, 2))));
		assertTrue(whiteMoves.contains(new Move(whiteFrom, new Location(File.F, 2))));
		assertTrue(whiteMoves.contains(new Move(whiteFrom, new Location(File.D, 2))));
		assertEquals(3, whiteMoves.size());

		// Black
		board = Board.createWithPieces(PieceColor.BLACK, null, true, true, false, false,
				new LocationAndPiece(File.H, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.A, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 8, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.D, 8, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 8, PieceFactory.getKing(PieceColor.BLACK)));

		Location blackFrom = new Location(File.E, 8);
		List<Move> blackMoves = board.getValidMoves(blackFrom);
		assertEquals(3, blackMoves.size());
	}

}
