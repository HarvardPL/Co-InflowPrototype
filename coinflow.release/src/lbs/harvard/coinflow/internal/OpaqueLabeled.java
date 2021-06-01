package lbs.harvard.coinflow.internal;

import lbs.harvard.coinflow.lattice.IFCLabel;
/**
 * type for opaque labeled values
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 * @param <T>
 */
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
