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

/**
 * Classe Dictionary.
 * @author Dorian Coffinet
 * @author Thibault Gauthier
 * @author Yassine Badih
 */
public class Dictionary {

	/**
	 * Variable pour stocker le dictionnaire.
	 */
	private HashMap<String, HashSet<String>> dictionary;

	/**
	 * Constructeur de la classe Dictionary.
	 */
	public Dictionary() {
		dictionary = new HashMap<String, HashSet<String>>();
	}

	/**
	 * Méthode pour remplir le dictionnaire.
	 * @param stemmerFile Liste des mots stemmés à ajouter.
	 * @param docID Nom du document en traitement.
	 */
	public void fillDictionary(ArrayList<String> stemmerFile, String docID) {
		for (String word : stemmerFile) {
			this.addWord(word, docID);
		}
	}

	/**
	 * Méthode pour ajouter un mot dans le dictionnaire.
	 * @param word Mot à ajouter.
	 * @param docID Nom du document en traitement.
	 */
	public void addWord(String word, String docID) {	
		if (dictionary.containsKey(word)) {
			dictionary.get(word).add(docID);
		} else {
			HashSet<String> values = new HashSet<String>();
			values.add(docID);
			dictionary.put(word, values);
		}
	}
	
	/**
	 * Méthode pour écrire le dictionnaire dans un fichier.
	 * @param path Chemin du fichier pour écrire le dictionnaire.
	 */
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
			System.out.println("Erreur lors du stemming");
		}
	}
}
