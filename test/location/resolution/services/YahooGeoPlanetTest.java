package location.resolution.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import location.resolution.models.LocationDescriptor;

import org.junit.Before;
import org.junit.Test;

public class YahooGeoPlanetTest {

	private YahooGeoPlanet ygp = null;
	
	private List<LocationDescriptor> lld = null;

	@Before
	public void setUp() throws Exception {
		this.ygp = new YahooGeoPlanet();
		this.lld = new ArrayList<LocationDescriptor>();
	}

	@Test
	public void testSuccessSearchPlace() {
		lld = this.ygp.searchPlace("Vitoria-Gasteiz");
		assertTrue(lld.size() > 0);
	}
	
	@Test
	public void testFailSearchPlace() {
		lld = this.ygp.searchPlace("fakeLocation");
		assertFalse(lld.size() > 0);
	}
}
