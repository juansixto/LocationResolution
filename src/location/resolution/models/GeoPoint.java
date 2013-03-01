package location.resolution.models;

public class GeoPoint {
	
	private double latitude;
	private double longitude;
	
	public GeoPoint() {}
	
	public GeoPoint(double lat, double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}
	
	/* Getters & Setters */
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/* To string */
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(this.latitude).append(", ").append(this.longitude);
		return str.toString();			
	}
}
