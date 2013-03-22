package location.resolution.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import location.resolution.aux.Utils;
import location.resolution.models.BoundingBox;
import location.resolution.models.GeoPoint;
import location.resolution.models.LocationDescriptor;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleReverseCoder {
	
	private Logger logger = null;
	
	private static String BASE_URL = "http://maps.googleapis.com/maps/api/geocode/json?address=";
	private static String FORMAT = "&language=en&sensor=false&limit=";
	
	private static int LIMIT;
	
	public GoogleReverseCoder() {
		PropertyConfigurator.configure("log4j.properties");
		this.logger = Logger.getLogger(GoogleReverseCoder.class);
		
		this.logger.info("Initialising GoogleReverseCoder()");
		
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
			prop.load(is);
			
			LIMIT = Integer.parseInt(prop.getProperty("google_reverse_coder_limit"));
			
			this.logger.info("GoogleReverseCoder() initialised with a maximum results number fixed in " + LIMIT);
		}
		catch (FileNotFoundException e) {
			this.logger.warn("Unable to find location_resolution.properties file by GoogleReverseCoder.");
		}
		catch (IOException e) {
			this.logger.warn("Unable to load limit for GoogleReverseCoder.");
		}
	}
	
	private String formURL(String query) {
		StringBuffer str = new StringBuffer();
		
		str.append(BASE_URL).append(query.replaceAll(" ", "+")).append(FORMAT).append(LIMIT);
		
		this.logger.info("Query for '" + query + "': " + str.toString());
		
		return str.toString();
	}
	
	private GeoPoint fillGeoPoint(JSONObject jsonObject) {
		GeoPoint geoPoint = new GeoPoint();
		
		try {
			JSONObject locationJSONObject = jsonObject.getJSONObject("location");
			
			geoPoint.setLatitude(locationJSONObject.getDouble("lat"));
			geoPoint.setLongitude(locationJSONObject.getDouble("lng"));
			
			this.logger.info("\tNew GeoPoint => (" + geoPoint.getLatitude() + ", " + geoPoint.getLongitude() + ")");
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in GoogleReverseCoder.fillGeoPoint()");
			this.logger.debug(e.toString());
		}
		
		return geoPoint;
	}
	
	private BoundingBox fillBoundingBox(JSONObject jsonObject) {
		BoundingBox boundingBox = new BoundingBox();
		
		try {
			JSONObject viewportJSONObject = jsonObject.getJSONObject("viewport");
			
			JSONObject southwestJSONObject = viewportJSONObject.getJSONObject("southwest");
			JSONObject northeastJSONObject = viewportJSONObject.getJSONObject("northeast");
							
			boundingBox.addGeoPoint(new GeoPoint(southwestJSONObject.getDouble("lat"), southwestJSONObject.getDouble("lng")));
			boundingBox.addGeoPoint(new GeoPoint(northeastJSONObject.getDouble("lat"), northeastJSONObject.getDouble("lng")));
			
			this.logger.info("\tFilled boundingBox");
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in GoogleReverseCoder.fillBoundingBox()");
			this.logger.debug(e.toString());
		}
		
		return boundingBox;
	}
	
	public List<LocationDescriptor> searchPlace(String placename) {
		List<LocationDescriptor> locationDescriptors = new ArrayList<LocationDescriptor>();
		try {
			InputStream is = new URL(formURL(placename)).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    	String jsonText = Utils.readAll(rd);
	    	JSONObject json = new JSONObject(jsonText);
	    	is.close();
	    	
	    	JSONArray jsonArray = json.getJSONArray("results");
	    	
	    	this.logger.info("Searching GoogleReverseCoder for '" + placename + "'...");
	    	this.logger.info(jsonArray.length() + " results found.");
	    	
	    	for(int i = 0; i < jsonArray.length(); i++) {
	    		JSONObject jsonObject = jsonArray.getJSONObject(i);
	    		
	    		JSONObject geometryJSONObject = jsonObject.getJSONObject("geometry");
	    		
	    		GeoPoint geopoint = fillGeoPoint(geometryJSONObject);
	    		BoundingBox boundingBox = fillBoundingBox(geometryJSONObject);
	    		
	    		locationDescriptors.add(new LocationDescriptor(geopoint, boundingBox));
	    	}
		}
		catch (MalformedURLException e) {
			this.logger.warn("MalformedURLException in GoogleReverseCoder.searchPlace()");
			this.logger.debug(e.toString());
		}
		catch (IOException e) {
			this.logger.warn("IOException in GoogleReverseCoder.searchPlace()");
			this.logger.debug(e.toString());
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in GoogleReverseCoder.searchPlace()");
			this.logger.debug(e.toString());
		}
		
		return locationDescriptors;
	}
}
