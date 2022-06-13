package com.mctschess.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mctschess.gui.panels.BoardPanel;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnGame;
	private JMenuItem mntmNew;
	private JSeparator separator;
	private JMenuItem mntmExit;
	private JMenu mnMode;
	private JRadioButtonMenuItem rdbtnmntmMachine;
	private JRadioButtonMenuItem rdbtnmntm1vs1;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JSeparator separator_1;
	private JMenuItem mntmContent;
	private JPanel pnTimers;
	private JTextField txTimer1;
	private JTextField txTimer2;
	private BoardPanel boardPanel;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		initialize();

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(MainWindow.class.getResource("/com/mctschess/gui/img/black_knight.png")));
		setResizable(false);
		setTitle("MCTS Chess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnTimers(), BorderLayout.EAST);
		contentPane.add(getBoardPanel(), BorderLayout.CENTER);
		setLocationRelativeTo(null);
	}

	private void initialize() {
		validate();
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnGame());
			menuBar.add(getMnMode());
			menuBar.add(getMnHelp());
		}
		return menuBar;
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
			mnMode.add(getRdbtnmntmMachine());
			mnMode.add(getRdbtnmntm1vs1());
		}
		return mnMode;
	}

	private JRadioButtonMenuItem getRdbtnmntmMachine() {
		if (rdbtnmntmMachine == null) {
			rdbtnmntmMachine = new JRadioButtonMenuItem("Machine");
			rdbtnmntmMachine.setMnemonic('M');
		}
		return rdbtnmntmMachine;
	}

	private JRadioButtonMenuItem getRdbtnmntm1vs1() {
		if (rdbtnmntm1vs1 == null) {
			rdbtnmntm1vs1 = new JRadioButtonMenuItem("1vs1");
			rdbtnmntm1vs1.setMnemonic('S');
		}
		return rdbtnmntm1vs1;
	}

	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Help");
			mnHelp.setMnemonic('H');
			mnHelp.add(getMntmAbout());
			mnHelp.add(getSeparator_1());
			mnHelp.add(getMntmContent());
		}
		return mnHelp;
	}

	private JMenuItem getMntmAbout() {
		if (mntmAbout == null) {
			mntmAbout = new JMenuItem("About");
			mntmAbout.setMnemonic('A');
		}
		return mntmAbout;
	}

	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
		}
		return separator_1;
	}

	private JMenuItem getMntmContent() {
		if (mntmContent == null) {
			mntmContent = new JMenuItem("Content");
			mntmContent.setMnemonic('C');
		}
		return mntmContent;
	}

	private JPanel getPnTimers() {
		if (pnTimers == null) {
			pnTimers = new JPanel();
			pnTimers.setLayout(new BorderLayout(0, 0));
			pnTimers.add(getTxTimer1(), BorderLayout.NORTH);
			pnTimers.add(getTxTimer2(), BorderLayout.SOUTH);
		}
		return pnTimers;
	}

	private JTextField getTxTimer1() {
		if (txTimer1 == null) {
			txTimer1 = new JTextField();
			txTimer1.setHorizontalAlignment(SwingConstants.CENTER);
			txTimer1.setText("5:00");
			txTimer1.setEditable(false);
			txTimer1.setFont(new Font("Tahoma", Font.BOLD, 14));
			txTimer1.setColumns(6);
		}
		return txTimer1;
	}

	private JTextField getTxTimer2() {
		if (txTimer2 == null) {
			txTimer2 = new JTextField();
			txTimer2.setHorizontalAlignment(SwingConstants.CENTER);
			txTimer2.setText("5:00");
			txTimer2.setEditable(false);
			txTimer2.setFont(new Font("Tahoma", Font.BOLD, 14));
			txTimer2.setColumns(6);
		}
		return txTimer2;
	}

	private BoardPanel getBoardPanel() {
		if (boardPanel == null) {
			boardPanel = new BoardPanel();
		}
		return boardPanel;
	}
}
