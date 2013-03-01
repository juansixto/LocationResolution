package location.resolution.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class LocationDetector {
	
	private List<String> countryList;
	private List<String> postcodeList;
	private List<String> stateList;
	private List<String> stateDistrictList;
	private List<String> countyList;
	private List<String> cityList;
	private List<String> suburbList;
	private List<String> streetList;
	
	private float total_locations;
	private float threshold;
	
	private boolean found;
	
	public LocationDetector() {
		this.countryList = new ArrayList<String>();
		this.postcodeList = new ArrayList<String>();
		this.stateList = new ArrayList<String>();
		this.stateDistrictList = new ArrayList<String>();
		this.countyList = new ArrayList<String>();
		this.cityList = new ArrayList<String>();
		this.suburbList = new ArrayList<String>();
		this.streetList = new ArrayList<String>();
		
		this.found = false;
	}
	
	private void getProbability(String key, List<String> list) {
		int highest = Collections.frequency(list, key);
		float prob = (float)highest / (float)this.total_locations;
		if ( prob > this.threshold ) {
			this.found = true;
			if(!key.equals("")) {
				System.out.println(key + " (" + prob*100 + "%)");
			}
		}
	}
	
	public void detectLocation(List<LocationDescriptor> lld, float threshold) {
		
		this.total_locations = lld.size();
		this.threshold = threshold;
		
		for(LocationDescriptor ld : lld) {
			this.countryList.add(ld.getCountry());
			this.postcodeList.add(ld.getPostcode());
			this.stateList.add(ld.getState());
			this.stateDistrictList.add(ld.getStateDistrict());
			this.countyList.add(ld.getCounty());
			this.cityList.add(ld.getCity());
			this.suburbList.add(ld.getSuburb());
			this.streetList.add(ld.getStreet());
		}
		
		while(!found) {
			System.out.println("\n Street");
			System.out.println("---------------------------------");
			for (String key : new HashSet<String>(streetList)) {
				getProbability(key, streetList);
			}
			
			System.out.println("\n Suburb");
			System.out.println("---------------------------------");
			for (String key : new HashSet<String>(suburbList)) {
				getProbability(key, suburbList);
			}
			
			System.out.println("\n City");
			System.out.println("---------------------------------");
			for (String key : new HashSet<String>(cityList)) {
				getProbability(key, cityList);
			}
			
			System.out.println("\n County");
			System.out.println("---------------------------------");
			for (String key : new HashSet<String>(countyList)) {
				getProbability(key, countyList);
			}
			
			System.out.println("\n State district");
			System.out.println("---------------------------------");
			for (String key : new HashSet<String>(stateDistrictList)) {
				getProbability(key, stateDistrictList);
			}
			
			System.out.println("\n State");
			System.out.println("---------------------------------");
			for (String key : new HashSet<String>(stateList)) {
				getProbability(key, stateList);
			}
			
			System.out.println("\n PostCode");
			System.out.println("---------------------------------");
			for (String key : new HashSet<String>(postcodeList)) {
				getProbability(key, postcodeList);
			}
			
			System.out.println("\n Country");
			System.out.println("---------------------------------");
			for (String key : new HashSet<String>(countryList)) {
				getProbability(key, countryList);
			}
		}		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
