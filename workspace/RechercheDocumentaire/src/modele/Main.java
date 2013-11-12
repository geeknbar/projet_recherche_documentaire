package modele;

public class Main {

	public static void main(String[] args) {
		Parser p = new Parser();
		p.init("./src/doc/AP890101.txt");
		Stemmer s = new Stemmer();
		//permet de comparer les deux méthodes entre celle qui est donner par le mec et la notre
		//il reste un souci avec les mots composés
		s.init();
		s.stemmerArray(p.getLines());
	}
}
