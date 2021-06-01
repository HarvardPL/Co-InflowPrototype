package lbs.harvard.coinflow.internal;

import java.util.HashMap;
import java.util.Map;

import lbs.harvard.coinflow.lattice.IFCLabel;

/**
 * internal presentation of source and sink channels
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class IFCPolicyInternal{
	
	public static String parameter = "parameter";
	public static String returnValue = "return";
	public static String field = "field";
	public static String objectSelf = "self";
	
	// dynamic map: values can be labels created at runtime 
	// key : port.tostring()   value : IFCLabel; these are runtime labels 
	public static Map<String, IFCLabel> sinks = new HashMap<>();
	public static Map<String, IFCLabel> sources = new HashMap<>();
	
	// static annotations
	public static Map<String, String> staticSinkAnnotations = new HashMap<>();
	public static Map<String, String> staticSourceAnnotations = new HashMap<>();
	
	public static void addParameterAsSink(String className, String methodSign, int parameterPosition, IFCLabel label) {
		sinks.put(new Port(parameter, className, methodSign, parameterPosition).toString(), label);
	}
	
	public static void staticAddParameterAsSink(String className, String methodSign, int parameterPosition, String labelId) {
		staticSinkAnnotations.put(new Port(parameter, className, methodSign, parameterPosition).toString(), labelId);
	}
	
	public static IFCLabel getParameterSink(String className, String methodSign, int parameterPosition) {
		return sinks.get(new Port(parameter, className, methodSign, parameterPosition).toString());
	}

	public static void addReturnValAsSink(String className, String methodSign, IFCLabel label) {
		sinks.put(new Port(returnValue, className, methodSign, 0).toString(), label);
	}
	public static void staticAddReturnValAsSink(String className, String methodSign, String labelId) {
		staticSinkAnnotations.put(new Port(returnValue, className, methodSign, 0).toString(), labelId);
	}
	
	public static IFCLabel getReturnValSink(String className, String methodSign) {
		return sinks.get(new Port(returnValue, className, methodSign, 0).toString());
	}
	
	public static void addFieldAsSink(String className, String fieldName, IFCLabel label) {
		sinks.put(new Port(field, className, fieldName, 0).toString(), label);
	}
	public static void staticAddFieldAsSink(String className, String fieldName, String labelId) {
		staticSinkAnnotations.put(new Port(field, className, fieldName, 0).toString(), labelId);
	}
	
	public static IFCLabel getFieldSink(String className, String fieldName) {
		return sinks.get(new Port(field, className, fieldName, 0).toString());
	}
	
	public static void addReturnValAsSource(String className, String methodSign, IFCLabel label) {
		sources.put(new Port(returnValue, className, methodSign, 0).toString(), label);
	}
	public static void staticAddReturnValAsSource(String className, String methodSign, String labelId) {
		staticSourceAnnotations.put(new Port(returnValue, className, methodSign, 0).toString(), labelId);
	}
	
	public static IFCLabel getReturnValSource(String className, String methodSign) {
		return sources.get(new Port(returnValue, className, methodSign, 0).toString());
	}
	
	
	public static void addFieldAsSource(String className, String fieldName, IFCLabel labelId) {
		sources.put(new Port(field, className, fieldName, 0).toString(), labelId);
	}
	public static void staticAddFieldAsSource(String className, String fieldName, String labelId) {
		staticSourceAnnotations.put(new Port(field, className, fieldName, 0).toString(), labelId);
	}
	public static IFCLabel getFieldSource(String className, String fieldName) {
		return sources.get(new Port(field, className, fieldName, 0).toString());
	}
	
	public static void addParameterAsSource(String className, String methodSign, int parameterPosition, IFCLabel label) {
		sources.put(new Port(parameter, className, methodSign, parameterPosition).toString(), label);
	}
	public static void staticAddParameterAsSource(String className, String methodSign, int parameterPosition, String labelId) {
		staticSourceAnnotations.put(new Port(parameter, className, methodSign, parameterPosition).toString(), labelId);
	}
	
	public static IFCLabel getParameterSource(String className, String methodSign, int parameterPosition) {
		return sources.get(new Port(parameter, className, methodSign, parameterPosition).toString());
	}
	
	public static class Port{
		String className = "";
		String methodOrFieldName = "";
		int parameterPositionOnly = 0;
		String kind = "";
		
		public Port(String kind, String className, String methodOrFieldName, int parameterPositionOnly) {
			super();
			this.kind = kind;
			this.className = className;
			this.methodOrFieldName = methodOrFieldName;
			this.parameterPositionOnly = parameterPositionOnly;
		}
		
		public String getClassName() {
			return className;
		}

		public String getMethodOrFieldName() {
			return methodOrFieldName;
		}

		public int getParameterPositionOnly() {
			return parameterPositionOnly;
		}

		public String getKind() {
			return kind;
		}



		@Override
		public String toString() {
			return className + ":" + methodOrFieldName + ":" + parameterPositionOnly;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Port) {
				return this.toString().equals(((Port)o).toString());
			}
			return false;
		}
	}
}





