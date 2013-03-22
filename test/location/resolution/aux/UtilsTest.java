package location.resolution.aux;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import location.resolution.models.GeoPoint;
import location.resolution.models.LocationDescriptor;

import org.junit.Before;
import org.junit.Test;

public class UtilsTest {
	
	private GeoPoint deustoBI = null;
	private GeoPoint deustoSS = null;
	private GeoPoint guggenheim = null;
	
	private List<LocationDescriptor> lld = null;
	
	@Before
	public void setUp() {
		this.deustoBI = new GeoPoint(43.27156,-2.940447);
		this.deustoSS = new GeoPoint(43.30984, -1.97601);
		this.guggenheim = new GeoPoint(43.26850, -2.93456);
		
		this.lld = new ArrayList<LocationDescriptor>();
	}

	@Test
	public void testTrueCalculateDistance() {
		int distance = Utils.calculateDistance(this.deustoBI.getLatitude(), this.deustoBI.getLongitude(), this.guggenheim.getLatitude(), this.guggenheim.getLongitude());
		assertTrue(distance == 1);
	}
	
	@Test
	public void testFalseCalculateDistance() {
		int distance = Utils.calculateDistance(this.deustoBI.getLatitude(), this.deustoBI.getLongitude(), this.deustoSS.getLatitude(), this.deustoSS.getLongitude());
		assertFalse(distance > 100);
	}

	@Test
	public void testTrueHasAnyPointNear() {
		this.lld.add(new LocationDescriptor(this.deustoBI, null));
		this.lld.add(new LocationDescriptor(this.deustoSS, null));
		this.lld.add(new LocationDescriptor(this.guggenheim, null));
		boolean near = Utils.hasAnyPointNear(new LocationDescriptor(this.deustoBI, null), lld, 1);
		assertTrue(near);
	}
	
	@Test
	public void testFalseHasAnyPointNear() {
		this.lld.add(new LocationDescriptor(this.deustoBI, null));
		this.lld.add(new LocationDescriptor(this.deustoSS, null));
		boolean near = Utils.hasAnyPointNear(new LocationDescriptor(this.deustoBI, null), lld, 50);
		assertFalse("", near);
	}
}
