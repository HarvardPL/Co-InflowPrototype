package lbs.harvard.coinflow.lattice;

public interface IFCLabel<T> {
	
	public T getLabel();
//	public void setLabel(T t);
	
	@Override  
	public boolean equals(Object other);
	
	@Override  
	public int hashCode();
	
	@Override
	public String toString();
	
}
