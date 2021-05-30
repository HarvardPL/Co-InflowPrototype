package lbs.harvard.coinflow.lattice;

import java.util.concurrent.ConcurrentHashMap;

public class IFCLabelintlist implements IFCLabel<int[]> {
	
	private int[] l = null;
	
	private IFCLabelintlist(int[] contents) {
		l = contents;
	}
	
	@Override
	public int[] getLabel() {
		return l;
	}

	private static final ConcurrentHashMap<int[], IFCLabel> memo = new ConcurrentHashMap<>();
	
	public static IFCLabel<int[]> makeIFCLabel(int[] contents) {
		IFCLabel v = memo.get(contents);
		if (v != null) {
			return v;
		}
		v = new IFCLabelintlist(contents);
		IFCLabel existing = memo.putIfAbsent(contents, v);
		return existing != null ? existing : v;
	}
	
	public String toString() {
		return "" + l;
	}
}
