package NERAnalyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import Classes.Location;
import OSMNominatim.Nominatim;

public class Classifier {

	static CorpusLoader myCL;
	static NERAnalyzer myNER;
	
	static Nominatim myNom;
	
	static List<Location> myLocs;
	static List<Location> trueLocs = new ArrayList<Location>();

	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
		    prop.load(is);
		    
			myCL = new CorpusLoader();
			myNom = new Nominatim();
			myLocs = new ArrayList<>();
			myCL.LoadCorpus("corpus/" + prop.getProperty("corpus_filename"), Integer.parseInt(prop.getProperty("number_of_tweets")));
			myNER = new NERAnalyzer();
			
			int found = 0;
			int deleted = 0;
			
			Iterator<String> iter = myCL.sentences.iterator();
			
			while (iter.hasNext()) {
				String tweet = (String) iter.next();
				myLocs.addAll(myNER.extractLocations(tweet));
			}
		
			Iterator<Location> locs = myLocs.iterator();
			
			while (locs.hasNext()) {
				Location loc = (Location) locs.next();
				
				if (trueLocs.contains(loc)) {
					found++;
				}
				else {
					if (!myNom.getUrlString(loc)) {
						System.out.println("*************************");
						System.out.println(loc.getName() + " not found and removed");
						System.out.println();
						deleted++;
					}
					else {
						found++;
						trueLocs.add(loc);
					}
				}
			}
			System.out.println("***********************************************************************");
			System.out.println("Encontradas " + found + " referencias en " + myCL.sentences.size() + " tweets");
			System.out.println("No encontradas y borradas " + deleted + " referencias");
			System.out.println("***********************************************************************");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
