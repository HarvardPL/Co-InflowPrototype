
class A {}
class B extends A {}
class C extends A {}

public class simpleTypes {
	static boolean secret = false;
	
	public static void main(String[] args) {
		test();
	}

        public static boolean test(){
                A obj;
		
		if(secret) {
			obj = new B();
		} else {
			obj = new C();
		}
		
		boolean reconstructed = obj instanceof B;
                return reconstructed;
        }
}
