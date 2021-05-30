package lbs.harvard.coinflow.lattice;

public class IFCLabelString implements IFCLabel<String>{

	String l = "";
	@Override
	public String getLabel() {
		return l;
	}
	
	public IFCLabelString(String s) {
		l = s; 
	}
	
	@Override
	public boolean equals(Object other) {
		if((other instanceof IFCLabel)) {
			return other.toString().equals(l);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return l.hashCode();
	}
	
	@Override
	public String toString() {
		return l;
	}

}
