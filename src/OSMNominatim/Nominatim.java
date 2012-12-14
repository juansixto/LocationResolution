package OSMNominatim;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;

import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.jdom2.Element;
import org.w3c.dom.*;
import java.io.*;

import Classes.Location;

public class Nominatim {
	
	static String URL = "http://nominatim.openstreetmap.org/search";
	
	public Nominatim() {
		
	}
	
	 public boolean getUrlString(Location location)
	  {

	    URL miURL = null;
	    InputStreamReader isReader = null;
	    BufferedReader bReader = null;
	    String lineaURL;
	    StringBuffer buffer = new StringBuffer();
	    String myURL = URL+"?q="+location.getName()+"&format=xml&accept-language=en-US";

	    try {
	      miURL = new URL(myURL);
	      isReader = new InputStreamReader(miURL.openStream());
	      bReader = new BufferedReader(isReader);
	      while ((lineaURL = bReader.readLine()) != null){
	        buffer.append(lineaURL);
	      }
	      bReader.close();
	      isReader.close();
	    } catch (MalformedURLException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    boolean resp = location.toXML(buffer.toString());
	    return resp;
	    
	 }
	 
	
	 
}
