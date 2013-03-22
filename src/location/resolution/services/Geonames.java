package location.resolution.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import location.resolution.models.GeoPoint;
import location.resolution.models.LocationDescriptor;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

public class Geonames {
	
	private Logger logger = null;
	
	private static int LIMIT;
	
	public Geonames() {
		PropertyConfigurator.configure("log4j.properties");
		this.logger = Logger.getLogger(WebServices.class);
		
		try {
			Properties prop = new Properties();
			InputStream is = new FileInputStream("location_resolution.properties");
			prop.load(is);
			
			WebService.setUserName(prop.getProperty("geonames_username"));
			LIMIT = Integer.parseInt(prop.getProperty("geonames_limit"));
			
			this.logger.info("location_resolution.properties loaded");
		}
		catch (FileNotFoundException e) {
			this.logger.warn("Unable to load location_resolution.properties file.");
		}
		catch (IOException e) {
			this.logger.warn("Unable to load username for GeoNames.");
		}
	}
	
	private GeoPoint fillGeoPoint(Toponym toponym) {
		GeoPoint geoPoint = new GeoPoint();		
		
		geoPoint.setLatitude(toponym.getLatitude());
		geoPoint.setLongitude(toponym.getLongitude());
		
		return geoPoint;
	}
	
	public List<LocationDescriptor> searchPlace(String placename) {
		List<LocationDescriptor> locationDescriptors = new ArrayList<LocationDescriptor>();
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
				
				GeoPoint geopoint = fillGeoPoint(toponym);
				
				locationDescriptors.add(new LocationDescriptor(geopoint, null));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return locationDescriptors;
	}
}
