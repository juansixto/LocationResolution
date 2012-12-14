package NERAnalyzer;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.objectbank.ObjectBank;

public class CorpusLoader {
	
	List<String> sentences;
	static int MAX_LINES = 100000;
	
	public CorpusLoader() {
		this.sentences = new ArrayList();
		
	}
	
	public boolean LoadCorpus(String file) {
		boolean resp = false;
		int i = 0;
		 for (String line : ObjectBank.getLineIterator(file)) {
			 this.sentences.add(line);
			 i++;
			 if(i>= MAX_LINES) break; 
		 }	
		 System.out.println("Generado Corpus con " + i+ " tweets");
		return resp;		
	}
	
	

}
