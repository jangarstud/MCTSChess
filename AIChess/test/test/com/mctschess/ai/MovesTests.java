package test.com.mctschess.ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

	@Test
	void testMCTSAI() {

	}

	@Test
	void testMCTSTimeToThink() {
		IBoard board = Board.createOnInitState();

		MCTS tree = new MCTS(board);

		long startTime = System.currentTimeMillis();

		tree.learn(500);

		assertTrue(Math.abs(System.currentTimeMillis() - startTime - 500) < 50);
	}

	@Test
	void testKingAndTwoBishopsAgainstAKingEnding() {
		IBoard board = Board.createWithPieces(PieceColor.WHITE, null, false, false, false, false,
				new LocationAndPiece(File.C, 1, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.E, 1, PieceFactory.getBishop(PieceColor.WHITE)),
				new LocationAndPiece(File.F, 1, PieceFactory.getKing(PieceColor.WHITE)),
				new LocationAndPiece(File.D, 5, PieceFactory.getKing(PieceColor.BLACK)));

		System.out.println(board);
		boolean end = false;
		while (!end) {
			MCTS chessEndgameTree = new MCTS(board);

			chessEndgameTree.learn(500);
			Move move = chessEndgameTree.selectBestMove();
			board = board.applyMove(move);
			
			System.out.println("-------------------- WHITE MOVE -------------------");
			System.out.println(board);
			
			List<Move> possibleMoves = board.getValidMoves();
			if (possibleMoves.isEmpty()) {
				end = true;
			} else {
				board = board.applyMove(possibleMoves.get(0));
				System.out.println("**********BLACK MOVE**************");
				System.out.println(board);
			}
		}
		assertTrue(board.isKingOnCheck());
		assertEquals(PieceColor.BLACK, board.getCurrentColor());

	}

}
