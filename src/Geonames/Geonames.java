package Geonames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import Classes.Location;

public class Geonames {
	static String URL = "http://api.geonames.org/postalCodeSearch?";
	static String USERNAME = "&username=jsixto";
	static String LIMIT = "&maxRows=1";

	public Geonames() {}

	public boolean getUrlString(Location location) {
		URL miURL = null;
		InputStreamReader isReader = null;
		BufferedReader bReader = null;
		String lineaURL;
		StringBuffer buffer = new StringBuffer();
		String name = location.getName().replace(" ", "+");
		
		String myURL = URL + "placename=" + name + USERNAME + LIMIT;
		System.out.println(myURL);
		try {
			miURL = new URL(myURL);
			isReader = new InputStreamReader(miURL.openStream());
			bReader = new BufferedReader(isReader);
			
			while ((lineaURL = bReader.readLine()) != null) {
				buffer.append(lineaURL);
			}
			
			bReader.close();
			isReader.close();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(buffer.toString());
		return location.GeonametoXML(buffer.toString());
	}
}
