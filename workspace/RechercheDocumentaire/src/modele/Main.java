package modele;

import java.io.IOException;
import java.lang.reflect.Array;
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
//		ArrayList<String> listCorpus = new ArrayList<String>();
//		listCorpus.add("/home/dorian/Documents/projet_recherche_documentaire/workspace/RechercheDocumentaire/./bin/corpus/DOSSIER_2/AP890203.txt");
		int i = 0;
		Dictionary dic = new Dictionary();
		for (String filePath : listCorpus) {
			
//			System.out.println(filePath);
			Parser p = new Parser();
			p.init(filePath);
			Stemmer s = new Stemmer();
			//permet de comparer les deux méthodes entre celle qui est donner par le mec et la notre
			//il reste un souci avec les mots composés
//			s.init();
			s.stemmerArray(p.getLines());
			
			String docID = "890101"+String.valueOf(i);
			dic.fillDictionary(s.getStemmerFile2(),docID);
//			dic.displayInfos();
			s.setStemmerFile2(new ArrayList<String>());
			p.setLines(new ArrayList<String>());
			System.out.println(i);
			i++;
//			if (i==50) dic.displayInfos();
//			if (i==50) break;
		}
		long stop = System.currentTimeMillis();
		writeFileDictionnary("./src/doc/dictionary.txt", dic.getDictionary());
		System.out.println(stop-start);
		
		
	}
	
	public static void writeFileDictionnary(String path,HashMap<String, ArrayList<String> > hashMapDic){
		Path stemmerFilePath = Paths.get(path);
		ArrayList<String> arrayDic = new ArrayList<>();
		for(Entry<String, ArrayList<String>> entry : hashMapDic.entrySet()) {
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
