package NERAnalyzer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import Classes.Location;

public class NERAnalyzerTest {

	NERAnalyzer myNER;
	@Before
	public void setUp() throws Exception {
		myNER = new NERAnalyzer();
	}

	@Test 
	@Ignore
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void analyzeTextTest() {
		String s1 = "This is a test, and Adolf is traveling to Berlin.";
		String answer = myNER.analyzeText(s1);
		String a1 = "Adolf/PERSON Berlin/LOCATION ";
		assertTrue(answer.equals(a1));
	}	
	
	@Test
	public void extractLocation() {
		String s1 = "This is a test, and Adolf is traveling to Berlin. But we are in Slovenia ";
		List<Location> answer = myNER.extractLocations(s1);
		String a1 = "Berlin"; 
		assertTrue(answer.get(0).getName().equals(a1));
	}
}
