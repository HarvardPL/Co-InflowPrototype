package lbs.harvard.coinflow.lattice;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Integer implementation for labels. It is simple and fast
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class IFCLabelInt implements IFCLabel<Integer> {

	
	private int l = 0;
	
	public IFCLabelInt(int content) {
		l = content;
	}
	
	@Override
	public Integer getLabel() {
		return l;
	}

	private static final ConcurrentHashMap<Integer, IFCLabel> memo = new ConcurrentHashMap<>();
	
	public static IFCLabel makeIFCLabel(int content) {
		IFCLabel v = memo.get(content);
		if (v != null) {
			return v;
		}
		v = new IFCLabelInt(content);
		IFCLabel existing = memo.putIfAbsent(content, v);
		return existing != null ? existing : v;
	}
	
	public String toString() {
		return "" + l;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof IFCLabel) {
			IFCLabel<Integer> i = (IFCLabel<Integer>)o;
			if(i.getLabel() == this.getLabel()) {
				return true;
			}
		}
		return false;
	}
}
