package com.mctschess.ai;

import java.util.List;
import java.util.Random;

import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.Move;

public class MCTS {

	public static final int MCTS_DEFEAT = 0;
	public static final int MCTS_DRAW = 1;
	public static final int MCTS_WIN = 10;

	private static final Random random = new Random();

	private Node root;
	private long startTime;
	private long timeToThink;

	public MCTS(IBoard board) {
		root = Node.root(board);
	}

	public void learn(long timeToThink) {

		this.timeToThink = timeToThink;
		startTime = System.currentTimeMillis();
		while (true) {
			Node nodeToExpand = selection();
			if (isTimeToThinkFinished())
				break;
			Node nodeToRollout = expansion(nodeToExpand);
			if (isTimeToThinkFinished())
				break;
			int evaluation = rollout(nodeToRollout);
			if (isTimeToThinkFinished())
				break;
			backPropagate(nodeToRollout, evaluation);
			if (isTimeToThinkFinished())
				break;
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
			current = current.getBestChild();
		}

		// Terminal state
		return current;
	}

	public Node expansion(Node nodeToExpand) {
		if (nodeToExpand == root || nodeToExpand.getN() > 0) {
			nodeToExpand.expand();
			return nodeToExpand.getFirstChild();
		} else {
			return nodeToExpand;
		}
	}

	public int rollout(Node nodeToRollout) {
		System.out.println("ROLLOUT START");
		IBoard board = nodeToRollout.getBoard();
		do {
			if (isTimeToThinkFinished())
				return -1;

			List<Move> moves = board.getValidMoves();
			if (moves.isEmpty()) {
				if (board.isKingOnCheck()) {
					if (nodeToRollout.getBoard().getCurrentColor() == root.getBoard().getCurrentColor()) {
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
				Move move = moves.get(random.nextInt(moves.size()));
				System.out.println("AI MOVE: " + move + " MUEVEN " + board.getCurrentColor());
				board = board.applyMove(move);
				System.out.println(board);

			}
		} while (true);
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
