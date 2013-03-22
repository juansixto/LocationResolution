package location.resolution.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import location.resolution.models.LocationDescriptor;

public class WebServices {
	
	private Logger logger = null;

	private List<String> searchedPlacenames = null;
	
	private Geonames geonames = null;
	private GoogleReverseCoder googleReverseCoder = null;
	private OSMNominatim osmNominatim = null;
	private YahooGeoPlanet yahooGeoPlanet = null;
	
	public WebServices() {
		PropertyConfigurator.configure("log4j.properties");
		this.logger = Logger.getLogger(WebServices.class);
		
		this.logger.info("Initialising WebServices()");
		
		this.searchedPlacenames = new ArrayList<String>();
		
		this.geonames = new Geonames();
		this.googleReverseCoder = new GoogleReverseCoder();
		this.osmNominatim = new OSMNominatim();
		this.yahooGeoPlanet = new YahooGeoPlanet();
		
		this.logger.info("WebServices() initialised");
	}
	
	public List<LocationDescriptor> searchPlacename(String placename) {
		List<LocationDescriptor> locationDescriptors = new ArrayList<LocationDescriptor>();
		if(!this.searchedPlacenames.contains(placename)) {
			this.searchedPlacenames.add(placename);
			
			this.logger.info("New location to search: " + placename);
			
			locationDescriptors.addAll(geonames.searchPlace(placename));
			locationDescriptors.addAll(googleReverseCoder.searchPlace(placename));
			locationDescriptors.addAll(osmNominatim.searchPlace(placename));
			locationDescriptors.addAll(yahooGeoPlanet.searchPlace(placename));
		}
		else {
			this.logger.info("The location '" + placename + "' was already resolved.");
		}
		return locationDescriptors;
	}
}
