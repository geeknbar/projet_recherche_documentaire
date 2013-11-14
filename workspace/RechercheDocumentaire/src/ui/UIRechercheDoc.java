package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ScrollPaneConstants;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextArea;

import modele.Dictionary;
import modele.DirectoryBrowsing;
import modele.Parser;
import modele.Query;


public class UIRechercheDoc extends JFrame implements ActionListener {

	/**
	 * Classe pour l'IU
	 * @author Dorian Coffinet
	 * @author Thibault Gauthier
	 * @author Yassine Badih
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField pathCorpus;
	private JTextField user_query;
	private JButton btn_file;
	private JButton btn_create;
	private JButton btn_proceed;
	private JButton btn_load;
	private JTextArea results;
	private boolean dictionaryLoad = false;
	private Query query;
	private JCheckBox isDicLoad;
	private JTextField pathDictionary;
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

	public void createDictionaryFromCorpus(String path){
		DirectoryBrowsing direct = new DirectoryBrowsing(path);
		direct.loadFiles();
		ArrayList<String> listCorpus = direct.getFilesPath();
		Parser p  = new Parser();

		Dictionary dic = new Dictionary();

		for (String filePath : listCorpus) {
			p.loadFile(filePath);
			String docID = direct.getFileName(filePath);
			dic.fillDictionary(p.getStemmerFile(), docID);
			p.clearStemmerFile();
		}
		dic.writeFileDictionnary("./bin/doc/dictionary.txt");
	}

	public void proceedQuery(){
		query.queryProcessBoolean(user_query.getText());
	}
	/**
	 * Create the frame.
	 */
	public UIRechercheDoc() {

		query = new Query();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 693, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_action = new JPanel();
		contentPane.add(panel_action, BorderLayout.SOUTH);
		panel_action.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btn_create = new JButton("Create Dictionary");
		panel_action.add(btn_create);

		btn_load = new JButton("Load Dictionary");

		panel_action.add(btn_load);

		btn_proceed = new JButton("Proceed Query");
		panel_action.add(btn_proceed);

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

		pathCorpus = new JTextField();
		pathCorpus.setText("./bin/corpus");
		horizontalBox.add(pathCorpus);
		pathCorpus.setColumns(10);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_2);

		btn_file = new JButton("File");
		horizontalBox.add(btn_file);

		Component horizontalStrut1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut1);

		JLabel dictionaryLoad = new JLabel("Dictionary load");
		horizontalBox.add(dictionaryLoad);

		isDicLoad = new JCheckBox();
		isDicLoad.setSelected(false);
		isDicLoad.setEnabled(false);
		horizontalBox.add(isDicLoad);

		btn_file.addActionListener(this);
		btn_create.addActionListener(this);
		btn_proceed.addActionListener(this);
		btn_load.addActionListener(this);

		JPanel panel = new JPanel();
		panel_corpus_querry.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		Box horizontalBox_4 = Box.createHorizontalBox();
		panel.add(horizontalBox_4);

		JLabel lblFilePathDictionary = new JLabel("File Path Dictionary");
		horizontalBox_4.add(lblFilePathDictionary);

		pathDictionary = new JTextField();
		pathDictionary.setText("./bin/doc/dictionary.txt");
		horizontalBox_4.add(pathDictionary);
		pathDictionary.setColumns(10);

		JPanel panel_querry = new JPanel();
		panel_corpus_querry.add(panel_querry, BorderLayout.SOUTH);
		panel_querry.setLayout(new BoxLayout(panel_querry, BoxLayout.X_AXIS));

		Box horizontalBox_1 = Box.createHorizontalBox();
		panel_querry.add(horizontalBox_1);

		JLabel lblNewLabel_1 = new JLabel("Please enter your query");
		horizontalBox_1.add(lblNewLabel_1);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_1);

		user_query = new JTextField();
		horizontalBox_1.add(user_query);
		user_query.setColumns(10);

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


		results = new JTextArea();
		results.setEditable(false); // set textArea non-editable
		JScrollPane scroll = new JScrollPane(results);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		horizontalBox_3.add(scroll);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_file) {
			JFileChooser chooser;
			chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Select Corpus");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false); 
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
				pathCorpus.setText(chooser.getSelectedFile().toString());
			}
		}
		if (e.getSource() == btn_create) {
			createDictionaryFromCorpus(pathCorpus.getText());
			query.loadDictionary("./bin/doc/dictionary.txt");
			JOptionPane.showMessageDialog(this, "Dictionary load, you can ask a query");
			dictionaryLoad = true;
			isDicLoad.setSelected(true);
		}
		if (e.getSource() == btn_proceed) {
			results.setText("");
			if(dictionaryLoad){
				proceedQuery();
				String result = query.displayResult();
				results.append("We find "+ query.getTotalDocFind() +" relative documents for your query.\n");
				results.append(result);
				results.setCaretPosition(0);
			}else{
				JOptionPane.showMessageDialog(this, "Please load a dictionary to proceed query");
			}
		}
		if (e.getSource() == btn_load) {
			query.loadDictionary("./bin/doc/dictionary.txt");
			dictionaryLoad = true;
			isDicLoad.setSelected(true);
		}
	}

}
