package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class Parser {

	/**
	 * @param args
	 */
	private static List<String> lines = new ArrayList<>();

	private static ArrayList<String> stopwords = new ArrayList<>();

	public static void loadfile(String file)
	{
		boolean intext=false;
		Vector<String> next = new Vector<String>();
		next.add("</TEXT>");
		next.add("<DOC>");
		next.add("</DOC>");
		next.add("<DOCNO>");
		next.add("</DOCNO>");
		next.add("<FILIED>");
		next.add("</FILIED>");
		next.add("<FIRST>");
		next.add("</FIRST>");
		next.add("<SECOND>");
		next.add("</SECOND>");
		next.add("<HEAD>");
		next.add("</HEAD>");
		next.add("<BYLINE>");
		next.add("</BYLINE>");
		next.add("<DATELINE>");
		next.add("</DATELINE>");
		next.add("<HEAD>");
		next.add("</HEAD>");

		System.out.println("************* File: "+file+" ***********************");
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(file));
			try
			{
				String line = null;
				while ((line = input.readLine()) != null && line.length()!=0)
				{
					// On split la ligne
					if(line.equals(" ")) line = input.readLine();
					StringTokenizer token = new StringTokenizer(line, " ''``;,.\n\t\r");
					String firstToken = token.nextToken();

					for(int i=0; i<next.size(); i++)
					{
						//firstToken.
						if(firstToken.contains(next.get(i))) 
						{
							intext=false;
							break;
						}else if ("<TEXT>".equals(firstToken)) {
							intext=true;
							break;
						}
					}
					if (intext==true && !("<TEXT>".equals(firstToken))) 
					{
						//System.out.println(firstToken);
						if (!(stopwords.contains(firstToken.toLowerCase()))){
							lines.add(firstToken);
						}
						while(token.hasMoreTokens())
						{
							//System.out.println(token.nextToken());
							String nextToken = token.nextToken();
							if (!(stopwords.contains(nextToken.toLowerCase()))){
								lines.add(nextToken);
							}
						}
					}
				}
			} finally
			{
				input.close();
			}
		} catch (IOException ex)
		{
			System.err.println("Erreur:loadFile(" + file + "):" + ex.getMessage());
		}
	}

	public static void chargerStopWord(){

		List<String> lignes= null;
		try {
			lignes = Files.readAllLines(Paths.get("./src/doc/stopwords.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String ligne : lignes){
			stopwords.add(ligne);
		}
	}

	public void init(String docPath) {
		chargerStopWord();
		System.out.println("******file : "+docPath);
		loadfile(docPath);
		writeFile("./src/doc/AP890101_parser.txt");
	}
	
	public static void writeFile(String path){
		try {
			Files.write(Paths.get(path), lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static List<String> getLines() {
		return lines;
	}

}
