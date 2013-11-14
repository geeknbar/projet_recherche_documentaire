package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Map.Entry;

/**
 * Classe Query.
 * 
 * @author Dorian Coffinet
 * @author Thibault Gauthier
 * @author Yassine Badih
 */
public class Query {

	private HashMap<String, HashSet<String>> dictionary;
	private HashMap<String, Integer> docIdResults;
	private ArrayList<String> docIdResultsBoolean;
	private ArrayList<String> stemQuery;
	private Parser parser;
	private int totalDocFind;

	/**
	 * Constructeur de la classe Query.
	 */
	public Query() {
		dictionary = new HashMap<String, HashSet<String>>();
		docIdResults = new HashMap<String, Integer>();
		docIdResultsBoolean = new ArrayList<String>();
		stemQuery = new ArrayList<String>();
		parser = new Parser();
		totalDocFind = 0;
	}

	/**
	 * Méthode pour charger le dictionnaire.
	 * 
	 * @param path
	 *            Chemin du dictionnaire.
	 */
	public void loadDictionary(String path) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(path));
			String line;
			while ((line = input.readLine()) != null) {
				String[] parts = line.split("\\[");
				String words = parts[0];
				String docIDs = parts[1];
				docIDs = docIDs.replaceAll("\\]", "").trim();
				String[] docIDsparse = docIDs.split(",");
				HashSet<String> docIdValues = new HashSet<String>();

				for (int i = 0; i < docIDsparse.length; i++) {
					docIdValues.add(docIDsparse[i].trim());
				}
				dictionary.put(words, docIdValues);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour traiter une Query. Elle appelle les méthodes searchWord,
	 * queryProcess, intersect et union.
	 * 
	 * @param query
	 *            Query à traiter.
	 */
	public void queryProcessBoolean(String query) {
		docIdResults.clear();
		docIdResultsBoolean.clear();
		parser.clearStemmerFile();
		ArrayList<String> p1 = new ArrayList<String>();
		query = query.trim().replaceAll("&", " & ");
		query = query.trim().replaceAll("\\|", " \\| ");
		if (query.contains("&") || query.contains("|")) {
			StringTokenizer tokens = new StringTokenizer(query, " ");
			int nbElement = 0;
			String tempQuery = "";
			while (tokens.hasMoreElements()) {
				String s = tokens.nextToken();
				
				if ("&".equals(s)) {
					tempQuery += s;
				} else if ("|".equals(s)) {
					tempQuery += s;
				} else {
					ArrayList<String> tmp = parser.stemLine(s);
					if (!tmp.isEmpty()) {
						tempQuery += s;
						nbElement++;
					} else {
						tempQuery = tempQuery.substring(0, tempQuery.length()-1);
					}
				}
				if (nbElement == 2) {
					p1 = tempResultQuery(p1, tempQuery);
					nbElement = 1;
					tempQuery = "";
				}

			}
			docIdResultsBoolean = p1;
		} else {
			queryProcess(query);
		}

	}

	public ArrayList<String> tempResultQuery(ArrayList<String> temp,
			String query) {
		ArrayList<String> tempResults = new ArrayList<String>();

		if (temp.isEmpty()) {
			docIdResults.clear();
			docIdResultsBoolean.clear();
			parser.clearStemmerFile();
			ArrayList<String> p1 = new ArrayList<String>();
			ArrayList<String> p2 = new ArrayList<String>();

			String operator = "";
			if (query.contains("&")) {
				operator = "&";
				query = query.replaceAll("&", " ");
				stemQuery = parser.stemLine(query);
				String q1 = stemQuery.get(0);
				String q2 = stemQuery.get(1);
				p1 = searchWord(q1);
				p2 = searchWord(q2);
			} else if (query.contains("|")) {
				operator = "|";
				query = query.replaceAll("\\|", " ");
				stemQuery = parser.stemLine(query);
				String q1 = stemQuery.get(0);
				String q2 = stemQuery.get(1);
				p1 = searchWord(q1);
				p2 = searchWord(q2);
			}

			switch (operator) {
			case "&":
				docIdResultsBoolean = intersect(p1, p2);
				break;
			case "|":
				docIdResultsBoolean = union(p1, p2);
				break;
			}
			tempResults.addAll(docIdResultsBoolean);
		} else {
			docIdResults.clear();
			docIdResultsBoolean.clear();
			parser.clearStemmerFile();
			ArrayList<String> p1 = temp;
			ArrayList<String> p2 = new ArrayList<String>();
			String operator = "";
			if (query.contains("&")) {
				operator = "&";
				query = query.replaceAll("&", " ");
				stemQuery = parser.stemLine(query);
				p2 = searchWord(stemQuery.get(0));
			} else if (query.contains("|")) {
				operator = "|";
				query = query.replaceAll("\\|", " ");
				stemQuery = parser.stemLine(query);
				p2 = searchWord(stemQuery.get(0));
			}

			switch (operator) {
			case "&":
				docIdResultsBoolean = intersect(p1, p2);
				break;
			case "|":
				docIdResultsBoolean = union(p1, p2);
				break;
			}
			tempResults.addAll(docIdResultsBoolean);
		}
		return tempResults;
	}

	/**
	 * Méthode pour traiter une query simple (sans AND ni OR)
	 * 
	 * @param query
	 *            Query à traiter.
	 */
	public void queryProcess(String query) {
		stemQuery = parser.stemLine(query);
		for (String word : stemQuery) {
			if (dictionary.containsKey(word)) {
				for (String docID : dictionary.get(word)) {
					if (docIdResults.containsKey(docID)) {
						docIdResults.put(docID, docIdResults.get(docID) + 1);
					} else {
						docIdResults.put(docID, 1);
					}
				}
			}
		}
	}

	/**
	 * Méthode pour chercher les documents dans lesquels apparait le mot.
	 * 
	 * @param word
	 *            Mot à chercher.
	 * @return Retourne la liste des documents dans lesquels se trouve le mot.
	 */
	public ArrayList<String> searchWord(String word) {
		ArrayList<String> docIdResult = new ArrayList<String>();
		if (dictionary.containsKey(word)) {
			for (String docID : dictionary.get(word)) {
				if (docIdResults.containsKey(docID)) {
					docIdResults.put(docID, docIdResults.get(docID) + 1);
				} else {
					docIdResults.put(docID, 1);
				}
			}
		}
		for (Entry<String, Integer> entry : docIdResults.entrySet()) {
			docIdResult.add(entry.getKey());
		}
		docIdResults.clear();
		return docIdResult;
	}

	/**
	 * Méthode pour faire l'intersection de deux listes.
	 * 
	 * @param p1
	 *            Liste 1.
	 * @param p2
	 *            Liste 2.
	 * @return Retourne la liste d'intersection des deux listes.
	 */
	public ArrayList<String> intersect(ArrayList<String> p1,
			ArrayList<String> p2) {
		ArrayList<String> answer = new ArrayList<String>();
		Collections.sort(p1);
		Collections.sort(p2);
		String docId1, docId2;
		boolean isUndermost;

		while (!p1.isEmpty() && !p2.isEmpty()) {
			docId1 = p1.get(0);
			docId2 = p2.get(0);
			isUndermost = false;
			if (docId1.compareTo(docId2) < 0) {
				isUndermost = true;
			}

			if (docId1.equals(docId2)) {
				answer.add(docId1);
				p1.remove(0);
				p2.remove(0);
			} else if (isUndermost) {
				p1.remove(0);
			} else {
				p2.remove(0);
			}
		}

		return answer;
	}

	/**
	 * Méthode pour faire l'union de deux listes.
	 * 
	 * @param x
	 *            Liste 1.
	 * @param y
	 *            Liste 2.
	 * @return Retourne la liste d'union des deux listes.
	 */
	public ArrayList<String> union(ArrayList<String> x, ArrayList<String> y) {
		ArrayList<String> answer = new ArrayList<String>();
		Collections.sort(x);
		Collections.sort(y);
		String docId1, docId2;
		boolean isUndermost;
		do {
			docId1 = x.get(0);
			docId2 = y.get(0);
			isUndermost = false;
			if (docId1.compareTo(docId2) < 0) {
				isUndermost = true;
			}

			if (docId1.equals(docId2)) {
				answer.add(docId1);
				x.remove(0);
				y.remove(0);
			} else if (isUndermost) {
				answer.add(docId1);
				x.remove(0);
			} else {
				answer.add(docId2);
				y.remove(0);
			}
		} while (!x.isEmpty() && !y.isEmpty());
		if (x.isEmpty()) {
			for (String s : y) {
				answer.add(s);
			}
		} else if (y.isEmpty()) {
			for (String s : x) {
				answer.add(s);
			}
		}

		return answer;
	}

	/**
	 * Méthode pour trier le résultat de la méthode queryProcess.
	 * 
	 * @return Retourne le résultat trier par ordre d'importance des documents.
	 */
	public ArrayList<String> sortResult() {
		ArrayList<String> sortResult = new ArrayList<String>();
		ArrayList<Integer> table = new ArrayList<Integer>();
		for (Entry<String, Integer> entry : docIdResults.entrySet()) {
			table.add(entry.getValue());
		}
		Collections.sort(table, Collections.reverseOrder());
		HashMap<String, Integer> temp = new HashMap<String, Integer>();
		HashMap<String, Integer> temp2 = new HashMap<String, Integer>();
		temp.putAll(docIdResults);
		for (Integer i : table) {
			temp2.clear();
			temp2.putAll(temp);
			for (Entry<String, Integer> entry : temp2.entrySet()) {
				if (i == entry.getValue()
						&& !sortResult.contains(entry.getKey())) {
					sortResult.add(entry.getKey());
					temp.remove(entry.getKey());
				}
			}
		}
		return sortResult;
	}

	/**
	 * Méthode pour afficher les résultats.
	 * 
	 * @return Retourne une chaine contenant les noms de documents recherchés.
	 */
	public String displayResult() {
		String result = "";
		if (docIdResults.size() > 0) {
			totalDocFind = docIdResults.size();
			for (String s : sortResult()) {
				result = result + s + "\n";
			}
		} else if (docIdResultsBoolean.size() > 0) {
			totalDocFind = docIdResultsBoolean.size();
			for (String s : docIdResultsBoolean) {
				result = result + s + "\n";
			}
		} else {
			return "\nError in displayResult()\n";
		}
		return result;
	}

	public int getTotalDocFind() {
		return totalDocFind;
	}

}
