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
import location.resolution.models.GeoPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YahooGeoPlanet {
	
	private static String BASE_URL = "http://where.yahooapis.com/v1/places.q('";
	private static String API_KEY = "";
	
	private static int LIMIT;
	
	public YahooGeoPlanet() {
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
			prop.load(is);
			
			API_KEY = prop.getProperty("yahoo_geoplanet_api_key");
			LIMIT = Integer.parseInt(prop.getProperty("yahoo_geoplanet_limit"));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to load properties file.");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to load API key for Yahoo GeoPlanet.");
		}
	}
	
	private String formURL(String query) {
		StringBuffer str = new StringBuffer();
		
		str.append(BASE_URL).append(query.replaceAll(" ", "+")).append("');start=0;count=").append(LIMIT).append("?lang=en&format=json&appid=").append(API_KEY);
		
		return str.toString();
	}
	
	private GeoPoint fillGeoPoint(JSONObject centroidJSONObject) {
		GeoPoint geoPoint = new GeoPoint();		
		
		try {
			geoPoint.setLatitude(centroidJSONObject.getDouble("latitude"));
			geoPoint.setLongitude(centroidJSONObject.getDouble("longitude"));
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return geoPoint;
	}
	
	public List<GeoPoint> searchPlace(String placename) {
		List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
		try {
			InputStream is = new URL(formURL(placename)).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    	String jsonText = Utils.readAll(rd);
	    	JSONObject json = new JSONObject(jsonText);
	    	is.close();
	    	
	    	JSONArray jsonArray = json.getJSONObject("places").getJSONArray("place");
	    	
	    	for(int i = 0; i < jsonArray.length(); i++) {
	    		JSONObject jsonObject = jsonArray.getJSONObject(i);
	    		JSONObject centroidJSONObject = jsonObject.getJSONObject("centroid");
	    		
	    		geoPoints.add(fillGeoPoint(centroidJSONObject));
	    	}
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
		
		return geoPoints;
	}
}
