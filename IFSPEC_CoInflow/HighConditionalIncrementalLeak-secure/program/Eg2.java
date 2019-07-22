class Eg2 {
    public static void main(String args[]){
			int h = 5;
			int l = 1;
                        f(h,l);	
			//System.out.println(f(h, l));
    }

    public static int f(int h, int l)
    {
			h = helper(h);
			return l;
    }
    
    public static int helper(int h) {
	    	while (h>0){
				h--;
		}
	    	return h;
    }
}
