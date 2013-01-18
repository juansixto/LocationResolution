package NERAnalyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import YahooGeoPlanet.YahooGp;

import Classes.Location;
import Geonames.Geonames;
import GoogleReverseCoder.GoogleRC;
import OSMNominatim.Nominatim;

public class WebServices {

	static CorpusLoader myCL;
	static Nominatim myNom;
	
	static List<Location> myLocs;
	static List<Location> trueLocs = new ArrayList<Location>();
	static List<Location> falseLocs = new ArrayList<Location>();

	public static void main(String[] args) throws InterruptedException {
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
		    prop.load(is);
		    
			myCL = new CorpusLoader();
			myNom = new Nominatim();  //Change
			myLocs = new ArrayList<>();
			myCL.LoadCorpus("corpus/" + prop.getProperty("corpus_filename"), Integer.parseInt(prop.getProperty("number_of_tweets")));
			
			int found = 0;
			int deleted = 0;
			int request = 0;
			int prog = 0;
			
			
			Iterator<String> iter = myCL.sentences.iterator();
			
			while (iter.hasNext()) {
				String tweet = (String) iter.next();
				myLocs.add(new Location(tweet));
				
			}
		
			Iterator<Location> locs = myLocs.iterator();
			
			
			
			while (locs.hasNext()) {
				System.out.println("Progreso: " + (prog++*100/myLocs.size()) + "%");
				Location loc = (Location) locs.next();
				
				if (trueLocs.contains(loc)) {
					found++;
				}
				else {
					if(falseLocs.contains(loc)){
						deleted++;
					}
					else{
						if (!myNom.getUrlString(loc)) {
							System.out.println("*************************");
							System.out.println(loc.getName() + " not found and removed");
							System.out.println();
							request++;
							deleted++;
							falseLocs.add(loc);
						}
						else {
							request++;
							found++;
							trueLocs.add(loc);
						}
					}
				}
			}
			System.out.println("***********************************************************************");
			System.out.println("Encontradas " + found + " referencias en " + myCL.sentences.size() + " tweets");
			System.out.println("Se han realizado "+ request + " consultas a OSMNominatim");
			System.out.println("No encontradas y borradas " + deleted + " referencias");
			System.out.println("Encontrados "+trueLocs.size() + " lugares únicos");
			System.out.println("Borrados " +falseLocs.size() +" lugares únicos");
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
