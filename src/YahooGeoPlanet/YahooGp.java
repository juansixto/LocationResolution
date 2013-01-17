package YahooGeoPlanet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import Classes.Location;

public class YahooGp {
	static String URL1 = "http://where.yahooapis.com/v1/places.q('";
	static String URL2 = "');start=0;count=";
	static String URL3 = "?appid=tTD8DLzV34EBP5gPd3vxWFeBTCMxs74RFmqZMet2fkHU6vn7MBwNJY72MG6lTuO_kQ--";
	static int LIMIT = 1;

	public YahooGp() {}

	public boolean getUrlString(Location location) {
		URL miURL = null;
		InputStreamReader isReader = null;
		BufferedReader bReader = null;
		String lineaURL;
		StringBuffer buffer = new StringBuffer();
		String name = location.getName().replace(" ", "+");
		
		String myURL = URL1 + name + URL2 + LIMIT + URL3;
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
		return location.YahooGptoXML(buffer.toString());
	}
}
