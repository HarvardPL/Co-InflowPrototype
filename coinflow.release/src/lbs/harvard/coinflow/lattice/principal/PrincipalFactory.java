package lbs.harvard.coinflow.lattice.principal;

public class PrincipalFactory {

	public static Principal<String> makePrincipal(String prinId){
		return PrincipalStringImpl.makePrincipal(prinId);
	}
	
}
