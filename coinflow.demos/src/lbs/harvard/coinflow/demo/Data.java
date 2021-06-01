package lbs.harvard.coinflow.demo;


import lbs.harvard.coinflow.CoInFlowUserAPI;
import lbs.harvard.coinflow.lattice.IFCLabel;
import lbs.harvard.coinflow.lattice.IFCLabelString;
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
		IFCLattice l = buildLattice();
		CoInFlowUserAPI.raiseObjLabel(Data.alice, l.lookup(aliceP));
		CoInFlowUserAPI.raiseObjLabel(bob, l.lookup(bobP));
	}
	
	public static IFCLattice buildLattice() {
		IFCLatticeGraphImpl imp = new IFCLatticeGraphImpl();
		imp.addFlowRelation(new IFCLabelString("L"), new IFCLabelString("alice"));
		imp.addFlowRelation(new IFCLabelString("L"), new IFCLabelString("bob"));
		imp.addFlowRelation(new IFCLabelString("alice"), new IFCLabelString("H"));
		imp.addFlowRelation(new IFCLabelString("bob"), new IFCLabelString("H"));
		CoInFlowUserAPI.initilize(imp);		
		return imp;
	}
}
