package location.resolution.aux;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import location.resolution.models.BoundingBox;
import location.resolution.models.GeoPoint;
import location.resolution.models.LocationDescriptor;

public class Utils {
	
	private static int AVERAGE_RADIUS_OF_EARTH = 6371;
	
	public static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
	    int cp;
	    
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    
	    return sb.toString();
	}
	
	public static int calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

	    double latDistance = Math.toRadians(userLat - venueLat);
	    double lngDistance = Math.toRadians(userLng - venueLng);

	    double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
	                    (Math.cos(Math.toRadians(userLat))) *
	                    (Math.cos(Math.toRadians(venueLat))) *
	                    (Math.sin(lngDistance / 2)) *
	                    (Math.sin(lngDistance / 2));

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));
	}
	
	public static boolean hasAnyPointNear(LocationDescriptor locationDescriptor, List<LocationDescriptor> lld, int distance) {
		
		double lat = locationDescriptor.getLatitude();
		double lng = locationDescriptor.getLongitude();
		
		for(LocationDescriptor ld : lld) {
			int dist = calculateDistance(lat, lng, ld.getLatitude(), ld.getLongitude());
			if( (dist < distance) && (dist != 0) ) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void main (String args[]) {
		
		GeoPoint geopoint1 = new GeoPoint(42.8498032, -2.672999700000001);
		GeoPoint geopoint2 = new GeoPoint(52.5487429714954, -1.81602098644987);
		
		LocationDescriptor ld1 = new LocationDescriptor(geopoint1, null);
		LocationDescriptor ld2 = new LocationDescriptor(geopoint2, null);
		
		List<LocationDescriptor> lld = new ArrayList<>();
		lld.add(ld1);
		lld.add(ld2);
		
		if(hasAnyPointNear(ld1, lld, 10000)) {
			System.out.println("Near");
		}
		else {
			System.out.println("Far");
		}
	}
}
