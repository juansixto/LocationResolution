package location.resolution.main;

import java.util.List;

import location.resolution.aux.GeoPoint;
import location.resolution.aux.LocationDescriptor;
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
		
		String placename = "iurreta";
		
		List<GeoPoint> lgpgn = gn.searchPlace(placename);
		List<GeoPoint> lgpgrc = grc.searchPlace(placename);
		List<GeoPoint> lgposmn = osmn.searchPlace(placename);
		List<GeoPoint> lgpygp = ygp.searchPlace(placename);
		
		System.out.println("Geonames:");
		for(GeoPoint gp: lgpgn) {
//			System.out.println(gp.toString());
			System.out.println(new LocationDescriptor(gp).toString());
		}

		System.out.println("\nGoogle Reverse Coder:");
		for(GeoPoint gp: lgpgrc) {
//			System.out.println(gp.toString());
			System.out.println(new LocationDescriptor(gp).toString());
		}

		System.out.println("\nOSM Nominatim:");		
		for(GeoPoint gp: lgposmn) {
//			System.out.println(gp.toString());
			System.out.println(new LocationDescriptor(gp).toString());
		}

		System.out.println("\nYahoo GeoPlanet:");
		for(GeoPoint gp: lgpygp) {
//			System.out.println(gp.toString());
			System.out.println(new LocationDescriptor(gp).toString());
		}
	}
}
