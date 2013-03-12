package location.resolution.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import location.resolution.models.LocationDescriptor;

public class WebServices {

	private List<String> searchedPlacenames = null;
	
	private Geonames geonames = null;
	private GoogleReverseCoder googleReverseCoder = null;
	private OSMNominatim osmNominatim = null;
	private YahooGeoPlanet yahooGeoPlanet = null;
	
	public WebServices() {
		this.searchedPlacenames = new ArrayList<String>();
		
		this.geonames = new Geonames();
		this.googleReverseCoder = new GoogleReverseCoder();
		this.osmNominatim = new OSMNominatim();
		this.yahooGeoPlanet = new YahooGeoPlanet();
	}
	
	public List<LocationDescriptor> searchPlacename(String placename) {
		List<LocationDescriptor> locationDescriptors = new ArrayList<LocationDescriptor>();
		if(!this.searchedPlacenames.contains(placename)) {
			this.searchedPlacenames.add(placename);
			
			locationDescriptors.addAll(geonames.searchPlace(placename));
			locationDescriptors.addAll(googleReverseCoder.searchPlace(placename));
			locationDescriptors.addAll(osmNominatim.searchPlace(placename));
			locationDescriptors.addAll(yahooGeoPlanet.searchPlace(placename));
		}
		return locationDescriptors;
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		Logger logger = Logger.getLogger(WebServices.class);
		
		String placename = "Vitoria-Gasteiz";
		
		WebServices webServices = new WebServices();
		for(LocationDescriptor ld : webServices.searchPlacename(placename)) {
			logger.info(ld.toString());
		}
	}
}
