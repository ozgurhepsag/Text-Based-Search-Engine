
public class Word {
	private String name;
	private int key;
	private double IDF = 0;
	
	
	Word(String nameOfWord){
		this.name = nameOfWord;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}	
	public double getIDF() {
		return IDF;
	}
	public void setIDF(double iDF) {
		IDF = iDF;
	}
	public void IDF(int docLength){
		
		IDF = (double) 1 + Math.log(699/docLength);
		
	}
	
	
}
