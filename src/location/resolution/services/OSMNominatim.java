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

public class OSMNominatim {
	
	private Logger logger = null;
	
	private static String BASE_URL = "http://nominatim.openstreetmap.org/search?q=";
	private static String FORMAT = "&format=json&accept-language=en-US&limit=";
	
	private static int LIMIT;
	
	public OSMNominatim() {
		PropertyConfigurator.configure("log4j.properties");
		this.logger = Logger.getLogger(OSMNominatim.class);
		
		this.logger.info("Initialising OSMNominatim()");
		
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
			prop.load(is);
			
			LIMIT = Integer.parseInt(prop.getProperty("osm_nominatim_limit"));
			
			this.logger.info("OSMNominatim() initialised with a maximum results number fixed in " + LIMIT);
		}
		catch (FileNotFoundException e) {
			this.logger.warn("Unable to find location_resolution.properties file by OSMNominatim.");
		}
		catch (IOException e) {
			this.logger.warn("Unable to load limit for OSMNominatim.");
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
			geoPoint.setLatitude(jsonObject.getDouble("lat"));
			geoPoint.setLongitude(jsonObject.getDouble("lon"));
			
			this.logger.info("\tNew GeoPoint => (" + geoPoint.getLatitude() + ", " + geoPoint.getLongitude() + ")");
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in OSMNominatim.fillGeoPoint()");
			this.logger.debug(e.toString());
		}
		
		return geoPoint;
	}
	
	private BoundingBox fillBoundingBox(JSONObject jsonObject) throws JSONException {
		BoundingBox boundingBox = new BoundingBox();
		
		try {
			JSONArray boundingBoxJSONArray = jsonObject.getJSONArray("boundingbox");
			
			if(boundingBoxJSONArray.length() == 0) {
				this.logger.warn("No points for boundingBox found");
			}
			
			int cut = (boundingBoxJSONArray.length() / 2);
			
			for(int i = 0; i < cut; i++) {
				Double lat;
				Double lng;
				try {
					String slat = boundingBoxJSONArray.getString(i);
					String slng = boundingBoxJSONArray.getString(i+cut);
					
					lat = Double.parseDouble(slat);
					lng = Double.parseDouble(slng);
				}
				catch (JSONException e) {
					lat = boundingBoxJSONArray.getDouble(i);
					lng = boundingBoxJSONArray.getDouble(i+cut);
				}
				
				GeoPoint geoPoint = new GeoPoint(lat, lng);
								
				boundingBox.addGeoPoint(geoPoint);				
			}
			
			this.logger.info("\tFilled boundingBox");
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in OSMNominatim.fillBoundingBox()");
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
	    	JSONArray jsonArray = new JSONArray(jsonText);
	    	is.close();
	    	
	    	this.logger.info("Searching OSMNominatim for '" + placename + "'...");
	    	this.logger.info(jsonArray.length() + " results found.");
	    	
	    	for(int i = 0; i < jsonArray.length(); i++) {
	    		JSONObject jsonObject = jsonArray.getJSONObject(i);
	    		
	    		GeoPoint geopoint = fillGeoPoint(jsonObject);
	    		BoundingBox boundingBox = fillBoundingBox(jsonObject);
	    		
	    		locationDescriptors.add(new LocationDescriptor(geopoint, boundingBox));
	    	}
		}
		catch (MalformedURLException e) {
			this.logger.warn("MalformedURLException in OSMNominatim.searchPlace()");
			this.logger.debug(e.toString());
		}
		catch (IOException e) {
			this.logger.warn("IOException in OSMNominatim.searchPlace()");
			this.logger.debug(e.toString());
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in OSMNominatim.searchPlace()");
			this.logger.debug(e.toString());
		}
		
		return locationDescriptors;
	}
}
