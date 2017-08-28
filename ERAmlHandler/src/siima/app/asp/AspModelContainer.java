package siima.app.asp;

import java.io.File;
import java.util.List;

import it.unical.mat.wrapper.Model;

public class AspModelContainer {

	public File[] rulesAndFacts;
	public String rulefile; // .dlv
	public String factfile; // .db
	public boolean rulesAndFactsLoaded = false;
	public boolean rulesLoaded = false;
	public boolean factsLoaded = false;
	public int numOfModels = 1;
	public List<Model> resultAspModels;
	public StringBuffer resultModelsAsXML;

	public File[] getRulesAndFacts() {
		return rulesAndFacts;
	}

	public void setRulesAndFacts(File[] rulesAndFacts) {

		if (rulesAndFacts != null) {
			this.rulesAndFacts = rulesAndFacts;
			for (int i = 0; i < rulesAndFacts.length; i++) {
				File file = rulesAndFacts[i];
				String path = file.getAbsolutePath();
				if (path.contains(".dlv")) {
					rulefile = path;
					rulesLoaded = true;
				}
				if (path.contains(".db")) {
					factfile = path;
					factsLoaded = true;
				}
				
			}
			if(rulesLoaded&&factsLoaded) this.rulesAndFactsLoaded = true;
		}
		System.out.println("-AspModel:setRulesAndFacts");
	}

	public String getRulefile() {
		return rulefile;
	}

	public String getFactfile() {
		return factfile;
	}

	public boolean isRulesAndFactsLoaded() {
		return rulesAndFactsLoaded;
	}

	public int getNumOfModels() {
		return numOfModels;
	}

	public void setNumOfModels(int numOfModels) {
		this.numOfModels = numOfModels;
	}

	public StringBuffer getResultModelsAsXML() {
		return resultModelsAsXML;
	}

	public void setResultModelsAsXML(StringBuffer resultModels) {
		this.resultModelsAsXML = resultModels;
	}

	public List<Model> getResultAspModels() {
		return resultAspModels;
	}

	public void setResultAspModels(List<Model> resultAspModels) {
		this.resultAspModels = resultAspModels;
	}

	
}
