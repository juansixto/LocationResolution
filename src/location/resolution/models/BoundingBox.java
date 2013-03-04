package location.resolution.models;

import java.util.ArrayList;
import java.util.List;

public class BoundingBox {
	
	private List<GeoPoint> geoPoints;
	
	public BoundingBox() {
		this.geoPoints = new ArrayList<GeoPoint>();
	}
	
	/* Getter */

	public List<GeoPoint> getGeoPoints() {
		return geoPoints;
	}
	
	/* Add GeoPoint */
	
	public void addGeoPoint(GeoPoint geopoint) {
		this.geoPoints.add(geopoint);
	}
	
	/* to String */
	
	public String toString() {
		StringBuffer str = new StringBuffer();
		
		str.append("[");
		
		for(GeoPoint gp : geoPoints) {
			str.append("{" + gp.toString() + "}, ");
		}
		
		String s = str.substring(0, str.length() - 2);
		
		s += "]";
		
		return s;
	}
}
