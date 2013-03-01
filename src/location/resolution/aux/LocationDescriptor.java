package location.resolution.aux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationDescriptor {
	
	private static String NOMINATIM_BASE_URL = " http://nominatim.openstreetmap.org/reverse?format=json";
	private static String EXTRAS = "&zoom=18&accept-language=en&addressdetails=1";
	
	private String country = "";
	private String postcode = "";
	private String state = "";
	private String stateDistrict = "";
	private String county = "";
	private String city = "";
	private String suburb = "";
	private String street = "";
	private int houseNumber = 0;
	private double latitude;
	private double longitude;
	
	public LocationDescriptor() {}
	
	public LocationDescriptor(GeoPoint geopoint) {
		StringBuffer str = new StringBuffer();
		str.append(NOMINATIM_BASE_URL).append("&lat=").append(geopoint.getLatitude()).append("&lon=").append(geopoint.getLongitude()).append(EXTRAS);
		
		try {
			URL url = new URL(str.toString());
			InputStream is = url.openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    	String jsonText = Utils.readAll(rd);
	    	JSONObject jsonObject = new JSONObject(jsonText);
	    	is.close();
	    	
	    	JSONObject addressJSONObject = jsonObject.getJSONObject("address");
	    	
	    	try {
		    	this.setCountry(addressJSONObject.getString("country"));
	    	}
	    	catch (JSONException e) {
	    		this.setCountry("");
	    	}
	    	
	    	try {
		    	this.setPostcode(addressJSONObject.getString("postcode"));
	    	}
	    	catch (JSONException e) {
		    	this.setPostcode("");
	    	}
	    	
	    	try {
		    	this.setState(addressJSONObject.getString("state"));
	    	}
	    	catch (JSONException e) {
		    	this.setState("");
	    	}
	    	
	    	try {
		    	this.setStateDistrict(addressJSONObject.getString("state_district"));
	    	}
	    	catch (JSONException e) {
		    	this.setStateDistrict("");
	    	}
	    	
	    	try {
		    	this.setCounty(addressJSONObject.getString("county"));
	    	}
	    	catch (JSONException e) {
	    		this.setCounty("");
	    	}
	    	
	    	try {
		    	this.setCity(addressJSONObject.getString("city"));
	    	}
	    	catch (JSONException e) {
		    	this.setCity("");
	    	}
	    	
	    	try {
		    	this.setSuburb(addressJSONObject.getString("suburb"));
	    	}
	    	catch (JSONException e) {
		    	this.setSuburb("");
	    	}
	    	
	    	try {
		    	this.setStreet(addressJSONObject.getString("road"));
	    	}
	    	catch (JSONException e) {
		    	this.setStreet("");
	    	}
	    	
	    	try {
		    	this.setHouseNumber(addressJSONObject.getInt("house_number"));
	    	}
	    	catch (JSONException e) {
		    	this.setHouseNumber(0);
	    	}
	    	
	    	this.setLatitude(geopoint.getLatitude());
	    	this.setLongitude(geopoint.getLongitude());
		    	
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/* Getters & Setters */
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getStateDistrict() {
		return stateDistrict;
	}
	public void setStateDistrict(String stateDistrict) {
		this.stateDistrict = stateDistrict;
	}
	
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public int getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
	
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
		StringBuffer locationDescriptor = new StringBuffer();
		
		if(!this.country.equals("")) {
			locationDescriptor.append("country: " + this.country + ", ");
		}
		if(!this.postcode.equals("")) {
			locationDescriptor.append("postcode: " + this.postcode + ", ");
		}
		if(!this.state.equals("")) {
			locationDescriptor.append("state: " + this.state + ", ");
		}
		if(!this.stateDistrict.equals("")) {
			locationDescriptor.append("stateDistrict: " + this.stateDistrict + ", ");
		}
		if(!this.county.equals("")) {
			locationDescriptor.append("county: " + this.county + ", ");
		}
		if(!this.city.equals("")) {
			locationDescriptor.append("city: " + this.city + ", ");
		}
		if(!this.suburb.equals("")) {
			locationDescriptor.append("suburb: " + this.suburb + ", ");
		}
		if(!this.street.equals("")) {
			locationDescriptor.append("street: " + this.street + ", ");
		}
		if(this.houseNumber != 0) {
			locationDescriptor.append("house_number: " + this.houseNumber + ", ");
		}
		if(!String.valueOf(this.latitude).isEmpty()) {
			locationDescriptor.append("latitude: " + this.latitude + ", ");
		}
		if(!String.valueOf(this.longitude).isEmpty()) {
			locationDescriptor.append("longitude: " + this.longitude + ", ");
		}
		
		return locationDescriptor.substring(0, locationDescriptor.length() - 2);		
	}
	
	public static void main (String args[]) {
		GeoPoint geopoint = new GeoPoint(42.8498032, -2.672999700000001);
//		GeoPoint geopoint = new GeoPoint(52.5487429714954, -1.81602098644987);
		
		LocationDescriptor ld = new LocationDescriptor(geopoint);
		System.out.println(ld.toString());
	}
}
