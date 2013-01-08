package Classes;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Location {

	String name;
	String display_name;
	String lat;
	String lon;

	public Location(String name) {
		this.name = name;
		this.display_name = "";
		this.lat = "";
		this.lat = "";
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public boolean toXML(String xmlRecords) {
		boolean resp = false;
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("place");
			
			if (nodes.getLength() > 0) {
				String dis_name = nodes.item(0).getAttributes().getNamedItem("display_name").getNodeValue();
				String lat = nodes.item(0).getAttributes().getNamedItem("lat").getNodeValue();
				String lon = nodes.item(0).getAttributes().getNamedItem("lon").getNodeValue();
				
				System.out.println("Name: " + name);
				System.out.println("Display Name: " + dis_name);
				System.out.println("Latitude: " + lat);
				System.out.println("Longitude: " + lon);
				System.out.println();
				
				this.display_name = dis_name;
				this.lat = lat;
				this.lon = lon;
				
				resp = true;

			}
			else {
				resp = false;
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
}
