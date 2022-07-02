package com.mctschess.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mctschess.gui.panels.BoardPanel;
import com.mctschess.gui.viewmodels.BoardVM;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;

public class MainWindow extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JMenuBar mwMenuBar;
	private JMenu mnGame;
	private JMenuItem mntmNew;
	private JSeparator separator;
	private JMenuItem mntmExit;
	private JMenu mnMode;
	private ButtonGroup modeRdbuttonsGroup;
	private JRadioButtonMenuItem rdbtnmntmAI;
	private JRadioButtonMenuItem rdbtnmntm1vs1;
	private JPanel pnTimers;
	private JTextField txTimer1;
	private JTextField txTimer2;
	private BoardPanel boardPanel;
	
	private BoardVM boardVM;
	
	private boolean aiPlaying;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		this.boardVM = new BoardVM();		
		this.boardVM.addPropertyChangeListener(this);
		
		initialize();

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(MainWindow.class.getResource("/com/mctschess/gui/img/black_knight.png")));
		setResizable(false);
		setTitle("MCTS Chess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		setJMenuBar(getMWMenuBar());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnTimers(), BorderLayout.EAST);
		contentPane.add(getBoardPanel(), BorderLayout.CENTER);
		setLocationRelativeTo(null);
	}

	private void initalizeTimeCounters() {
		getTxWhiteTimer().setText("00:00");
		getTxBlackTimer().setText("00:00");
	}
	
	private void initializeModeRdbtns(boolean aiPlayingSelected) {
		getRdbtnmntmAI().setSelected(aiPlayingSelected);
		getRdbtnmntm1vs1().setSelected(!aiPlayingSelected);
	}
	
	private void initialize() {
		aiPlaying = true;
		initalizeTimeCounters();
		initializeModeRdbtns(aiPlaying);
		
		boardVM.reset(PieceColor.WHITE, aiPlaying);
		validate();
	}

	private JMenuBar getMWMenuBar() {
		if (mwMenuBar == null) {
			mwMenuBar = new JMenuBar();
			mwMenuBar.add(getMnGame());
			mwMenuBar.add(getMnMode());
		}
		return mwMenuBar;
	}

	private JMenu getMnGame() {
		if (mnGame == null) {
			mnGame = new JMenu("Game");
			mnGame.setMnemonic('G');
			mnGame.add(getMntmNew());
			mnGame.add(getSeparator());
			mnGame.add(getMntmExit());
		}
		return mnGame;
	}

	private JMenuItem getMntmNew() {
		if (mntmNew == null) {
			mntmNew = new JMenuItem("New");
			mntmNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					initialize();
				}
			});
			mntmNew.setMnemonic('N');
		}
		return mntmNew;
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}

	private JMenuItem getMntmExit() {
		if (mntmExit == null) {
			mntmExit = new JMenuItem("Exit");
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			mntmExit.setMnemonic('E');
		}
		return mntmExit;
	}

	private JMenu getMnMode() {
		if (mnMode == null) {
			mnMode = new JMenu("Mode");
			mnMode.setMnemonic('M');
			modeRdbuttonsGroup = new ButtonGroup();
			modeRdbuttonsGroup.add(getRdbtnmntmAI());
			modeRdbuttonsGroup.add(getRdbtnmntm1vs1());
			mnMode.add(getRdbtnmntmAI());
			mnMode.add(getRdbtnmntm1vs1());
		}
		return mnMode;
	}

	private JRadioButtonMenuItem getRdbtnmntmAI() {
		if (rdbtnmntmAI == null) {
			rdbtnmntmAI = new JRadioButtonMenuItem("AI");
			rdbtnmntmAI.setSelected(true);
			String[] playerOptions = new String[] {"White", "Black", "Cancel"};
			rdbtnmntmAI.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedOption = 0;
					selectedOption = JOptionPane.showOptionDialog(null, "Player color: ", "Player color selection", JOptionPane.DEFAULT_OPTION,  JOptionPane.QUESTION_MESSAGE, null, playerOptions, playerOptions[0]);
					// WHITE
					if(selectedOption == 0) {
						aiPlaying = true;
						initalizeTimeCounters();
						boardVM.reset(PieceColor.WHITE, true);
					} 
					// BLACK
					else if(selectedOption == 1) {
						aiPlaying = true;
						initalizeTimeCounters();
						boardVM.reset(PieceColor.BLACK, true);
					} 
					// Cancel
					else {
						JOptionPane.getRootFrame().dispose();
						initializeModeRdbtns(aiPlaying);
					}
				}
			});
			rdbtnmntmAI.setMnemonic('A');
		}
		return rdbtnmntmAI;
	}

	private JRadioButtonMenuItem getRdbtnmntm1vs1() {
		if (rdbtnmntm1vs1 == null) {
			rdbtnmntm1vs1 = new JRadioButtonMenuItem("1vs1");
			rdbtnmntm1vs1.setSelected(false);
			rdbtnmntm1vs1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					aiPlaying = false;
					initalizeTimeCounters();
					boardVM.reset(PieceColor.WHITE, false);
				}
			});
			rdbtnmntm1vs1.setMnemonic('S');
		}
		return rdbtnmntm1vs1;
	}

	private JPanel getPnTimers() {
		if (pnTimers == null) {
			pnTimers = new JPanel();
			pnTimers.setLayout(new BorderLayout(0, 0));
			pnTimers.add(getTxBlackTimer(), BorderLayout.NORTH);
			pnTimers.add(getTxWhiteTimer(), BorderLayout.SOUTH);
		}
		return pnTimers;
	}

	private JTextField getTxBlackTimer() {
		if (txTimer1 == null) {
			txTimer1 = new JTextField();
			txTimer1.setHorizontalAlignment(SwingConstants.CENTER);
			txTimer1.setText("00:00");
			txTimer1.setEditable(false);
			txTimer1.setFont(new Font("Tahoma", Font.BOLD, 14));
			txTimer1.setColumns(6);
		}
		return txTimer1;
	}

	private JTextField getTxWhiteTimer() {
		if (txTimer2 == null) {
			txTimer2 = new JTextField();
			txTimer2.setHorizontalAlignment(SwingConstants.CENTER);
			txTimer2.setText("00:00");
			txTimer2.setEditable(false);
			txTimer2.setFont(new Font("Tahoma", Font.BOLD, 14));
			txTimer2.setColumns(6);
		}
		return txTimer2;
	}

	private BoardPanel getBoardPanel() {
		if (boardPanel == null) {
			boardPanel = new BoardPanel(boardVM);
		}
		return boardPanel;
	}
	
	private String millisecondsToTime(long time) {
		long seconds = time % 60;
		long minutes = time / 60;
		return String.format("%02d:%02d", minutes, seconds);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("whiteTime")) 
		{
			getTxWhiteTimer().setText(millisecondsToTime((Long)evt.getNewValue())); 
		}
		else if (evt.getPropertyName().equals("blackTime")) {
			getTxBlackTimer().setText(millisecondsToTime((Long)evt.getNewValue()));
		}
	}
}
