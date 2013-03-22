package location.resolution.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import location.resolution.models.LocationDescriptor;

import org.junit.Before;
import org.junit.Test;

public class GeonamesTest {
	
	private Geonames geonames = null;
	
	private List<LocationDescriptor> lld = null;

	@Before
	public void setUp() throws Exception {
		this.geonames = new Geonames();
		this.lld = new ArrayList<LocationDescriptor>();
	}

	@Test
	public void testSuccessSearchPlace() {
		lld = this.geonames.searchPlace("Vitoria-Gasteiz");
		assertTrue(lld.size() > 0);
	}
	
	@Test
	public void testFailSearchPlace() {
		lld = this.geonames.searchPlace("fakeLocation");
		assertFalse(lld.size() > 0);
	}
}
