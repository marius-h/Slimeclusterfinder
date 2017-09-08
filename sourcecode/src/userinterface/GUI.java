package userinterface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
	private JCheckBox chckbxChangeCenterFrom;
	private JPanel panel_2;
	private JSpinner spinnerX;
	private JSpinner spinnerZ;

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
		frmSlimchunkFormationFinder.setBounds(100, 100, 328, 407);
		frmSlimchunkFormationFinder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSlimchunkFormationFinder.getContentPane().setLayout(null);
		frmSlimchunkFormationFinder.setIconImage(new ImageIcon(this.getClass().getResource("slime.png")).getImage());
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 50, 300, 194);
		frmSlimchunkFormationFinder.getContentPane().add(panel);
		panel.setLayout(new GridLayout(5, 2, 8, 10));
		
		lblSeed = new JLabel("Enter your seed.............");
		lblSeed.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(lblSeed);
		
		inputSeed = new JTextField();
		inputSeed.setForeground(new Color(0, 0, 0));
		inputSeed.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(inputSeed);
		inputSeed.setColumns(20);
		
		lblRange = new JLabel("Radius in chunks...................");
		lblRange.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(lblRange);
		
		inputRange = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		panel.add(inputRange);
		
		lblMinChunks = new JLabel("Min slimechunks................\r\n");
		lblMinChunks.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMinChunks.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(lblMinChunks);
		
		inputMin = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		panel.add(inputMin);
		
		lblMaxChunks = new JLabel("Max slimechunks.......................");
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
		chckbxChangeCenterFrom = new JCheckBox("center chunk.....................");
		chckbxChangeCenterFrom.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1)
					panel_2.setVisible(true);
				else
					panel_2.setVisible(false);
			}
		});
		chckbxChangeCenterFrom.setBackground(Color.WHITE);
		chckbxChangeCenterFrom.setFont(new Font("Arial", Font.PLAIN, 16));
		panel.add(chckbxChangeCenterFrom);
		chckbxChangeCenterFrom.setToolTipText("change center from 0/0 to a different chunk");
		
		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 2, 0, 0));
		panel_2.setVisible(false);
		
		spinnerX = new JSpinner();
		panel_2.add(spinnerX);
		
		spinnerZ = new JSpinner();
		panel_2.add(spinnerZ);
		
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
		lblSettings.setBounds(10, 256, 300, 28);
		frmSlimchunkFormationFinder.getContentPane().add(lblSettings);
		
		btnStartSearching = new JButton("Start");
		btnStartSearching.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					slimefinder.findFormations(Long.parseLong(inputSeed.getText()), (int) inputRange.getValue(), (int) inputMin.getValue(), (int) inputMax.getValue(), panel, (int) spinnerX.getValue(), (int) spinnerZ.getValue());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "You have to enter a valid seed", null, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnStartSearching.setBackground(new Color(125, 204, 106));
		btnStartSearching.setForeground(Color.WHITE);
		btnStartSearching.setFont(new Font("Arial", Font.BOLD, 16));
		btnStartSearching.setBounds(10, 340, 300, 28);
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
		panel_1.setBounds(10, 295, 300, 34);
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
