package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Querry {

	private HashMap<String, HashSet<String>> dictionary;
	private ArrayList<String> stemQuery;
	private Parser parser;

	public Querry(){
		dictionary = new HashMap<String, HashSet<String>>();
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
			writeFileDictionnary("./bin/doc/dictionaryAfterLoad.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void queryProcess(String query) {
		String stemQuery = parser.tokenizeLine(query);
		System.out.println(stemQuery);
	}

	public HashMap<String, HashSet<String>> getDictionary() {
		return dictionary;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Querry q = new Querry();
		q.loadDictionary("./bin/doc/dictionary.txt");
		q.queryProcess("politicians talk disparagingly of the ``Vietnam Syndrome''");
		for (String s : q.stemQuery) {
			System.out.println(s);
		}
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
