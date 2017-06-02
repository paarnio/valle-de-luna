package siima.app.asp;

import java.io.File;

public class AspModelContainer {

	public File[] rulesAndFacts;
	public String rulefile; // .dlv
	public String factfile; // .db
	public boolean rulesAndFactsLoaded = false;
	public int numOfModels = 1;
	public StringBuffer resultModels;

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
				}
				if (path.contains(".db")) {
					factfile = path;
				}
				
			}
			this.rulesAndFactsLoaded = true;
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

	public StringBuffer getResultModels() {
		return resultModels;
	}

	public void setResultModels(StringBuffer resultModels) {
		this.resultModels = resultModels;
	}

	
}
