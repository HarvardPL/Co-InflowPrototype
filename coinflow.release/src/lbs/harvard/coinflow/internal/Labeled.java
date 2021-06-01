package lbs.harvard.coinflow.internal;

import lbs.harvard.coinflow.lattice.IFCLabel;

/**
 * Type for labeled values
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 * @param <T>
 */
public final class Labeled<T> {
	IFCLabel l = null;
	T v = null;

	public Labeled(T t, IFCLabel label) {
		v = t;
		l = label;
	}

	T getV(){
		return v;
	}

	IFCLabel getLabel() {
		return l;
	}
}
