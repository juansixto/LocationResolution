package location.resolution.main;

import java.util.ArrayList;
import java.util.List;

import location.resolution.models.GeoPoint;
import location.resolution.models.LocationDescriptor;
import location.resolution.models.LocationDetector;
import location.resolution.services.Geonames;
import location.resolution.services.GoogleReverseCoder;
import location.resolution.services.OSMNominatim;
import location.resolution.services.YahooGeoPlanet;

public class Main {
	
	public static void main(String[] args) {
		Geonames gn = new Geonames();
		GoogleReverseCoder grc = new GoogleReverseCoder();
		OSMNominatim osmn = new OSMNominatim();
		YahooGeoPlanet ygp = new YahooGeoPlanet();
		
		String placename = "Bilbao";
		
		List<GeoPoint> lgpgn = gn.searchPlace(placename);
		List<GeoPoint> lgpgrc = grc.searchPlace(placename);
		List<GeoPoint> lgposmn = osmn.searchPlace(placename);
		List<GeoPoint> lgpygp = ygp.searchPlace(placename);
		
		List<LocationDescriptor> lld = new ArrayList<LocationDescriptor>();
		
//		System.out.println("Geonames:");
//		System.out.println("---------------------------------");
		for(GeoPoint gp: lgpgn) {
//			System.out.println(gp.toString());
//			System.out.println(new LocationDescriptor(gp).toString());
			lld.add(new LocationDescriptor(gp));
		}

//		System.out.println("\nGoogle Reverse Coder:");
//		System.out.println("---------------------------------");
		for(GeoPoint gp: lgpgrc) {
//			System.out.println(gp.toString());
//			System.out.println(new LocationDescriptor(gp).toString());
			lld.add(new LocationDescriptor(gp));
		}

//		System.out.println("\nOSM Nominatim:");
//		System.out.println("---------------------------------");
		for(GeoPoint gp: lgposmn) {
//			System.out.println(gp.toString());
//			System.out.println(new LocationDescriptor(gp).toString());
			lld.add(new LocationDescriptor(gp));
		}

//		System.out.println("\nYahoo GeoPlanet:");
//		System.out.println("---------------------------------");
		for(GeoPoint gp: lgpygp) {
//			System.out.println(gp.toString());
//			System.out.println(new LocationDescriptor(gp).toString());
			lld.add(new LocationDescriptor(gp));
		}
		
		LocationDetector ld = new LocationDetector();
		ld.detectLocation(lld, (float) 0.1);
	}
}
