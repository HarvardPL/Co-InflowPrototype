public class IFLoop {
    public int low;
    private int high;


   public static void main(String[] args){
      IFLoop ifl = new IFLoop();
      ifl.insecure_ifl();
   }
      

    public void insecure_ifl() {
	int x = 0;
	int y = 0;
	while (y < 10) {
	    print(x);
	    if (y == 5) {
		x = high;
	    }
	    x++;
	    y++;
	}
    }

    public void print(int x) {
            low = x;
    }
}
