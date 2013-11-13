package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JTextArea;


public class UIRechercheDoc extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIRechercheDoc frame = new UIRechercheDoc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UIRechercheDoc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 693, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_action = new JPanel();
		contentPane.add(panel_action, BorderLayout.SOUTH);
		panel_action.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btn_launch = new JButton("Launch");
		panel_action.add(btn_launch);
		
		JButton btn_quit = new JButton("Quit");
		panel_action.add(btn_quit);
		
		JPanel panel_corpus_querry = new JPanel();
		contentPane.add(panel_corpus_querry, BorderLayout.NORTH);
		panel_corpus_querry.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_corpus_querry.add(verticalStrut, BorderLayout.WEST);
		
		Component verticalGlue = Box.createVerticalGlue();
		panel_corpus_querry.add(verticalGlue, BorderLayout.EAST);
		
		JPanel panel_path = new JPanel();
		panel_corpus_querry.add(panel_path, BorderLayout.NORTH);
		panel_path.setLayout(new BoxLayout(panel_path, BoxLayout.X_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		panel_path.add(horizontalBox);
		
		JLabel lblNewLabel = new JLabel("File Path Corpus");
		horizontalBox.add(lblNewLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		
		textField = new JTextField();
		horizontalBox.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_2);
		
		JButton btnFile = new JButton("File");
		horizontalBox.add(btnFile);
		
		JPanel panel_querry = new JPanel();
		panel_corpus_querry.add(panel_querry, BorderLayout.SOUTH);
		panel_querry.setLayout(new BoxLayout(panel_querry, BoxLayout.X_AXIS));
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		panel_querry.add(horizontalBox_1);
		
		JLabel lblNewLabel_1 = new JLabel("Please enter your querry");
		horizontalBox_1.add(lblNewLabel_1);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_1);
		
		textField_1 = new JTextField();
		horizontalBox_1.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel panel_answers = new JPanel();
		contentPane.add(panel_answers, BorderLayout.CENTER);
		panel_answers.setLayout(new BoxLayout(panel_answers, BoxLayout.X_AXIS));
		
		Box verticalBox = Box.createVerticalBox();
		panel_answers.add(verticalBox);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);
		
		JLabel lblNewLabel_2 = new JLabel("Answers");
		horizontalBox_2.add(lblNewLabel_2);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_3);
		
		JTextArea textArea = new JTextArea();
		horizontalBox_3.add(textArea);
	}

}
