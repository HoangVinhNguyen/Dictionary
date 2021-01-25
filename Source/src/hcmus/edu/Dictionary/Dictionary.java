package hcmus.edu.Dictionary;



public class Dictionary {

	private String word;
	private String meaning;

	public void setDictionary(String key, String value) {
		word = key;
		meaning = value;
	}
	
	public String getWord() {
		return word;
	}
	
	public String getMeaning() {
		return meaning;
	}

}
