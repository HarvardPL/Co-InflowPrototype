package lbs.harvard.coinflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation for source and sink channels
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class CoInflowLibraryAnnotation {
	
	public static Map<String, List<Integer>> libraryEffectReadMap = new HashMap<>();
	public static Map<String, List<Integer>> libraryEffectWriteMap = new HashMap<>();
	
	
	static {
		// object.toString()
//		addLibraryCallRead("*", "toString()", 0);
//		addLibraryCallRead("*", "toUpperCase()", 0);
//		addLibraryCallRead("*", "toLowerCase()", 0);
//		
//		// StringBuffer
//		addLibraryCallRead("java.lang.StringBuffer", "append(java.lang.String)", new int[] {0, 1});
//		addLibraryCallWrite("java.lang.StringBuffer", "append(java.lang.String)", new int[] {0});
//		addLibraryCallRead("java.lang.StringBuffer", "java.lang.StringBuffer(java.lang.String)", new int[] {1});
//		addLibraryCallWrite("java.lang.StringBuffer", "java.lang.StringBuffer(java.lang.String)", new int[] {0});
//		
//		//java.util.StringTokenizer
//		addLibraryCallRead("java.util.StringTokenizer", "java.util.StringTokenizer(java.lang.String,java.lang.String)", new int[] {0, 1});
//		addLibraryCallWrite("java.util.StringTokenizer", "java.util.StringTokenizer(java.lang.String,java.lang.String)", new int[] {0});
//		
//		//java.util.LinkedList
//		addLibraryCallRead("java.util.LinkedList", "addLast(java.lang.Object)", new int[] {0, 1});
//		addLibraryCallWrite("java.util.LinkedList", "addLast(java.lang.Object)", new int[] {0});
//		addLibraryCallWrite("java.util.LinkedList", "getLast()", new int[] {0});
//		
//		
//		//java.util.ArrayList
//		addLibraryCallWrite("java.util.ArrayList", "add(java.lang.Object)", new int[] {0});
//		addLibraryCallRead("java.util.ArrayList", "iterator()", new int[] {0});
//		
//		//java.util.Iterator
//		addLibraryCallWrite("java.util.Iterator", "next(java.lang.Object)", new int[] {0});
//		
//		//java.util.Map
//		addLibraryCallWrite("java.util.Map", "put(java.lang.Object,java.lang.Object)", new int[] {0});
//		addLibraryCallRead("java.util.Map", "get(java.lang.Object)", new int[] {0});
//		
//		//java.util.Collection
//		addLibraryCallWrite("java.util.Collection", "add(java.lang.Object)", new int[] {0});
//		
//		//javax.servlet.http.HttpSession
//		addLibraryCallWrite("javax.servlet.http.HttpSession", "setAttribute(java.lang.String,java.lang.Object)", new int[] {0});
//		addLibraryCallRead("javax.servlet.http.HttpSession", "getAttribute(java.lang.String)", new int[] {0});
	}
	
	public static String buildMethodSign(String classQName, String methodName) {
		return classQName + "$"+ methodName;
	}
	
	public static void addLibraryCallRead(String classQName, String methodName, int[] reads) {
		for(int i : reads) {
			addLibraryCallRead(classQName, methodName, i);
		}
	}
	
	public static void addLibraryCallRead(String classQName, String methodName, int read) {
		String key = buildMethodSign(classQName, methodName);
		libraryEffectReadMap.putIfAbsent(key, new ArrayList<Integer>());
		libraryEffectReadMap.get(key).add(read);
	}
	
	public static void addLibraryCallWrite(String classQName, String methodName, int[] writes) {
		for(int i : writes) {
			addLibraryCallWrite(classQName, methodName, i);
		}
	}
	
	public static void addLibraryCallWrite(String classQName, String methodName, int write) {
		String key = buildMethodSign(classQName, methodName);
		libraryEffectWriteMap.putIfAbsent(key, new ArrayList<Integer>());
		libraryEffectWriteMap.get(key).add(write);
	}
	
	
}
