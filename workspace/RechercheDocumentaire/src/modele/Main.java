package modele;

import java.util.ArrayList;

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
		
		Parser p  = new Parser();
		
		Dictionary dic = new Dictionary();
		
		for (String filePath : listCorpus) {
			p.loadFile(filePath);
			String docID = direct.getFileName(filePath);
			dic.fillDictionary(p.getStemmerFile(), docID);
			// dic.displayInfos();
			p.clearStemmerFile();
			System.out.println(i);
			i++;
			// if (i==50) dic.displayInfos();
			//if (i==10) break;
		}
		long stop = System.currentTimeMillis();
		dic.writeFileDictionnary("./bin/doc/dictionary.txt");
		System.out.println(stop - start);
	}

}
