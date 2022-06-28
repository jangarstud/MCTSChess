package test.com.mctschess.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.LogManager;

import org.junit.jupiter.api.Test;

import com.mctschess.ai.MCTS;
import com.mctschess.model.board.Board;
import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.LocationAndPiece;
import com.mctschess.model.location.Move;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.PieceFactory;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;

class MovesTests {
	
	static {
		try (InputStream is = MovesTests.class.getClassLoader().getResourceAsStream("logging.properties")) {
			LogManager.getLogManager().readConfiguration(is);
		} catch (IOException e) {
			throw new RuntimeException("Error while loading log configuration");
		}
	}

	@Test
	void testMCTSAI() {

	}

	@Test
	void testMCTSTimeToThink() {
		IBoard board = Board.createOnInitState();
		MCTS tree = new MCTS(board);
		
		long startTime = System.currentTimeMillis();
		tree.learn(500);
		long endTime = System.currentTimeMillis();
		
		System.out.println(endTime-startTime );
		
		assertTrue(Math.abs(endTime - startTime - 500) < 200);
	}

	@Test
	void testKingAndTwoBishopsAgainstAKingEnding() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.C, 1, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 1, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 5, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(1000);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testKingAndTwoBishopsAgainstAKingEnding2() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.C, 1, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 4, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 6, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 8, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(1000);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testAnderssensMateIn1() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.H, 3, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 7, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 6, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 8, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(500);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testCoziosMateIn1() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.H, 6, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 1, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 3, PieceFactory.getQueen(PieceColor.BLACK)),
				new LocationAndPiece(File.G, 4, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.G, 3, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(500);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testRetisMateIn1() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.H, 4, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 1, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.H, 1, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.B, 8, PieceFactory.getKnight(PieceColor.BLACK)),
				new LocationAndPiece(File.B, 7, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.C, 6, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.C, 8, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.C, 7, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(500);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testMateIn2Sample() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.C, 6, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 1, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 3, PieceFactory.getPawn(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 1, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 7, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.G, 7, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.G, 8, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(2000);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testDoubleKnightMateIn2() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.F, 6, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 6, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 4, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.C, 8, PieceFactory.getKnight(PieceColor.BLACK)),
				new LocationAndPiece(File.A, 7, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.B, 7, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.C, 7, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.B, 8, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(2000);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testBoardProblem3MateIn2() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.E, 1, PieceFactory.getKnight(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 2, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 2, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.H, 2, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.B, 4, PieceFactory.getKnight(PieceColor.BLACK)),
				new LocationAndPiece(File.E, 2, PieceFactory.getBishop(PieceColor.BLACK)),
				new LocationAndPiece(File.F, 2, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(2000);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testCoziosMateIn2() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.B, 6, PieceFactory.getQueen(PieceColor.WHITE)),
				new LocationAndPiece(File.G, 1, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 3, PieceFactory.getQueen(PieceColor.BLACK)),
				new LocationAndPiece(File.G, 4, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.H, 4, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(2000);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}
	
	@Test
	void testBlindSwineMateIn3() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.E, 7, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.B, 7, PieceFactory.getRook(PieceColor.WHITE)),
				new LocationAndPiece(File.A, 1, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 8, PieceFactory.getRook(PieceColor.BLACK)),
				new LocationAndPiece(File.G, 7, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.H, 7, PieceFactory.getPawn(PieceColor.BLACK)),
				new LocationAndPiece(File.G, 8, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(5000);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("--- WHITE MOVE ---");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("*** BLACK MOVE ***");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());
	}

}
