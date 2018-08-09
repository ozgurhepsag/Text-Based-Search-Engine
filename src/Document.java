
public class Document {
	private String name;
	private int frequency;
	private Document up;
	private int totalWord = 0;
	
	
	public Document(String name, int frequency) {
		this.name = name;
		this.frequency = frequency;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public Document getUp() {
		return up;
	}
	public void setUp(Document up) {
		this.up = up;
	}

	public int getTotalword() {
		return totalWord;
	}

	public void setTotalword(int totalWord) {
		this.totalWord = totalWord;
	}
	
	

	
	
	
}
