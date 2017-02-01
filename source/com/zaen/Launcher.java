package com.zaen;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

public class Launcher {

	/**
	 * @author: Zaen Khilji
	 */

	private static JFrame frame;
	private JTextPane txtpnPort;
	private JTextPane textPane_1;

	public static JTextField IP;
	public static JTextField PORT;
	public static JCheckBox SSL;
	public static JTextArea CONSOLE;

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher window = new Launcher();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		while (true) {
			Thread.sleep(600);
			process();
		}
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public Launcher() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		frame = new JFrame(Settings.NAME + " | By Zaen Khilji | [0MB/0MB memory]");
		frame.setBounds(100, 100, 480, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		IP = new JTextField();
		IP.setText("127.0.0.1");
		IP.setBounds(61, 11, 86, 20);
		frame.getContentPane().add(IP);

		JButton btnStart = new JButton("Start");
		btnStart.setBounds(10, 410, 454, 34);
		frame.getContentPane().add(btnStart);

		JTextPane txtpnThreads = new JTextPane();
		txtpnThreads.setText("IP");
		txtpnThreads.setBounds(10, 11, 45, 20);
		txtpnThreads.setOpaque(false);
		txtpnThreads.setEditable(false);
		frame.getContentPane().add(txtpnThreads);

		txtpnPort = new JTextPane();
		txtpnPort.setText("Port");
		txtpnPort.setOpaque(false);
		txtpnPort.setEditable(false);
		txtpnPort.setBounds(157, 11, 45, 20);
		frame.getContentPane().add(txtpnPort);

		PORT = new JTextField();
		PORT.setText("8080");
		PORT.setColumns(10);
		PORT.setBounds(192, 11, 86, 20);
		frame.getContentPane().add(PORT);

		textPane_1 = new JTextPane();
		textPane_1.setText("Threads");
		textPane_1.setOpaque(false);
		textPane_1.setEditable(false);
		textPane_1.setBounds(291, 11, 45, 20);
		frame.getContentPane().add(textPane_1);

		SSL = new JCheckBox("Use SSL");
		SSL.setBounds(10, 38, 137, 23);
		frame.getContentPane().add(SSL);

		CONSOLE = new JTextArea();
		CONSOLE.setBounds(10, 68, 454, 331);
		CONSOLE.setEditable(false);
		CONSOLE.setCaretPosition(CONSOLE.getDocument().getLength());
		frame.getContentPane().add(CONSOLE);

		JSpinner spinner = new JSpinner(
				new SpinnerNumberModel(new Double(100), new Double(10), new Double(50000), new Double(10)));
		spinner.setBounds(346, 11, 86, 20);
		frame.getContentPane().add(spinner);

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", Settings.NAME,
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					System.exit(1);
				else
					return;
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "JStresser [By Zaen Khilji]\nCopyright (C) Varago Inc.",
						Settings.NAME, 1);
			}
		});
		mnHelp.add(mntmAbout);

		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		mntmInstructions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "Todo", Settings.NAME, 1);
			}
		});
		mnHelp.add(mntmInstructions);
		frame.setVisible(true);

		Settings.log("//**********************************\\\\");
		Settings.log("   JStresser [By Zaen Khilji]");
		Settings.log("   Copyright (C) Varago Inc.");
		Settings.log("   Running " + System.getProperty("os.name") + " on a(n) " + System.getProperty("os.arch")
				+ " architecture");
		Settings.log("   Java version is " + System.getProperty("java.version"));
		Settings.log("\\\\**********************************//");
		Settings.log("");

		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String ip = IP.getText();
				String port = PORT.getText();
				double threads = (double) spinner.getValue();
				if (btnStart.getText().equalsIgnoreCase("Start")) {
					if (ip.equals("127.0.0.1")) {
						Settings.log("You are attempting to send threads to localhost? Are you stupid?");
						return;
					}
					Settings.log("Sending" + (SSL.isSelected() ? " (SSL)" : "") + " "
							+ NumberFormat.getNumberInstance(Locale.UK).format(threads) + " threads to " + ip + ":"
							+ port);

					for (int i = 0; i < threads; i++)
						try {
							new PacketThreads().start();
						} catch (Exception e) {
							Settings.log("[ERROR] " + e);
						}

					btnStart.setText("Stop");
				} else {
					Settings.log("Stopped at " + ip + ":" + port);

					btnStart.setText("Start");
					try {
						new PacketThreads().interrupt();
					} catch (Exception e) {
						Settings.log("[ERROR] " + e);
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static void process() {
		System.gc();
		Runtime rt = Runtime.getRuntime();
		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1000;
		frame.setTitle(
				Settings.NAME + " | By Zaen Khilji | ["
						+ NumberFormat.getNumberInstance(Locale.UK).format(usedMB) + "MB/" + NumberFormat
								.getNumberInstance(Locale.UK).format(((Runtime.getRuntime().maxMemory()) / 10000) / 5)
				+ "MB memory]");
	}
}