package NERAnalyzer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CorpusLoaderTest {

	CorpusLoader myCL;
	@Before
	public void setUp() throws Exception {
		myCL = new CorpusLoader();
	}

	@Test
	@Ignore
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void loadTest() {
		myCL.LoadCorpus(".\\Corpus\\100tweetsCorpus.txt");
	}
	

}
