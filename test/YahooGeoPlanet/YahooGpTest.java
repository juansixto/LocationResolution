package YahooGeoPlanet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Classes.Location;
import OSMNominatim.Nominatim;

public class YahooGpTest {

	public YahooGp myNom;

	@Before
	public void setUp() throws Exception {
		myNom = new YahooGp();
	}

	@Test
	@Ignore
	public void test() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void XMLTestTrue() {
		Location loc = new Location("berlin");
		assertTrue(myNom.getUrlString(loc));
	}
	
	@Test
	public void XMLTestFalse() {
		Location loc = new Location("jksfasfajkl");
		assertFalse(myNom.getUrlString(loc));
	}
	
	@Test
	public void XMLTestTrash() {
		Location loc = new Location("egypt");
		System.out.println(myNom.getUrlString(loc));
	}
	@Test
	public void URLMultiWord() {
		Location loc = new Location("Hamilton Wood Type Museum");
		System.out.println(myNom.getUrlString(loc));
	}
}