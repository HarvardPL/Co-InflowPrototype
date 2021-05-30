package lbs.harvard.coinflow.lattice;

public class Pair<A,B> {
	A a;  B b;
    public Pair(A a, B b) {
    	this.a = a; this.b = b; 
    }

    A fst() { return a; }
    B snd() { return b; }
    
    public boolean equals(Pair<A,B> another) {
    	return this.a.equals(another.a) && this.b.equals(another.b);
    }
    
    public String toString() {
    	return a + ";" + b;
    }
    
}
