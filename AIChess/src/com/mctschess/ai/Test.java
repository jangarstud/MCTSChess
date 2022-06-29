package com.mctschess.ai;

import java.util.List;

import com.mctschess.model.board.Board;
import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.LocationAndPiece;
import com.mctschess.model.location.Move;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.PieceFactory;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;

public class Test {
	
	private static final int LEARNING_TIME = 1000;
	
	private static final int BOARD_CASES_LENGTH = 10;
	private static IBoard[] createSetOfBoards() {
	
		return 
				new IBoard[] {
					// ** Mate-in-1-move boards **
					// [0] - Anderssen's mate in 1 move
					Board.createBoardCase(
						new LocationAndPiece(File.H, 3, PieceFactory.getRook(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 7, PieceFactory.getPawn(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 6, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 8, PieceFactory.getKing(PieceColor.BLACK))
					),	
					// [1] - Cozio's mate in 1 move
					Board.createBoardCase(
						new LocationAndPiece(File.H, 6, PieceFactory.getQueen(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 1, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.F, 3, PieceFactory.getQueen(PieceColor.BLACK)),
						new LocationAndPiece(File.G, 4, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.G, 3, PieceFactory.getKing(PieceColor.BLACK))
					),	
					// [2] - Réti's mate in 1 move
					Board.createBoardCase(
						new LocationAndPiece(File.H, 4, PieceFactory.getBishop(PieceColor.WHITE)),
						new LocationAndPiece(File.D, 1, PieceFactory.getRook(PieceColor.WHITE)),
						new LocationAndPiece(File.H, 1, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.B, 8, PieceFactory.getKnight(PieceColor.BLACK)),
						new LocationAndPiece(File.B, 7, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.C, 6, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.C, 8, PieceFactory.getBishop(PieceColor.BLACK)),
						new LocationAndPiece(File.C, 7, PieceFactory.getKing(PieceColor.BLACK))	
					),
						
					// ** Mate-in-2-moves boards **
					// [3] - Mate in 2 moves (sample)
					Board.createBoardCase(
						new LocationAndPiece(File.C, 6, PieceFactory.getKnight(PieceColor.WHITE)),
						new LocationAndPiece(File.D, 1, PieceFactory.getQueen(PieceColor.WHITE)),
						new LocationAndPiece(File.D, 3, PieceFactory.getPawn(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 1, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.F, 8, PieceFactory.getRook(PieceColor.BLACK)),
						new LocationAndPiece(File.F, 7, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.G, 7, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.G, 8, PieceFactory.getKing(PieceColor.BLACK))
					),
					// [4] - Double-knight mate in 2 moves
					Board.createBoardCase(
						new LocationAndPiece(File.F, 6, PieceFactory.getKnight(PieceColor.WHITE)),
						new LocationAndPiece(File.E, 6, PieceFactory.getKnight(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 4, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.C, 8, PieceFactory.getKnight(PieceColor.BLACK)),
						new LocationAndPiece(File.A, 7, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.B, 7, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.C, 7, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.B, 8, PieceFactory.getKing(PieceColor.BLACK))
					),
					// [5] - Board problem #3 for a checkmate in 2 moves
					Board.createBoardCase(
						new LocationAndPiece(File.E, 1, PieceFactory.getKnight(PieceColor.WHITE)),
						new LocationAndPiece(File.D, 2, PieceFactory.getQueen(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 2, PieceFactory.getBishop(PieceColor.WHITE)),
						new LocationAndPiece(File.H, 2, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.B, 4, PieceFactory.getKnight(PieceColor.BLACK)),
						new LocationAndPiece(File.E, 2, PieceFactory.getBishop(PieceColor.BLACK)),
						new LocationAndPiece(File.F, 2, PieceFactory.getKing(PieceColor.BLACK))
					),
					
					// [6] - Cozio's mate in 2 moves
					Board.createBoardCase(
						new LocationAndPiece(File.B, 6, PieceFactory.getQueen(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 1, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.F, 3, PieceFactory.getQueen(PieceColor.BLACK)),
						new LocationAndPiece(File.G, 4, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.H, 4, PieceFactory.getKing(PieceColor.BLACK))
					),
					// ** Mate-in-3-moves boards **
					// [7] - Blind swine mate in 3 moves
					Board.createBoardCase(
						new LocationAndPiece(File.E, 7, PieceFactory.getRook(PieceColor.WHITE)),
						new LocationAndPiece(File.B, 7, PieceFactory.getRook(PieceColor.WHITE)),
						new LocationAndPiece(File.A, 1, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.F, 8, PieceFactory.getRook(PieceColor.BLACK)),
						new LocationAndPiece(File.G, 7, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.H, 7, PieceFactory.getPawn(PieceColor.BLACK)),
						new LocationAndPiece(File.G, 8, PieceFactory.getKing(PieceColor.BLACK))
					),
					// [8] - Basic checkmate of king and two bishops of opposite colour against a king for 3 moves
					Board.createBoardCase(
						new LocationAndPiece(File.C, 1, PieceFactory.getBishop(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 4, PieceFactory.getBishop(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 6, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.G, 8, PieceFactory.getKing(PieceColor.BLACK))
					),
					// ** More complex sample (several moves) **
					// [9] - More complex checkmate of king and two bishops of opposite colour against a king
					Board.createBoardCase(
						new LocationAndPiece(File.C, 1, PieceFactory.getBishop(PieceColor.WHITE)),
						new LocationAndPiece(File.F, 1, PieceFactory.getBishop(PieceColor.WHITE)),
						new LocationAndPiece(File.E, 1, PieceFactory.getKing(PieceColor.WHITE)),
						new LocationAndPiece(File.D, 5, PieceFactory.getKing(PieceColor.BLACK))
					)
				};
	}

	public static void main(String[] args) {
		final int TESTS_BY_BOARD = 100;
		
		testAllBoardCases(TESTS_BY_BOARD);
//		testBoardCase(TESTS_BY_BOARD, 8);
	}
		
	
	
	private static void testAllBoardCases(int numberOfTests) {
		System.out.println("BOARD CASE; N; WIN; LOSE; DRAW; MOVES");
		for(int i = 0; i < BOARD_CASES_LENGTH; i++) {
			testBoardCase(numberOfTests, i);
		}
	}
	
	
	private static void testBoardCase(int numberOfTests, int boardCaseIndex) {
		for (int i = 1; i <= numberOfTests; i++) {
			boolean end = false;
			int moves = 0;
			
			IBoard[] setOfBoards = createSetOfBoards();
			IBoard board = setOfBoards[boardCaseIndex];
			
			while (!end) {
				moves++;
				MCTS chessEndgameTree = new MCTS(board);
	
				chessEndgameTree.learn(LEARNING_TIME);
				Move move = chessEndgameTree.selectBestMove();
				board = board.applyMove(move);
				
				List<Move> possibleMoves = board.getValidMoves();
				if (possibleMoves.isEmpty() || board.isOnDeadPosition()) {
					end = true;
				} else {
					board = board.applyMove(possibleMoves.get(MCTS.random.nextInt(possibleMoves.size())));
				}
			}
			
			System.out.printf("%d; %d; %d; %d; %d; %d %n",
					boardCaseIndex,
					i,
					board.isKingOnCheck() && board.getCurrentColor() == PieceColor.BLACK ? 1 : 0,
					board.isKingOnCheck() && board.getCurrentColor() == PieceColor.WHITE ? 1 : 0,
					!board.isKingOnCheck() ? 1 : 0,
					moves);
		}
	}

}
