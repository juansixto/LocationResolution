package location.resolution.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import location.resolution.gui.Demo;
import location.resolution.models.LocationDescriptor;
import location.resolution.services.Geonames;
import location.resolution.services.GoogleReverseCoder;
import location.resolution.services.OSMNominatim;
import location.resolution.services.YahooGeoPlanet;

public class EarthquakesJapan {

	public static void main(String[] args) {
		
		Geonames gn = new Geonames();
		GoogleReverseCoder grc = new GoogleReverseCoder();
		OSMNominatim osmn = new OSMNominatim();
		YahooGeoPlanet ygp = new YahooGeoPlanet();
		
		List<LocationDescriptor> lldgn = new ArrayList<LocationDescriptor>();
		List<LocationDescriptor> lldgrc = new ArrayList<LocationDescriptor>();
		List<LocationDescriptor> lldosmn = new ArrayList<LocationDescriptor>();
		List<LocationDescriptor> lldygp = new ArrayList<LocationDescriptor>();
		
		BufferedReader br = null;
	    try {
	    	br = new BufferedReader(new FileReader("corpus/Japan.txt"));
	    	String line;
	    	while ((line = br.readLine()) != null) {

	    		lldgn.addAll(gn.searchPlace(line));
	    		lldgrc.addAll(grc.searchPlace(line));
	    		lldosmn.addAll(osmn.searchPlace(line));
	    		lldygp.addAll(ygp.searchPlace(line));
	    		
	    	}
	    	br.close();
	    }
	    catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    catch (IOException e) {
			e.printStackTrace();
		}
	    finally {
	        try {
				br.close();
			}
	        catch (IOException e) {
				e.printStackTrace();
			}
	        
	        new Demo(lldgn, lldgrc, lldosmn, lldygp).setVisible(true);
	    }
	}
}
