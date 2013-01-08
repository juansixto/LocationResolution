package NERAnalyzer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import edu.stanford.nlp.objectbank.ObjectBank;

public class CorpusLoader {

	List<String> sentences;

	public CorpusLoader() {
		this.sentences = new ArrayList<String>();
	}

	public void LoadCorpus(String filename, int max_lines) {
		int i = 0;
		boolean isJSON = false;
		
		this.sentences.clear();
		
		if(filename.endsWith(".json")) {
			isJSON = true;
		}
			
			
		for (String line : ObjectBank.getLineIterator(filename)) {
			if(isJSON) {
				try {
					if(!line.equals("")) {
						String text = new JSONObject(line).getString("text");
						this.sentences.add(text);
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else {
				this.sentences.add(line);
			}
			
			i++;
			if (i >= max_lines) {
				break;
			}
		}
		
		System.out.println("Generado Corpus con " + i + " tweets");
	}
}
