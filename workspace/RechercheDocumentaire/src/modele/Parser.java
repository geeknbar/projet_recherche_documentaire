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

public class Parser {

	/**
	 * @param args
	 */
	private static ArrayList<String> lines;
	private static ArrayList<String> stopwords;
	private static ArrayList<String> next;
	private Stemmer s;
	private boolean intext;
	
	public Parser() {
		lines = new ArrayList<String>();
		stopwords = new ArrayList<String>();
		next = new ArrayList<String>();
		s = new Stemmer();
		loadStopWords();
		intext = false;
		next.add("</TEXT>");
		next.add("<DOC>");
		next.add("</DOC>");
		next.add("<DOCNO>");
		next.add("</DOCNO>");
		next.add("<FILIED>");
		next.add("</FILIED>");
		next.add("<FIRST>");
		next.add("</FIRST>");
		next.add("<SECOND>");
		next.add("</SECOND>");
		next.add("<HEAD>");
		next.add("</HEAD>");
		next.add("<BYLINE>");
		next.add("</BYLINE>");
		next.add("<DATELINE>");
		next.add("</DATELINE>");
		next.add("<HEAD>");
		next.add("</HEAD>");
	}

	public void loadFile(String file) {
		lines.clear();
		intext = false;
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
	
	public void processLine(String line) {
		StringTokenizer token = new StringTokenizer(line, " ''``;,.\n\t\r");
		String firstToken = token.nextToken();
		for (int i = 0; i < next.size(); i++) {
			if (firstToken.contains(next.get(i))) {
				intext = false;
				break;
			} else if ("<TEXT>".equals(firstToken)) {
				intext = true;
				break;
			}
		}
		if (intext == true && !("<TEXT>".equals(firstToken))) {
			if (!(stopwords.contains(firstToken.toLowerCase()))) {
				s.stemmerWord(firstToken);
			}
			while (token.hasMoreTokens()) {
				String nextToken = token.nextToken();
				if (!(stopwords.contains(nextToken.toLowerCase()))) {
					s.stemmerWord(nextToken);
				}
			}
		}
	}
	
	public ArrayList<String> stemLine(String line) {
		lines.clear();
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
