package com.mctschess.gui.panels;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.mctschess.gui.ImagesFactory;
import com.mctschess.model.pieces.Piece;
import com.mctschess.model.pieces.PieceFactory;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PiecePromotionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel pnMessage;
	private JPanel pnPieces;
	private JButton btBishop;
	private JButton btRook;
	private JButton btKnight;
	private JButton btQueen;

	private PieceColor color;
	private String colorName;
	private Piece selectedPromotionPiece;

	private ProcessPromotion pP;

	/**
	 * Create the dialog.
	 */
	public PiecePromotionDialog(boolean isBlackPromotion) {
		colorName = isBlackPromotion ? "black" : "white";
		color = isBlackPromotion ? PieceColor.BLACK : PieceColor.WHITE;

		pP = new ProcessPromotion();

		setTitle("Piece Promotion");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(PiecePromotionDialog.class.getResource("/com/mctschess/gui/img/black_queen.png")));
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().add(getPnMessage(), BorderLayout.NORTH);
		getContentPane().add(getPnPieces(), BorderLayout.CENTER);

		setLocationRelativeTo(null);
	}

	private JPanel getPnMessage() {
		if (pnMessage == null) {
			pnMessage = new JPanel();
		}
		return pnMessage;
	}

	private JPanel getPnPieces() {
		if (pnPieces == null) {
			pnPieces = new JPanel();
			pnPieces.setLayout(null);
			pnPieces.add(getBtBishop());
			pnPieces.add(getBtRook());
			pnPieces.add(getBtKnight());
			pnPieces.add(getBtQueen());
		}
		return pnPieces;
	}

	private JButton getBtBishop() {
		if (btBishop == null) {
			btBishop = new JButton("");
			btBishop.setBounds(26, 5, 80, 80);
			btBishop.setActionCommand("_bishop");
			btBishop.setIcon(ImagesFactory.getScaledImage(btBishop, colorName + btBishop.getActionCommand()));
			btBishop.addActionListener(pP);
		}
		return btBishop;
	}

	private JButton getBtRook() {
		if (btRook == null) {
			btRook = new JButton("");
			btRook.setBounds(116, 5, 80, 80);
			btRook.setActionCommand("_rook");
			btRook.setIcon(ImagesFactory.getScaledImage(btRook, colorName + btRook.getActionCommand()));
			btRook.addActionListener(pP);
		}
		return btRook;
	}

	private JButton getBtKnight() {
		if (btKnight == null) {
			btKnight = new JButton("");
			btKnight.setBounds(206, 5, 80, 80);
			btKnight.setActionCommand("_knight");
			btKnight.setIcon(ImagesFactory.getScaledImage(btKnight, colorName + btKnight.getActionCommand()));
			btKnight.addActionListener(pP);
		}
		return btKnight;
	}

	private JButton getBtQueen() {
		if (btQueen == null) {
			btQueen = new JButton("");
			btQueen.setBounds(296, 5, 80, 80);
			btQueen.setActionCommand("_queen");
			btQueen.setIcon(ImagesFactory.getScaledImage(btQueen, colorName + btQueen.getActionCommand()));
			btQueen.addActionListener(pP);
		}
		return btQueen;
	}

	public Piece getSelectedPromotionPiece() {
		return this.selectedPromotionPiece;
	}

	private class ProcessPromotion implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton pieceButton = (JButton) e.getSource();
			switch (pieceButton.getActionCommand()) {
			case "_bishop":
				selectedPromotionPiece = PieceFactory.getBishop(color);
				break;
			case "_rook":
				selectedPromotionPiece = PieceFactory.getRook(color);
				break;
			case "_knight":
				selectedPromotionPiece = PieceFactory.getKnight(color);
				break;
			case "_queen":
				selectedPromotionPiece = PieceFactory.getQueen(color);
				break;
			}
			dispose();
		}

	}
}
