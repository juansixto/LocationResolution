package NERAnalyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Classes.Location;
import OSMNominatim.Nominatim;

public class Classifier {

	static CorpusLoader myCL;
	static NERAnalyzer myNER;
	static List<Location> myLocs;
	static Nominatim myNom;
	static List<Location> trueLocs;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		myCL = new CorpusLoader();
		myNom = new Nominatim();
		myLocs = new ArrayList<>();
		myCL.LoadCorpus(".\\Corpus\\100tweetsCorpus.txt");
		myNER = new NERAnalyzer();
		int found = 0;
		int deleted = 0;
		Iterator iter = myCL.sentences.iterator();
		while (iter.hasNext())
		{
		   String tweet = (String) iter.next();
		   myLocs.addAll(myNER.extractLocations(tweet));		   
		}
		Iterator locs = myLocs.iterator();
		while (locs.hasNext())
		{
			Location loc = (Location) locs.next();
			if(trueLocs.contains(loc)){
				found++;
			} else {
			
			if(!myNom.getUrlString(loc))
			{
				System.out.println("*************************");
				System.out.println(loc.getName() + " not found and removed");
				System.out.println();
				deleted++;
			} else {
				found++;
				trueLocs.add(loc);
			}
			}
		}
		System.out.println("***********************************************************************");
		System.out.println("Encontradas "+found+" referencias en " +myCL.sentences.size()+" tweets");
		System.out.println("No encontradas y borradas "+deleted+" referencias");
		System.out.println("***********************************************************************");
		
		
	}

}
