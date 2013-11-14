package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Query {

	private HashMap<String, HashSet<String>> dictionary;
	private HashMap<String, Integer> docIdResults;
	private ArrayList<String> docIdResultsBoolean;
	private ArrayList<String> stemQuery;
	private Parser parser;
	private int totalDocFind;

	public Query(){
		dictionary = new HashMap<String, HashSet<String>>();
		docIdResults = new HashMap<String, Integer>();
		docIdResultsBoolean = new ArrayList<String>();
		stemQuery = new ArrayList<String>();
		parser = new Parser();
		totalDocFind = 0;
	}

	public void loadDictionary(String path){
		try {
			BufferedReader input = new BufferedReader(new FileReader(path));
			String line;
			while ((line = input.readLine()) != null) {
				String[] parts = line.split("\\[");
				String words = parts[0];
				String docIDs = parts[1];
				docIDs=docIDs.replaceAll("\\]", "").trim();
				String[] docIDsparse = docIDs.split(",");
				HashSet<String> docIdValues = new HashSet<String>();

				for (int i = 0; i < docIDsparse.length; i++) {
					docIdValues.add(docIDsparse[i].trim());
				}
				dictionary.put(words, docIdValues);
			}
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
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
	
	public void queryProcessBoolean(String query) {
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
			p1 = searchWord(stemQuery.get(0));
			p2 = searchWord(stemQuery.get(1));
		} else if (query.contains("|")) {
			operator = "|";
			query = query.replaceAll("\\|", " ");
			stemQuery = parser.stemLine(query);
			p1 = searchWord(stemQuery.get(0));
			p2 = searchWord(stemQuery.get(1));
		} else {
			queryProcess(query);
		}
		
		switch(operator) {
			case "": queryProcess(query);break;
			case "&": docIdResultsBoolean = intersect(p1, p2); break;
			case "|": docIdResultsBoolean = union(p1, p2); break;
		}
	}
	
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
	
	public ArrayList<String> intersect(ArrayList<String> p1, ArrayList<String> p2) {
		ArrayList<String> answer = new ArrayList<String>();
		Collections.sort(p1);
		Collections.sort(p2);
		String docId1, docId2;
		boolean isUndermost;
		
		while(!p1.isEmpty() && !p2.isEmpty()) {
			docId1 = p1.get(0); docId2 = p2.get(0);
			isUndermost = false;
			if (docId1.compareTo(docId2) < 0) {isUndermost = true;}
			
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
	
	public ArrayList<String> union(ArrayList<String> x, ArrayList<String> y) {
		ArrayList<String> answer = new ArrayList<String>();
		Collections.sort(x);
		Collections.sort(y);
		String docId1, docId2;
		boolean isUndermost;
		do {
			docId1 = x.get(0); docId2 = y.get(0);
			isUndermost = false;
			if (docId1.compareTo(docId2) < 0) {isUndermost = true;}
			
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
		} while(!x.isEmpty() && !y.isEmpty());
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
				if (i == entry.getValue() && !sortResult.contains(entry.getKey())) {
					sortResult.add(entry.getKey());
					temp.remove(entry.getKey());
				}
			}
		}
		return sortResult;
	}

	public HashMap<String, HashSet<String>> getDictionary() {
		return dictionary;
	}
	
	public String displayResult() {
		String result = "";
		if (docIdResults.size() > 0) {
			totalDocFind = docIdResults.size();
			for (String s : sortResult()) {
				result = result + s+ "\n";
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

	public void writeFileDictionnary(String path) {
		Path stemmerFilePath = Paths.get(path);
		ArrayList<String> arrayDic = new ArrayList<>();
		for (Entry<String, HashSet<String>> entry : dictionary.entrySet()) {
			String cle = entry.getKey();
			HashSet<String> valeur = entry.getValue();
			String wordDocId = cle + valeur.toString();
			arrayDic.add(wordDocId);
		}
		try {
			Files.write(stemmerFilePath, arrayDic, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("erreur lors du stemming");
		}

	}

	public int getTotalDocFind() {
		return totalDocFind;
	}
	
}
