package modele;

import java.io.File;
import java.util.ArrayList;

public class DirectoryBrowsing {

	private String path;
	private ArrayList<String> filesPath;

	public DirectoryBrowsing(String path) {
		this.path = path;
		this.filesPath = new ArrayList<String>();
	}

	public void loadFiles() {
		recursiveFunction(new File(this.path), filesPath);
	}

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
