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
	private ArrayList<String> stemQuery;
	private Parser parser;

	public Query(){
		dictionary = new HashMap<String, HashSet<String>>();
		docIdResults = new HashMap<String, Integer>();
		stemQuery = new ArrayList<String>();
		parser = new Parser();
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
	
	private void queryProcess(String query) {
		ArrayList<String> stemQuery = parser.tokenizeLine(query);
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
	
	public ArrayList<String> sortResult() {
		ArrayList<String> sortResult = new ArrayList<String>();
		ArrayList<Integer> table = new ArrayList<Integer>();
		for (Entry<String, Integer> entry : docIdResults.entrySet()) {
			table.add(entry.getValue());
		}
		Collections.sort(table, Collections.reverseOrder());
		for (Integer i : table) {
			for (Entry<String, Integer> entry : docIdResults.entrySet()) {
				if (i == entry.getValue()) {
					sortResult.add(entry.getKey());
				}
			}
		}
		return sortResult;
	}

	public HashMap<String, HashSet<String>> getDictionary() {
		return dictionary;
	}
	
	public void displayResult() {
		int count = 0;
		for (String s : sortResult()) {
			System.out.println(s);
			count++;
			if (count == 5) {
				break;
			}
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Query q = new Query();
		q.loadDictionary("./bin/doc/dictionary.txt");
		q.queryProcess("politicians talk disparagingly of the ``Vietnam Syndrome''");
		for (String s : q.stemQuery) {
			System.out.println(s);
		}
		//q.displayResult();
		long stop = System.currentTimeMillis();
		System.out.println(stop - start);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("erreur lors du stemming");
		}

	}

}
