package com.mctschess.gui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.mctschess.gui.ImagesFactory;
import com.mctschess.gui.viewmodels.BoardVM;
import com.mctschess.gui.viewmodels.BoardVM.SquareState;

import java.awt.event.MouseEvent;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseAdapter;

public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BoardVM boardVM;
	private JPanel squares[] = new JPanel[64];

	/**
	 * Create the panel.
	 */
	public BoardPanel(BoardVM boardVM) {
		this.boardVM = boardVM;
		setLayout(new GridBagLayout());
		setRanksAndFiles();
		setBoard();
		boardVM.addPropertyChangeListener(new BoardVMPropertyChangeListener());
		addAncestorListener(new BoardPanelComponentListener());
	}
	
	private class BoardPanelComponentListener implements AncestorListener {
		
		@Override
		public void ancestorAdded(AncestorEvent event) {
			repaintSquares();
		
		}

		@Override
		public void ancestorRemoved(AncestorEvent event) {
		
		}

		@Override
		public void ancestorMoved(AncestorEvent event) {
		
		}
	}
	
	private class BoardVMPropertyChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt instanceof IndexedPropertyChangeEvent) {
				indexedPropertyChangeEvent((IndexedPropertyChangeEvent) evt);
			}
			else {
				simplePropertyChangeEvent(evt);
			}
		}

		private void simplePropertyChangeEvent(PropertyChangeEvent evt) {
			switch(evt.getPropertyName()) {
				case "squares": repaintSquares();
					break;
			}
		}

		private void indexedPropertyChangeEvent(IndexedPropertyChangeEvent evt) {
			switch(evt.getPropertyName()) {
				case "squares": repaintSquare(evt.getIndex());
					break;
			}
		}
		
	}

	/**
	 * Ranks = Rows - Filas // Files = Columns - Columnas
	 */
	private void setRanksAndFiles() {
		GridBagConstraints constraints;
		for (int ranks = 0; ranks < 8; ranks++) {
			JLabel squareLabel = new JLabel(Integer.toString(8 - ranks));
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = ranks;
			constraints.ipadx = 5;
			constraints.weighty = 1;
			add(squareLabel, constraints);
		}

		char file = 'a';
		for (int files = 1; files <= 8; files++) {
			JLabel squareLabel = new JLabel(String.valueOf(file));
			constraints = new GridBagConstraints();
			constraints.gridx = files;
			constraints.gridy = 8;
			constraints.weightx = 1;
			add(squareLabel, constraints);
			file++;
		}
	}

	private void setBoard() {
		int squaresIndex = 0;
		for (int ranks = 0; ranks < 8; ranks++) {
			for (int files = 1; files <= 8; files++) {
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.gridx = files;
				constraints.gridy = ranks;
				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.fill = GridBagConstraints.BOTH;
				JPanel square = createSquare(squaresIndex);
				add(square, constraints);
				squares[squaresIndex] = square;
				squaresIndex++;
			}
		}

		setPieces();
	}

	private void setPieces() {

	}

	private Color getColorByIndex(int index) {
		int rank = index / 8;
		int file = index % 8;
		if (rank % 2 == 0) {
			if (file % 2 == 0) {
				return Color.WHITE;
			}
			else {
				return Color.LIGHT_GRAY;
			}
		}
		else {
			if (file % 2 == 0) {
				return Color.LIGHT_GRAY;
			}
			else {
				return Color.WHITE;
			}
		} 
	}

	private JPanel createSquare(int index) {
		JPanel square = new JPanel();
		JLabel figure = new JLabel();
		square.add(figure);
		figure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boardVM.clickOnSquare(index);
			}
		});
		return square;
	}

	private void repaintSquares() {
		for (int i = 0; i < squares.length; i++) {
			repaintSquare(i);
			
//			square.revalidate();
//			square.repaint();
		}
	}
	
	private void repaintSquare(int index) {
		JPanel square = squares[index];
		
		JLabel figure = (JLabel) square.getComponent(0);
		
		Color originalSquareColor = getColorByIndex(index);
		
		SquareState state = boardVM.getSquare(index);
		figure.setIcon(ImagesFactory.getScaledImage(square, state.getImageName()));
		
		if (state.isSelected() || state.isMovePossible()) {
			if(originalSquareColor.equals(Color.LIGHT_GRAY)) {
				square.setBackground(new Color(186, 200, 167));
			} else {
				square.setBackground(new Color(214, 252, 145));
				// square.setBackground(new Color(214, 252, 227)); // Tonalidad blanca con tono verde				
			}
		}
		/* else if(state.isMovePossible()) {
			if(originalSquareColor.equals(Color.LIGHT_GRAY)) {
				square.setBackground(new Color(204, 204, 145));
			} else {
				square.setBackground(new Color(214, 252, 145)); // Tonalidad blanca con tono azul				
			}
		}*/
		else {
			square.setBackground(getColorByIndex(index));
		}
	}

}
