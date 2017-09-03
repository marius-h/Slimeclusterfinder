package userinterface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tools.Slimefinder;

public class GUI {

	Slimefinder slimefinder;
	private JFrame frmSlimchunkFormationFinder;
	private JTextField inputSeed;
	private JLabel lblSeed, lblRange, lblMinChunks, lblMaxChunks, lblSettings, label;
	private JPanel panel, panel_1;
	private JSpinner inputRange, inputMin, inputMax;
	private JButton btnStartSearching;
	private static JCheckBox chckbxLargestCluster, chckbxSmallestCluster, chckbxClosestCluster;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmSlimchunkFormationFinder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		slimefinder = new Slimefinder();
		frmSlimchunkFormationFinder = new JFrame();
		frmSlimchunkFormationFinder.getContentPane().setBackground(Color.WHITE);
		frmSlimchunkFormationFinder.setResizable(false);
		frmSlimchunkFormationFinder.setTitle("Slimecluster Finder by Couch");
		frmSlimchunkFormationFinder.setBounds(100, 100, 328, 405);
		frmSlimchunkFormationFinder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSlimchunkFormationFinder.getContentPane().setLayout(null);
		frmSlimchunkFormationFinder.setIconImage(new ImageIcon(this.getClass().getResource("slime.png")).getImage());
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 50, 300, 194);
		frmSlimchunkFormationFinder.getContentPane().add(panel);
		panel.setLayout(new GridLayout(4, 2, 8, 10));
		
		lblSeed = new JLabel("Enter your seed:");
		lblSeed.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSeed.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(lblSeed);
		
		inputSeed = new JTextField();
		inputSeed.setForeground(new Color(0, 0, 0));
		inputSeed.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(inputSeed);
		inputSeed.setColumns(20);
		
		lblRange = new JLabel("Radius in chunks:");
		lblRange.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRange.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(lblRange);
		
		inputRange = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		panel.add(inputRange);
		
		lblMinChunks = new JLabel("Min slimechunks:");
		lblMinChunks.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMinChunks.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(lblMinChunks);
		
		inputMin = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		panel.add(inputMin);
		
		lblMaxChunks = new JLabel("Max slimechunks:");
		lblMaxChunks.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaxChunks.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(lblMaxChunks);
		
		inputMax = new JSpinner(new SpinnerNumberModel(100, 0, 10000, 1));		
		inputMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if ((int)inputMin.getValue() > (int)inputMax.getValue() )
					inputMin.setValue((int) inputMax.getValue());
			}
		});
		panel.add(inputMax);
		
		inputMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if ((int)inputMax.getValue() < (int)inputMin.getValue() )
					inputMax.setValue((int) inputMin.getValue());
			}
		});
		
		lblSettings = new JLabel("Output");
		lblSettings.setForeground(Color.WHITE);
		lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettings.setFont(new Font("Arial", Font.BOLD, 16));
		lblSettings.setBackground(new Color(107, 175, 90));
		lblSettings.setOpaque(true);
		lblSettings.setBounds(10, 255, 300, 28);
		frmSlimchunkFormationFinder.getContentPane().add(lblSettings);
		
		btnStartSearching = new JButton("Start");
		btnStartSearching.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					slimefinder.findFormations(inputSeed, inputRange, inputMin, inputMax, panel);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "You have to enter a valid seed", null, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnStartSearching.setBackground(new Color(125, 204, 106));
		btnStartSearching.setForeground(Color.WHITE);
		btnStartSearching.setFont(new Font("Arial", Font.BOLD, 16));
		btnStartSearching.setBounds(10, 339, 300, 28);
		frmSlimchunkFormationFinder.getContentPane().add(btnStartSearching);
		
		label = new JLabel("Settings");
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.BOLD, 16));
		label.setBackground(new Color(107, 175, 90));
		label.setBounds(10, 11, 300, 28);
		frmSlimchunkFormationFinder.getContentPane().add(label);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 294, 300, 34);
		frmSlimchunkFormationFinder.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		
		chckbxLargestCluster = new JCheckBox("largest");
		chckbxLargestCluster.setSelected(true);
		panel_1.add(chckbxLargestCluster);
		
		chckbxSmallestCluster = new JCheckBox("smallest");
		chckbxSmallestCluster.setSelected(true);
		panel_1.add(chckbxSmallestCluster);
		
		chckbxClosestCluster = new JCheckBox("closest");
		chckbxClosestCluster.setSelected(true);
		panel_1.add(chckbxClosestCluster);
	}
	
	public static JCheckBox getChckbxLargestCluster() {
		return chckbxLargestCluster;
	}

	public static JCheckBox getChckbxSmallestCluster() {
		return chckbxSmallestCluster;
	}

	public static JCheckBox getChckbxClosestCluster() {
		return chckbxClosestCluster;
	}
}
