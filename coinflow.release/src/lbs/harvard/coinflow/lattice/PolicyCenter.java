package lbs.harvard.coinflow.lattice;

import java.util.ArrayList;
import java.util.List;


/**
 * This class manages security policies supplied by programmers. 
 * 
 * An application may want to enforce several different kinds of policies, i.e., 
 * confidentiality for IDs, integrity for user addresses, and confidentiality for diagnostics. 
 * These policies are encoded as lattices. 
 * 
 * The lattice.impl package provides a few implementations for the IFCLattice interface. 
 * 
 * @author Jian 
 *
 */
public class PolicyCenter {

	public static List<IFCLattice> lattices = new ArrayList<>();

	/**
	 * Programmers use this method to add lattice, then call CoInflowUserAPI.initialize() to setup the lattice. 
	 * Multiple lattices can be added,  construct a product 
	 */
	public static void addLattice(IFCLattice lattice) {
		lattices.add(lattice);
	}
	
	
	
}
