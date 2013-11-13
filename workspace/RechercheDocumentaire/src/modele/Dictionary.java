package modele;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Dictionary {

	private HashMap<String, HashSet<String>> dictionary;

	public Dictionary() {
		dictionary = new HashMap<String, HashSet<String>>();
	}

	public void fillDictionary(ArrayList<String> stemmerFile, String docID) {
		for (String word : stemmerFile) {
			this.addWord(word, docID);
		}

	}

	public void addWord(String word, String docID) {	
		if (dictionary.containsKey(word)) {
			dictionary.get(word).add(docID);
		} else {
			HashSet<String> values = new HashSet<String>();
			values.add(docID);
			dictionary.put(word, values);
		}
	}

	public HashMap<String, HashSet<String>> getDictionary() {
		return dictionary;
	}

	public void displayInfos() {

		System.out.println(this.getDictionary().toString());
		System.out.println("le dictionnaire a une taille de :"
				+ this.getDictionary().size());
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
