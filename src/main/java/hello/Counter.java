package hello;

public class Counter {
	
	private int count;
	private String counterName;
	
	public Counter(String counterName) {
		this.counterName = counterName;
	}
	
	public int count() {
		return this.count;
	}
	
	public void increment() {
		this.count++;
	}

}
