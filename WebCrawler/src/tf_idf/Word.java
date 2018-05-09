package tf_idf;

public class Word implements Comparable<Word>{
	public String term;
	public double value;
	
	public Word(String term, double value) {
		this.term = term;
		this.value = value;
	}

	@Override
	public int compareTo(Word o) {
		if(this.value - o.value> 0) {
			return 1;
		}
		else if(this.value - o.value == 0) {
			return 0;
		}
		else {
			return -1;	
		}	
	}
	
	public String toString() {
		return term + " " + value;
	}
}
