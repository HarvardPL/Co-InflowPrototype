import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.internal.Labeled;

public class A {
	private int i;
	
	public A(int i) {
		this.i = i;
	}
	
	public A(Labeled<Integer> x) {
		CoInFlowUserAPI.raiseObjLabel(this, CoInFlowUserAPI.labelOf(x));
		this.i = CoInFlowUserAPI.unlabel(x);
	}
	
	public int doPrint() {
		return out(this.i);
	}
	
	public static int out(int i){
		return i;
	}
}
