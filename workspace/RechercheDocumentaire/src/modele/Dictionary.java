package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {
	
	private HashMap<String, Integer> dictionary;
	
	public Dictionary(){
		dictionary = new HashMap<String, Integer>();
	}
	
	public void fillDictionary(ArrayList<String> stemmerFile){
		for(String word : stemmerFile){
			this.addWord(word, 1);
		}
		
	}
	
	public void addWord(String word, int docID){
		dictionary.put(word,docID);
	}

	public HashMap<String, Integer> getDictionary() {
		return dictionary;
	}
	
	public void displayInfos(){

		System.out.println(this.getDictionary().toString());
		System.out.println("le dictionnaire a une taille de :" + this.getDictionary().size());
	}

}
