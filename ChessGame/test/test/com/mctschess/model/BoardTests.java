package test.com.mctschess.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mctschess.model.board.Board;
import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.LocationAndPiece;
import com.mctschess.model.location.Move;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.PieceFactory;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;
import com.mctschess.model.pieces.AbstractPiece.PieceType;

class BoardTests {
	private static Board board;

	@BeforeAll
	static void setUpBeforeClass() {
		board = Board.createOnInitState();
	}

	@Test
	void testCreateOnInitState() {
		int startingRankPosition = 1;
		PieceColor colorToCheck = PieceColor.WHITE;
		int pieceToCheckIndex = 0;

		// Symmetrical pieces
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < Board.BOARD_DIMENSION / 2 - 1; j++) {
				Location locationToCheck = new Location(File.values()[j], startingRankPosition);
				assertFalse(board.isEmpty(locationToCheck));
				assertEquals(colorToCheck, board.getPieceAt(locationToCheck).getColor());
				assertEquals(PieceType.values()[pieceToCheckIndex], board.getPieceAt(locationToCheck).getType());

				locationToCheck = new Location(File.values()[File.values().length - 1 - j], startingRankPosition); // Files.sum(offset?)
				assertFalse(board.isEmpty(locationToCheck));
				assertEquals(colorToCheck, board.getPieceAt(locationToCheck).getColor());
				assertEquals(PieceType.values()[pieceToCheckIndex], board.getPieceAt(locationToCheck).getType());

				pieceToCheckIndex++;
			}
			colorToCheck = PieceColor.BLACK;
			startingRankPosition = Board.BOARD_DIMENSION;
			pieceToCheckIndex = 0;
		}

		// Pawns
		int pawnsRank = 2;
		colorToCheck = PieceColor.WHITE;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < Board.BOARD_DIMENSION; j++) {
				Location locationToCheck = new Location(File.values()[j], pawnsRank);
				assertFalse(board.isEmpty(locationToCheck));
				assertEquals(colorToCheck, board.getPieceAt(locationToCheck).getColor());
				assertEquals(PieceType.PAWN, board.getPieceAt(locationToCheck).getType());

				pieceToCheckIndex++;
			}
			colorToCheck = PieceColor.BLACK;
			pawnsRank = 7;
		}

		// King and Queen
		colorToCheck = PieceColor.WHITE;
		startingRankPosition = 1;
		for (int i = 0; i < 2; i++) {
			Location locationToCheck = new Location(File.values()[3], startingRankPosition);
			assertFalse(board.isEmpty(locationToCheck));
			assertEquals(colorToCheck, board.getPieceAt(locationToCheck).getColor());
			assertEquals(PieceType.QUEEN, board.getPieceAt(locationToCheck).getType());

			locationToCheck = new Location(File.values()[4], startingRankPosition);
			assertFalse(board.isEmpty(locationToCheck));
			assertEquals(colorToCheck, board.getPieceAt(locationToCheck).getColor());
			assertEquals(PieceType.KING, board.getPieceAt(locationToCheck).getType());

			colorToCheck = PieceColor.BLACK;
			startingRankPosition = Board.BOARD_DIMENSION;
		}
	}
	
	@Test
	void testGetValidMoves() {
		IBoard board = Board.createWithPieces(PieceColor.BLACK, null, false, false, false, false,
				new LocationAndPiece(File.C, 1, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.C, 7, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 3, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.H, 8, PieceFactory.getKing(PieceColor.BLACK)));

		
		List<Move> moves = board.getValidMoves();
		System.out.println(board);
		for (Move move: moves) System.out.println(move);
		assertEquals(3, moves.size());
	}

}
