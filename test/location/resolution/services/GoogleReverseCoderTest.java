package location.resolution.services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import location.resolution.models.LocationDescriptor;

import org.junit.Before;
import org.junit.Test;

public class GoogleReverseCoderTest {

	private GoogleReverseCoder grc = null;
	
	private List<LocationDescriptor> lld = null;

	@Before
	public void setUp() throws Exception {
		this.grc = new GoogleReverseCoder();
		this.lld = new ArrayList<LocationDescriptor>();
	}

	@Test
	public void testSuccessSearchPlace() {
		lld = this.grc.searchPlace("Vitoria-Gasteiz");
		assertTrue(lld.size() > 0);
	}
	
	@Test
	public void testFailSearchPlace() {
		lld = this.grc.searchPlace("fakeLocation");
		assertFalse(lld.size() > 0);
	}
}
