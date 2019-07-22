
class A {}
class B extends A {}
class C extends A {}

public class simpleTypesCastingError {
	static boolean secret = true;
	
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
		
		boolean reconstructed = true;
		
		try{
			A test = ((B) obj);
		} catch(Exception e){
			reconstructed = false;
		} finally {
			return reconstructed;
		}
        }

     
}
