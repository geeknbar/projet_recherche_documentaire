package modele;

import java.io.File;
import java.util.ArrayList;

public class DirectoryBrowsing {

	private String path;
	private ArrayList<String> filesPath;
	private int i_files;
	private int i_subdirectory;

	public DirectoryBrowsing(String path) {
		this.path = path;
		this.filesPath = new ArrayList<String>();
		this.i_files = 0;
		this.i_subdirectory = 0;
	}

	public void loadFiles() {
		recursiveFunction(new File(this.path), filesPath);
	}

	public void recursiveFunction(File path, ArrayList<String> filesPath) {
		if (path.isDirectory()) {
			this.i_subdirectory++;
			File[] list = path.listFiles();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					recursiveFunction(list[i], filesPath);
				}
			} else {
				System.err.println(path + " : Reading error.");
			}
		} else {
			this.i_files++;
			String currentFilePath = path.getAbsolutePath();
			filesPath.add(currentFilePath);
		}
	}
	
	public String getFileName(String path) {
		String tokens[] = path.split("/");
		return tokens[tokens.length-1];
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<String> getFilesPath() {
		return filesPath;
	}

	public int getI_files() {
		return i_files;
	}

	public int getI_subdirectory() {
		return i_subdirectory;
	}

}
