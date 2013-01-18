package Classes;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
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
	
	@Override
	public boolean equals(Object obj) {
		 if(obj == null)                return false;
		 if (obj instanceof Location){
		    
		    Location other = (Location) obj;
		    return this.name.equalsIgnoreCase(other.name);
		 } else { 
			 return false;
		 }
		
	}

	public boolean NominatimtoXML(String xmlRecords) {
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
	
	public boolean GeonametoXML(String xmlRecords) {
		boolean resp = true;
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("code");
			if (nodes.getLength() > 0){
			for (int s = 0; s < nodes.getLength(); s++) {
				Node primerNodo = nodes.item(s);
				if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {
					Element primerElemento = (Element) primerNodo;
					
					NodeList firstList = primerElemento.getElementsByTagName("name");
					Element firstElement =(Element) firstList.item(0);
					NodeList Name = firstElement.getChildNodes();
					this.display_name = ((Node) Name.item(0)).getNodeValue().toString();
					System.out.println("DisplayName : "  + this.display_name);
					
					NodeList secondList = primerElemento.getElementsByTagName("lat");
					Element secondElement =(Element) secondList.item(0);
					NodeList Lat = secondElement.getChildNodes();
					this.lat = ((Node) Lat.item(0)).getNodeValue().toString();
					System.out.println("Latitude : "  + this.lat);
					
					secondList = primerElemento.getElementsByTagName("lng");
					secondElement =(Element) secondList.item(0);
					Lat = secondElement.getChildNodes();
					this.lon = ((Node) Lat.item(0)).getNodeValue().toString();
					System.out.println("Longitude : "  + this.lon);
					
				} 
				
			}
			}else{
				resp = false;
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
	
	public boolean GoogletoXML(String xmlRecords) {
		boolean resp = true;
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("result");
			if (nodes.getLength() > 0){
			for (int s = 0; s < 1; s++) {
				Node primerNodo = nodes.item(s);
				if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {
					Element primerElemento = (Element) primerNodo;
					
					NodeList firstList = primerElemento.getElementsByTagName("formatted_address");
					Element firstElement =(Element) firstList.item(0);
					NodeList Name = firstElement.getChildNodes();
					this.display_name = ((Node) Name.item(0)).getNodeValue().toString();
					System.out.println("DisplayName : "  + this.display_name);
					
					NodeList secondList = primerElemento.getElementsByTagName("lat");
					Element secondElement =(Element) secondList.item(0);
					NodeList Lat = secondElement.getChildNodes();
					this.lat = ((Node) Lat.item(0)).getNodeValue().toString();
					System.out.println("Latitude : "  + this.lat);
					
					secondList = primerElemento.getElementsByTagName("lng");
					secondElement =(Element) secondList.item(0);
					Lat = secondElement.getChildNodes();
					this.lon = ((Node) Lat.item(0)).getNodeValue().toString();
					System.out.println("Longitude : "  + this.lon);
					
				} 
				
			}
			}else{
				resp = false;
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
	
	public boolean YahooGptoXML(String xmlRecords) {
		boolean resp = true;
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("place");
			if (nodes.getLength() > 0){
			for (int s = 0; s < 1; s++) {
				Node primerNodo = nodes.item(s);
				if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {
					Element primerElemento = (Element) primerNodo;
					
					NodeList firstList = primerElemento.getElementsByTagName("name");
					Element firstElement =(Element) firstList.item(0);
					NodeList Name = firstElement.getChildNodes();
					this.display_name = ((Node) Name.item(0)).getNodeValue().toString();
					System.out.println("DisplayName : "  + this.display_name);
					
					NodeList secondList = primerElemento.getElementsByTagName("latitude");
					Element secondElement =(Element) secondList.item(0);
					NodeList Lat = secondElement.getChildNodes();
					this.lat = ((Node) Lat.item(0)).getNodeValue().toString();
					System.out.println("Latitude : "  + this.lat);
					
					secondList = primerElemento.getElementsByTagName("longitude");
					secondElement =(Element) secondList.item(0);
					Lat = secondElement.getChildNodes();
					this.lon = ((Node) Lat.item(0)).getNodeValue().toString();
					System.out.println("Longitude : "  + this.lon);
					
				} 
				
			}
			}else{
				resp = false;
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
	
	public String getTagValue(String tag, Element elemento) {
		NodeList lista = ((Document) elemento).getElementsByTagName(tag).item(0).getChildNodes();
		Node valor = (Node) lista.item(0);
		return valor.getNodeValue();
		}
}
