package location.resolution.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import location.resolution.aux.GeoPoint;

import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

public class Geonames {
	
	private static int LIMIT;
	
	public Geonames() {
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
			prop.load(is);
			
			WebService.setUserName(prop.getProperty("geonames_username"));
			LIMIT = Integer.parseInt(prop.getProperty("geonames_limit"));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to load properties file.");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to load username for GeoNames.");
		}
	}
	
	private GeoPoint fillGeoPoint(Toponym toponym) {
		GeoPoint geoPoint = new GeoPoint();		
		
		geoPoint.setLatitude(toponym.getLatitude());
		geoPoint.setLongitude(toponym.getLongitude());
		
		return geoPoint;
	}
	
	public List<GeoPoint> searchPlace(String placename) {
		List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
		try {
			ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
			searchCriteria.setQ(placename);
			searchCriteria.setLanguage("en");
			ToponymSearchResult searchResult = WebService.search(searchCriteria);
			
			List<Toponym> lt = searchResult.getToponyms();
			
			if(lt.size() < LIMIT) {
				LIMIT = lt.size();
			}

			for (int i = 0; i < LIMIT; i++) {
				Toponym toponym = lt.get(i);
				geoPoints.add(fillGeoPoint(toponym));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return geoPoints;
	}
}
