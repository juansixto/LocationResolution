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
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		Geonames gn = new Geonames();
		GoogleReverseCoder grc = new GoogleReverseCoder();
		OSMNominatim osmn = new OSMNominatim();
		YahooGeoPlanet ygp = new YahooGeoPlanet();
		
		String placename = "Universidad de Deusto";
		
		
		WebServices ws = new WebServices();
		
		List<LocationDescriptor> l = new ArrayList<LocationDescriptor>();
		
		l = ws.searchPlacename(placename);
		 
//		List<LocationDescriptor> lldgn = gn.searchPlace(placename);
//		List<LocationDescriptor> lldgrc = grc.searchPlace(placename);
//		List<LocationDescriptor> lldosmn = osmn.searchPlace(placename);
//		List<LocationDescriptor> lldygp = ygp.searchPlace(placename);
//		
//		List<LocationDescriptor> lld = new ArrayList<LocationDescriptor>();
//		
//		System.out.println("Geonames:");
//		System.out.println("---------------------------------");
//		for(LocationDescriptor ld: lldgrc) {
//			System.out.println(ld.toString());
//			lld.add(ld);
//		}
//
//		System.out.println("\nGoogle Reverse Coder:");
//		System.out.println("---------------------------------");
//		for(LocationDescriptor ld: lldgn) {
//			System.out.println(ld.toString());
//			lld.add(ld);
//		}
//
//		System.out.println("\nOSM Nominatim:");
//		System.out.println("---------------------------------");
//		for(LocationDescriptor ld: lldosmn) {
//			System.out.println(ld.toString());
//			lld.add(ld);
//		}
//
//		System.out.println("\nYahoo GeoPlanet:");
//		System.out.println("---------------------------------");
//		for(LocationDescriptor ld: lldygp) {
//			System.out.println(ld.toString());
//			lld.add(ld);
//		}
		
//		LocationDetector ld = new LocationDetector();
//		ld.detectLocation(lld, (float) 0.1);
		
//		new Demo(lld).setVisible(true);
//		new Demo(lldgn, lldgrc, lldosmn, lldygp).setVisible(true);
		new Demo(l).setVisible(true);
		
		long end = System.currentTimeMillis();
		
		System.out.println("Execution time was: "+( (end-start)/1000 )+" seconds.");
	}
}
