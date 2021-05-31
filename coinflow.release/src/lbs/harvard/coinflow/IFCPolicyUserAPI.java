package lbs.harvard.coinflow;

import lbs.harvard.coinflow.lattice.IFCLabel;

/**
 * Programmers will use these method calls to specify source and sink. (reserved for future use). 
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class IFCPolicyUserAPI {

	public static void addParameterAsSink(String className, String methodSign, int parameterPosition, IFCLabel label) {
	}

	public static void addParameterAsSource(String className, String methodSign, int parameterPosition, IFCLabel label) {
	}

	public static void addReturnValAsSink(String className, String methodSign, IFCLabel label) {
	}

	public static void addReturnValAsSource(String className, String methodSign, IFCLabel label) {
	}

	public static void addFieldAsSink(String className, String fieldName, IFCLabel label) {
	}

	public static void addFieldAsSource(String className, String fieldName, IFCLabel label) {
	}

}