package OSMNominatim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import Classes.Location;

public class Nominatim {

	static String URL = "http://nominatim.openstreetmap.org/search";
	static String FORMAT = "&format=xml&accept-language=en-US";
	static int LIMIT = 1;

	public Nominatim() {}

	public boolean getUrlString(Location location) {
		URL miURL = null;
		InputStreamReader isReader = null;
		BufferedReader bReader = null;
		String lineaURL;
		StringBuffer buffer = new StringBuffer();
		String name = location.getName().replace(" ", "+");
		
		String myURL = URL + "?q=" + name + FORMAT +"&limit="+LIMIT;
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
		return location.toXML(buffer.toString());
	}
}
