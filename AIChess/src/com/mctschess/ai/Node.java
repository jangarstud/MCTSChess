package com.mctschess.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mctschess.model.board.IBoard;
import com.mctschess.model.location.Move;

public class Node {
	
	private IBoard board;
	private Move move;
	private int n;
	private int totalRewards;

	private Node parent;
	private List<Node> children;

	private Node(Node parent, IBoard board, Move move) {
		this.parent = parent;
		this.board = board;
		this.move = move;
	}

	public static Node root(IBoard board) {
		return new Node(null, board, null);
	}

	public double calculateUCB() {
		if (parent == null)
			return 0;
		
		if (n == 0)
			return Double.POSITIVE_INFINITY;

		double V = (double) totalRewards / n;
		double N = parent.n;
		return V + 2 * Math.sqrt(Math.log(N) / n);
	}

	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	public int getN() {
		return n;
	}

	public Node getBestChild() {
		double bestUCB = Double.NEGATIVE_INFINITY;
		Node bestChild = null;
		for (Node child : children) {
			double currentUCB = child.calculateUCB();
			if (currentUCB > bestUCB) {
				bestChild = child;
				bestUCB = currentUCB;
			}
		}
		return bestChild;
	}
	
	public Node getWorstChild() {
		double worstUCB = Double.POSITIVE_INFINITY;
		Node worstChild = null;
		for (Node child : children) {
			double currentUCB = child.calculateUCB();
			if (currentUCB <= worstUCB) {
				worstChild = child;
				worstUCB = currentUCB;
			}
		}
		return worstChild;
	}

	public Node getRandomChild() {
		return children.get(MCTS.random.nextInt(children.size()));
	}

	public Node getFirstChild() {
		return children.get(0);
	}

	public Node getParent() {
		return this.parent;
	}

	public int expand() {
		List<Move> availableMoves = board.getValidMoves();
		children = new ArrayList<Node>();
		for (Move expandedMove : availableMoves) {
			children.add(new Node(this, board.applyMove(expandedMove), expandedMove));
		}
		return children.size();
	}

	public void updateUCBParams(int result) {
		n++;
		totalRewards += result;
	}

	public IBoard getBoard() {
		return board;
	}

	public Move getMove() {
		return move;
	}
	
	public String getChildrenUCB() {
		return children.stream()
				.map(c -> c.calculateUCB())
				.sorted(Comparator.reverseOrder())
				.map(c -> String.format("%.4f", c))
				.collect(Collectors.joining(" - "));
	}

}
