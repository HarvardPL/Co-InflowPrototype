import java.lang.reflect.Method;

public class simpleClassLoading {
	private static boolean secret = true;

	public static void main(String[] args) throws Exception {
		// Inspired by
		// http://stackoverflow.com/questions/482633/in-java-is-it-possible-to-know-whether-a-class-has-already-been-loaded
		Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
		m.setAccessible(true);
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		if (secret) {
			new A();
		} else {
			new B();
		}

		Object test = m.invoke(cl, "simpleClassLoading.simpleClassLoading$A");
		boolean reconstructed = test != null;

		System.out.println("Reconstruction was " + reconstructed);
	}

	static class A {
	}

	static class B {
	}
}
