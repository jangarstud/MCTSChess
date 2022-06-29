package com.mctschess.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.Move;

public class MCTS {
	
	private Logger LOG = Logger.getLogger(MCTS.class.getName());

	private static final int MCTS_DEFEAT = 0;
	private static final int MCTS_DRAW = 1;
	private static final int MCTS_WIN = 10;
	
	private static final boolean MCTS_FORCE_CHECKMATE = false;
	private static final boolean MCTS_IGNORE_DRAWS = false;
	private static final int MCTS_MAX_ROLLOUT_STEPS = -1; // -1 -> Infinite steps
	private static final int MCTS_SELECT_ENEMY_MOVE_MODE = 1; // 0 - BestMove, 1 - WorstMove, 2 - RandomMove

	private static final int MCTS_UNFINISHED = -1;
	
	public static final Random random = new Random();

	private Node root;
	private long startTime;
	private long timeToThink;

	public MCTS(IBoard board) {
		root = Node.root(board);
	}

	public void learn(long timeToThink) {
		this.timeToThink = timeToThink;
		startTime = System.currentTimeMillis();
		
		int loops = 0;
		int wins = 0;
		int defeats = 0;
		int draws = 0;
		
		while (true) {
			try {
				loops++;
				Node nodeToExpand = selection();
				if (isTimeToThinkFinished())
					break;
				Node nodeToRollout = expansion(nodeToExpand);
				if (isTimeToThinkFinished())
					break;
				int evaluation = rollout(nodeToRollout);
				if (isTimeToThinkFinished())
					break;
				if (evaluation != MCTS_UNFINISHED && (!MCTS_IGNORE_DRAWS || evaluation != MCTS_DRAW)) {
					backPropagate(nodeToRollout, evaluation);
					if (isTimeToThinkFinished())
						break;
				}
				switch (evaluation) {
				
				case MCTS_DEFEAT: defeats++; break;
				case MCTS_DRAW: draws++; break;
				case MCTS_WIN: wins++; break;
				}
			}
			catch (Exception e) {
				LOG.log(Level.WARNING, "An error has occurred while learning", e);				
			}
		}
		
		if (LOG.isLoggable(Level.FINE)) {
			LOG.log(Level.FINE, "Trains: "+ loops + " loops");
			LOG.log(Level.FINE, " - Wins: " + wins);
			LOG.log(Level.FINE, " - Defeats: " + defeats);
			LOG.log(Level.FINE, " - Draws: " + draws);
			LOG.log(Level.FINE, " - UCBs: " + root.getChildrenUCB());
		}
		
	}

	public Move selectBestMove() {
		if (!root.hasChildren())
			root.expand();
		Node bestChild = root.getBestChild();
		return bestChild.getMove();
	}

	private Node selection() {
		Node current = root;
		while (current.hasChildren()) {
			if (isTimeToThinkFinished())
				break;
			if (current.getBoard().getCurrentColor() == root.getBoard().getCurrentColor()) { 
				current = current.getBestChild();
			} else {
				switch (MCTS_SELECT_ENEMY_MOVE_MODE) {
				case 0: current = current.getBestChild();
				break;
				case 1: current = current.getWorstChild();
				break;
				case 2: current = current.getRandomChild();
				break;
				default: throw new RuntimeException();
				}
			}
		}

		// Terminal state
		return current;
	}

	public Node expansion(Node nodeToExpand) {
		if (nodeToExpand == root || nodeToExpand.getN() > 0) {
			if (nodeToExpand.expand() > 0) {
				return nodeToExpand.getFirstChild();
			}
			else{
				return nodeToExpand;
			}
		} else {
			return nodeToExpand;
		}
	}

	public int rollout(Node nodeToRollout) {
		IBoard board = nodeToRollout.getBoard();
		int loops = 0;
		List<IBoard> boards = new ArrayList<>();
		try {
			
		do {
			loops ++;
			//System.out.println(board + "\n");
			boards.add(board);
			if (isTimeToThinkFinished())
				return -1;

			List<Move> moves = board.getValidMoves();
			if (moves.isEmpty()) {
				if (board.isKingOnCheck()) {
					if (board.getCurrentColor() == root.getBoard().getCurrentColor()) {
						return MCTS_DEFEAT;
					} else {
						return MCTS_WIN;
					}
				} else {
					return MCTS_DRAW;
				}
			} else if (board.isOnDeadPosition()) {
				return MCTS_DRAW;
			} else {
				board = getRolloutNextBoard(moves, board);
			}
		} while (MCTS_MAX_ROLLOUT_STEPS == -1 || loops < MCTS_MAX_ROLLOUT_STEPS);
		
		}
		catch (Exception e) {
			System.out.println("\n");
			for (IBoard b: boards) System.out.println(b);
			
			System.out.println("\n");
		}
		
		return MCTS_UNFINISHED;
	}
	
	private IBoard getRolloutNextBoard(List<Move> moves, IBoard board) {
		if (MCTS_FORCE_CHECKMATE) { 
			for (Move move: moves) {
				IBoard auxBoard = board.applyMove(move);
				if (auxBoard.isKingOnCheck() && auxBoard.getValidMoves().isEmpty()) {
					return auxBoard;
				}
			}
		}
		Move move = moves.get(random.nextInt(moves.size()));
		return board.applyMove(move);
	}

	public void backPropagate(Node nodeToBackPropagate, int evaluation) {
		Node current = nodeToBackPropagate;
		do {
			if (isTimeToThinkFinished())
				return;

			current.updateUCBParams(evaluation);
			current = current.getParent();
		} while (current != null);
	}

	private boolean isTimeToThinkFinished() {
		return System.currentTimeMillis() - startTime > timeToThink;
	}
}
