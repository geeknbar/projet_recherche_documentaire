package modele;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe DirectoryBrowsing.
 * @author Dorian Coffinet
 * @author Thibault Gauthier
 * @author Yassine Badih
 */
public class DirectoryBrowsing {

	/**
	 * Variable contenant le chemin du corpus.
	 */
	private String path;
	
	/**
	 * Variable contenant la liste des fichiers du corpus.
	 */
	private ArrayList<String> filesPath;

	/**
	 * Constructeur de la classe DirectoryBrowsing.
	 * @param path Chemin du corpus.
	 */
	public DirectoryBrowsing(String path) {
		this.path = path;
		this.filesPath = new ArrayList<String>();
	}

	/**
	 * Méthode pour lancer le parcours récursif du corpus.
	 */
	public void loadFiles() {
		recursiveFunction(new File(this.path), filesPath);
	}

	/**
	 * Parcours récursif du corpus.
	 * @param path Chemin du dossier à traiter.
	 * @param filesPath Liste des fichiers trouvés.
	 */
	public void recursiveFunction(File path, ArrayList<String> filesPath) {
		if (path.isDirectory()) {
			File[] list = path.listFiles();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					recursiveFunction(list[i], filesPath);
				}
			} else {
				System.err.println(path + " : Reading error.");
			}
		} else {
			String currentFilePath = path.getAbsolutePath();
			filesPath.add(currentFilePath);
		}
	}
	
	/**
	 * Méthode pour extraire le nom du fichier dans un chemin.
	 * @param path Chemin du fichier.
	 * @return Retourne le nom du fichier.
	 */
	public String getFileName(String path) {
		String tokens[] = path.split("/");
		return tokens[tokens.length-1];
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<String> getFilesPath() {
		return filesPath;
	}
}
