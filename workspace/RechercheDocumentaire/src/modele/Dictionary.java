package modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {
	
	private HashMap<String, ArrayList<String>> dictionary;
	
	public Dictionary(){
		dictionary = new HashMap<String, ArrayList<String>>();
	}
	
	public void fillDictionary(ArrayList<String> stemmerFile, String docID){
		for(String word : stemmerFile){
			this.addWord(word, docID);
		}
		
	}
	
	public void addWord(String word, String docID ){
		if (dictionary.containsKey(word)){
			if(!(dictionary.get(word).contains(docID))){
				dictionary.get(word).add(docID);
			}
		}else{
			ArrayList<String> values = new ArrayList<>();
			values.add(docID);
			dictionary.put(word,values);
		}
		
	}

	public HashMap<String, ArrayList<String>> getDictionary() {
		return dictionary;
	}
	
	public void displayInfos(){

		System.out.println(this.getDictionary().toString());
		System.out.println("le dictionnaire a une taille de :" + this.getDictionary().size());
	}

}
