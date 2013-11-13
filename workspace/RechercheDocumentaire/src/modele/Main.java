package modele;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		String path = "./bin/corpus";
		DirectoryBrowsing direct = new DirectoryBrowsing(path);
		direct.loadFiles();
		ArrayList<String> listCorpus = direct.getFilesPath();
		for (String filePath : listCorpus) {
			Parser p = new Parser();
			p.init(filePath);
			Stemmer s = new Stemmer();
			//permet de comparer les deux méthodes entre celle qui est donner par le mec et la notre
			//il reste un souci avec les mots composés
			s.init();
			s.stemmerArray(p.getLines());
			Dictionary dic = new Dictionary();
			int docID = 890101;
			dic.fillDictionary(s.getStemmerFile2(),docID);
			dic.displayInfos();
		}
		
	}
}
