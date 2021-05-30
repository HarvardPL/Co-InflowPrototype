package lbs.harvard.coinflow.internal;

import lbs.harvard.coinflow.lattice.IFCLabel;

public final class OpaqueLabeled<T> {
	IFCLabel l = null;
	T v = null;

	public OpaqueLabeled(T t, IFCLabel label) {
		v = t;
		l = label;
	}

	public T getV() {
		return v;
	}
	
	public IFCLabel getLabel() {
		return l;
	}
}
