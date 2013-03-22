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

public class YahooGeoPlanet {
	
	private Logger logger = null;
	
	private static String BASE_URL = "http://where.yahooapis.com/v1/places.q('";
	private static String API_KEY = "";
	
	private static int LIMIT;
	
	public YahooGeoPlanet() {
		PropertyConfigurator.configure("log4j.properties");
		this.logger = Logger.getLogger(YahooGeoPlanet.class);
		
		this.logger.info("Initialising YahooGeoPlanet()");
		
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
			prop.load(is);
			
			API_KEY = prop.getProperty("yahoo_geoplanet_api_key");
			LIMIT = Integer.parseInt(prop.getProperty("yahoo_geoplanet_limit"));
			
			this.logger.info("YahooGeoPlanet() initialised with a maximum results number fixed in " + LIMIT);
		}
		catch (FileNotFoundException e) {
			this.logger.warn("Unable to find location_resolution.properties file by YahooGeoPlanet.");
		}
		catch (IOException e) {
			this.logger.warn("Unable to load API key for YahooGeoPlanet.");
		}
	}
	
	private String formURL(String query) {
		StringBuffer str = new StringBuffer();
		
		str.append(BASE_URL).append(query.replaceAll(" ", "+")).append("');start=0;count=").append(LIMIT).append("?lang=en&format=json&appid=").append(API_KEY);
		
		this.logger.info("Query for '" + query + "': " + str.toString());
		
		return str.toString();
	}
	
	private GeoPoint fillGeoPoint(JSONObject centroidJSONObject) {
		GeoPoint geoPoint = new GeoPoint();		
		
		try {
			geoPoint.setLatitude(centroidJSONObject.getDouble("latitude"));
			geoPoint.setLongitude(centroidJSONObject.getDouble("longitude"));
			
			this.logger.info("\tNew GeoPoint => (" + geoPoint.getLatitude() + ", " + geoPoint.getLongitude() + ")");
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in YahooGeoPlanet.fillGeoPoint()");
			this.logger.debug(e.toString());
		}
		
		return geoPoint;
	}
	
	private BoundingBox fillBoundingBox(JSONObject jsonObject) {
		BoundingBox boundingBox = new BoundingBox();
		
		try {
			JSONObject boundingBoxJSONObject = jsonObject.getJSONObject("boundingBox");
			
			JSONObject southWestJSONObject = boundingBoxJSONObject.getJSONObject("southWest");
			JSONObject northEastJSONObject = boundingBoxJSONObject.getJSONObject("northEast");
							
			boundingBox.addGeoPoint(new GeoPoint(southWestJSONObject.getDouble("latitude"), southWestJSONObject.getDouble("longitude")));
			boundingBox.addGeoPoint(new GeoPoint(northEastJSONObject.getDouble("latitude"), northEastJSONObject.getDouble("longitude")));
			
			this.logger.info("\tFilled boundingBox");
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in YahooGeoPlanet.fillBoundingBox()");
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
	    	
	    	int total = json.getJSONObject("places").getInt("total");
	    	
	    	this.logger.info("Searching YahooGeoPlanet for '" + placename + "'...");
	    	this.logger.info(total + " results found.");
	    	
	    	if(total != 0) {
	    	
	    		JSONArray jsonArray = json.getJSONObject("places").getJSONArray("place");
	    	
		    	for(int i = 0; i < jsonArray.length(); i++) {
		    		JSONObject jsonObject = jsonArray.getJSONObject(i);
		    		JSONObject centroidJSONObject = jsonObject.getJSONObject("centroid");
		    		
		    		GeoPoint geopoint = fillGeoPoint(centroidJSONObject);
		    		BoundingBox boundingBox = fillBoundingBox(jsonObject);
		    		
		    		locationDescriptors.add(new LocationDescriptor(geopoint, boundingBox));
		    	}
	    	}
		}
		catch (MalformedURLException e) {
			this.logger.warn("MalformedURLException in YahooGeoPlanet.searchPlace()");
			this.logger.debug(e.toString());
		}
		catch (IOException e) {
			this.logger.warn("IOException in YahooGeoPlanet.searchPlace()");
			this.logger.debug(e.toString());
		}
		catch (JSONException e) {
			this.logger.warn("JSONException in YahooGeoPlanet.searchPlace()");
			this.logger.debug(e.toString());
		}
		
		return locationDescriptors;
	}
}
