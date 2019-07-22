
public class StaticDispatching {

	int h, l;
	
	int lsink, hsink;
	
	public void f()
	{
		if(l == 1)
			set((long) h);
		else
			set(h);
	}
	
	public void set(long a) { lsink = (int) a;}
	public void set(int a) { hsink = a;}
	
	
	public static void main(String[] args) {
		StaticDispatching sd = new StaticDispatching();		
		sd.f();		
	}
}
