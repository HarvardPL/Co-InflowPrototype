package lbs.harvard.coinflow.util;

import java.util.HashMap;
import java.util.Map;

import lbs.harvard.coinflow.lattice.IFCLabel;

public class IFCLabelMap {

	
	Map<String, IFCLabel[]> labelMap = new HashMap<>();
	
	public void put(Object o, IFCLabel[] labels) {
		labelMap.put(Integer.toHexString(System.identityHashCode(o)), labels);
	}
	
	public IFCLabel[] get(Object o) {
		return labelMap.get(Integer.toHexString(System.identityHashCode(o)));
	}
	
	public boolean containsObject(Object o) {
		return labelMap.containsKey(Integer.toHexString(System.identityHashCode(o)));
	}
}
