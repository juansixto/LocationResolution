package NERAnalyzer;

import java.util.ArrayList;
import java.util.List;

import Classes.Location;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreLabel;

public class NERAnalyzer {

	String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
	AbstractSequenceClassifier<CoreLabel> classifier;

	@SuppressWarnings("unchecked")
	public NERAnalyzer() {
		classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	}

	public String analyzeText(String text) {
		String resp = "";
		
		for (List<CoreLabel> lcl : classifier.classify(text)) {
			for (CoreLabel word : lcl) {
				if ((word.get(AnswerAnnotation.class).length() > 1)) {
					resp += (word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
				}
			}
		}
		
		return resp;
	}

	public List<Location> extractLocations(String text) {
		List<Location> resp = new ArrayList<>();
		Location loc;
		List<String> entities = new ArrayList<>();
		EntityExtractor myEEx = new EntityExtractor();
		entities = myEEx.getEntities(classifier.classifyWithInlineXML(text), "LOCATION");
		for (String string : entities) {
			loc = new Location(string);
			resp.add(loc);
		}
		return resp;
	}
	public List<Location> OldextractLocations(String text) {
		List<Location> resp = new ArrayList<>();
		Location loc;
		String place = "";
		for (List<CoreLabel> lcl : classifier.classify(text)) {
			for (CoreLabel word : lcl) {
				if ((word.get(AnswerAnnotation.class).contains("LOCATION"))) {
					place += word.word();
				} else {
					if(place.length()>0) {
						loc = new Location(word.word());
						resp.add(loc);
						place = "";
					}
				}
			}
		}
		return resp;
	}
}
