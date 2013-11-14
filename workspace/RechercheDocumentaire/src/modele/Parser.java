package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Classe Parser.
 * @author Dorian Coffinet
 * @author Thibault Gauthier
 * @author Yassine Badih
 */
public class Parser {

	/**
	 * Variables static pour le traitement des stopwords et des lignes.
	 */
	private static ArrayList<String> lines;
	private static ArrayList<String> stopwords;
	
	/**
	 * Variable Stemmer.
	 */
	private Stemmer s;

	@SuppressWarnings("serial")
	/**
	 * Constructeur de la classe Parser.
	 */
	public Parser() {
		
		lines = new ArrayList<String>();
		stopwords = new ArrayList<String>() {{
			add("</TEXT>");add("<DOC>");add("</DOC>");add("<DOCNO>");add("</DOCNO>");add("<FILIED>");
			add("</FILIED>");add("<FIRST>");add("</FIRST>");add("<SECOND>");add("</SECOND>");add("<HEAD>");
			add("</HEAD>");add("<BYLINE>");add("</BYLINE>");add("<DATELINE>");add("</DATELINE>");add("<HEAD>");add("</HEAD>");}};
		
		s = new Stemmer();
		loadStopWords();	
	}

	/**
	 * Méthode pour charger un fichier.
	 * @param file Chemin du fichier à charger.
	 */
	public void loadFile(String file) {
		lines.clear();
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			try {
				String line = null;
				while ((line = input.readLine()) != null && !line.replaceAll("[\\s\\p{Punct}]", "").trim().isEmpty()) {
					processLine(line);
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			System.err.println("Erreur:loadFile(" + file + "):" + ex.getMessage());
		}
	}
	
	/**
	 * Méthode pour charger les StopWords.
	 */
	public void loadStopWords() {
		List<String> lignes = null;
		try {
			lignes = Files.readAllLines(Paths.get("./bin/doc/stopwords.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String ligne : lignes) {
			stopwords.add(ligne);
		}
	}
	
	/**
	 * Méthode pour traiter une ligne d'un fichier.
	 * @param line Ligne à traiter.
	 */
	public void processLine(String line) {
		StringTokenizer tokens = new StringTokenizer(line, " ''``;,.\n\t\r");
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			if (!(stopwords.contains(token.toLowerCase()))) {
				s.stemmerWord(token);
			}
		}	
	}
	
	/**
	 * Méthode pour stemmer les mots d'une query.
	 * @param line Contient la query de l'utilisateur.
	 * @return Retourne la query stemmée.
	 */
	public ArrayList<String> stemLine(String line) {
		lines.clear();
		s.clearStemmerFile();
		StringTokenizer tokens = new StringTokenizer(line, " ''``;,.\n\t\r");
		String word = "";
		while(tokens.hasMoreTokens()) {
			word = tokens.nextToken();
			if(!stopwords.contains(word.toLowerCase())) {
				s.stemmerWord(word);
			}
		}
		return s.getStemmerFile();
	}
	
	public ArrayList<String> getStemmerFile() {
		return s.getStemmerFile();
	}
	
	public void clearStemmerFile() {
		s.clearStemmerFile();
	}
}
