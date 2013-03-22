package location.resolution.main;

import java.util.ArrayList;
import java.util.List;

import location.resolution.gui.Demo;
import location.resolution.models.LocationDescriptor;
import location.resolution.services.Geonames;
import location.resolution.services.GoogleReverseCoder;
import location.resolution.services.OSMNominatim;
import location.resolution.services.WebServices;
import location.resolution.services.YahooGeoPlanet;

public class Main {
	
	public static Demo callWebServicesAtOnce(String placename) {
		WebServices ws = new WebServices();
		
		List<LocationDescriptor> lld = new ArrayList<LocationDescriptor>();
		
		lld = ws.searchPlace(placename);
		
		return new Demo(lld);
	}
	
	public static Demo callWebServicesOneByOne(String placename) {
		Geonames gn = new Geonames();
		GoogleReverseCoder grc = new GoogleReverseCoder();
		OSMNominatim osmn = new OSMNominatim();
		YahooGeoPlanet ygp = new YahooGeoPlanet();
		
		List<LocationDescriptor> lldgn = gn.searchPlace(placename);
		List<LocationDescriptor> lldgrc = grc.searchPlace(placename);
		List<LocationDescriptor> lldosmn = osmn.searchPlace(placename);
		List<LocationDescriptor> lldygp = ygp.searchPlace(placename);
		
		return new Demo(lldgn, lldgrc, lldosmn, lldygp);
	}
	
	/*
	 * 	MAIN
	 */
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		Demo demo = null;
		
		String placename = "Universidad de Deusto";
		
//		demo = callWebServicesAtOnce(placename);
		demo = callWebServicesOneByOne(placename);
		
		demo.setVisible(true);
		
		long end = System.currentTimeMillis();
		
		System.out.println("Execution time was: "+( (end-start)/1000 )+" seconds.");
	}
}
