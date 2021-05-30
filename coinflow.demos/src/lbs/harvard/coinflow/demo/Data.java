package lbs.harvard.coinflow.demo;


import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.lattice.IFCLattice;
import lbs.harvard.coinflow.lattice.impl.IFCLatticeGraphImpl;
import lbs.harvard.coinflow.lattice.principal.Principal;
import lbs.harvard.coinflow.lattice.principal.PrincipalFactory;

public class Data {
	static Person alice = new Person("123-456-7890", 1);
	static Person bob = new Person("987-654-3210", 2);
	static Principal aliceP = PrincipalFactory.makePrincipal("alice");
	static Principal bobP = PrincipalFactory.makePrincipal("bob");
	
	/*
	 * User needs to provide code for constructing  
	 */
	static {
		buildLattice();
		IFCLattice l = CoInFlowUserAPI.getLattice();
		CoInFlowUserAPI.raiseObjLabel(Data.alice, l.lookup(aliceP));
		CoInFlowUserAPI.raiseObjLabel(bob, l.lookup(bobP));
	}
	
	public static IFCLattice buildLattice() {
		Principal aliceP = PrincipalFactory.makePrincipal("alice");
		Principal bobP = PrincipalFactory.makePrincipal("bob");
		IFCLatticeGraphImpl impl = new IFCLatticeGraphImpl();
		IFCLattice l = impl; 
		CoInFlowUserAPI.initilize(l);		
		return l;
	}
}
