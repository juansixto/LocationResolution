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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OSMNominatim {
	
	private static String BASE_URL = "http://nominatim.openstreetmap.org/search?q=";
	private static String FORMAT = "&format=json&accept-language=en-US&limit=";
	
	private static int LIMIT;
	
	public OSMNominatim() {
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
			prop.load(is);
			
			LIMIT = Integer.parseInt(prop.getProperty("osm_nominatim_limit"));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to load properties file.");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to load limit for OSMNominatim.");
		}
	}
	
	private String formURL(String query) {
		StringBuffer str = new StringBuffer();
		
		str.append(BASE_URL).append(query.replaceAll(" ", "+")).append(FORMAT).append(LIMIT);
		
		return str.toString();
	}
	
	private GeoPoint fillGeoPoint(JSONObject jsonObject) {
		GeoPoint geoPoint = new GeoPoint();		
		
		try {
			geoPoint.setLatitude(jsonObject.getDouble("lat"));
			geoPoint.setLongitude(jsonObject.getDouble("lon"));
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return geoPoint;
	}
	
	private BoundingBox fillBoundingBox(JSONObject jsonObject) {
		BoundingBox boundingBox = new BoundingBox();
		
		try {
			JSONArray boundingBoxJSONArray = jsonObject.getJSONArray("boundingbox");
			
			int cut = (boundingBoxJSONArray.length() / 2);
			
			for(int i = 0; i < cut; i++) {
				String lat = boundingBoxJSONArray.getString(i);
				String lng = boundingBoxJSONArray.getString(i+cut);
				
				GeoPoint geoPoint = new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lng));
								
				boundingBox.addGeoPoint(geoPoint);
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
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
	    	
	    	for(int i = 0; i < jsonArray.length(); i++) {
	    		JSONObject jsonObject = jsonArray.getJSONObject(i);
	    		
	    		GeoPoint geopoint = fillGeoPoint(jsonObject);
	    		BoundingBox boundingBox = fillBoundingBox(jsonObject);
	    		
	    		locationDescriptors.add(new LocationDescriptor(geopoint, boundingBox));
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
		
		return locationDescriptors;
	}
	
	public static void main(String[] args) {
		OSMNominatim osmn = new OSMNominatim();
		
		List<LocationDescriptor> lld = osmn.searchPlace("vitoria-gasteiz");
        
		for(LocationDescriptor ld: lld) {
			System.out.println(ld.toString());
		}
	}
}
