
public class Static_Initializers_Leak {

	static String l = "Foo";
	static String h = "Top Secret";

	static String x = "Foo";
	static {
		x = h;
	}

	public static void main(String[] args) {
		l=x;
		System.out.println(l);
	}

}
