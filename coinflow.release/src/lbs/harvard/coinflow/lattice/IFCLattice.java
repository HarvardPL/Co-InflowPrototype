package lbs.harvard.coinflow.lattice;

import lbs.harvard.coinflow.lattice.principal.Principal;

public interface IFCLattice {
	
	public IFCLabel join(IFCLabel t1, IFCLabel t2);
	
	public IFCLabel meet(IFCLabel t1, IFCLabel t2);
	
	public boolean flowsTo(IFCLabel t1, IFCLabel t2);
	
	public IFCLabel bot();
	
	public IFCLabel top();
	
	public IFCLabel lookup(Principal p);
	
	public IFCLabel memoPrincipal(Principal p, IFCLabel label);

}
