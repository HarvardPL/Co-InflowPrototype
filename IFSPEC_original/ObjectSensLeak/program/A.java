
public class A {
	private int i;
	
	public A(int i) {
		this.i = i;
	}
	
	public int doPrint() {
		return out(this.i);
	}
	
	public static int out(int i){
		return i;
	}
}
