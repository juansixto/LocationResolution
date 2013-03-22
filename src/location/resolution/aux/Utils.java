package location.resolution.aux;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

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
		
		int mod = distance / 110;
		
		
		
		for(LocationDescriptor ld : lld) {
			
			// Experimental sorrounding if
			
			int diff = (int) Math.ceil(Math.abs(lat - ld.getLatitude()));
			
			if(diff > mod) {
				int dist = calculateDistance(lat, lng, ld.getLatitude(), ld.getLongitude());
				if( (dist <= distance) && (dist != 0) ) {
					return true;
				}
			}
		}
		return false;
	}
}
