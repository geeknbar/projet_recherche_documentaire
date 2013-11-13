package modele;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String path = "./bin/corpus";
		DirectoryBrowsing direct = new DirectoryBrowsing(path);
		direct.loadFiles();
		ArrayList<String> listCorpus = direct.getFilesPath();
		// ArrayList<String> listCorpus = new ArrayList<String>();
		// listCorpus.add("/home/dorian/Documents/projet_recherche_documentaire/workspace/RechercheDocumentaire/./bin/corpus/DOSSIER_2/AP890203.txt");
		int i = 0;
		Dictionary dic = new Dictionary();
		for (String filePath : listCorpus) {

			Parser p = new Parser();
			p.init(filePath);
			Stemmer s = new Stemmer();
			s.stemmerArray(p.getLines());

			String docID = direct.getFileName(filePath);
			dic.fillDictionary(s.getStemmerFile(), docID);
			// dic.displayInfos();
			s.setStemmerFile(new ArrayList<String>());
			p.setLines(new ArrayList<String>());
			System.out.println(i);
			i++;
			// if (i==50) dic.displayInfos();
			//if (i==10) break;
		}
		long stop = System.currentTimeMillis();
		writeFileDictionnary("./src/doc/dictionary.txt", dic.getDictionary());
		System.out.println(stop - start);

	}

	public static void writeFileDictionnary(String path,
			HashMap<String, ArrayList<String>> hashMapDic) {
		Path stemmerFilePath = Paths.get(path);
		ArrayList<String> arrayDic = new ArrayList<>();
		for (Entry<String, ArrayList<String>> entry : hashMapDic.entrySet()) {
			String cle = entry.getKey();
			ArrayList<String> valeur = entry.getValue();
			String wordDocId = cle + valeur.toString();
			arrayDic.add(wordDocId);
			// traitements
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
