package lbs.harvard.coinflow.lattice.principal;

import java.util.concurrent.ConcurrentHashMap;

public class PrincipalStringImpl implements Principal<String> {
	
	private String identifer = "";
	
	private PrincipalStringImpl(String id){
		this.identifer = id;
	}

	private static ConcurrentHashMap<String, Principal<String>> memo = new ConcurrentHashMap<>();
	static Principal<String> makePrincipal(String id){
		Principal<String> prin = memo.get(id);
		if(prin != null) {
			return prin;
		}
		prin = new PrincipalStringImpl(id);
		Principal<String> existing = memo.putIfAbsent(id, prin);
		return existing != null ? existing : prin;
	}
	
	@Override
	public String getIdentifier() {
		return identifer;
	}
	
	
}
