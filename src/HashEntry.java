
public class HashEntry {
	private Word word;
	private HashEntry right;
	private Document up;
	
	HashEntry(Word dataToAdd,Document up){
		this.word = dataToAdd;
		this.up = up;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public HashEntry getRight() {
		return right;
	}

	public void setRight(HashEntry right) {
		this.right = right;
	}

	public Document getUp() {
		return up;
	}

	public void setUp(Document up) {
		this.up = up;
	}
		
	
	
}
