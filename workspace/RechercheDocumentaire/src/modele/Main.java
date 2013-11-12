package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class Main {

	/**
	 * @param args
	 */
	
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
						System.out.println(firstToken);
						while(token.hasMoreTokens())
						{
							System.out.println(token.nextToken());
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
	
	public static void main(String[] args) {
		int filenumber=101;
		//while(filenumber<103)
		//{
			String file = "AP890"+Integer.toString(filenumber);
			System.out.println("******file:"+file);
			loadfile(file);
			//filenumber++;
		//}
	}

}
