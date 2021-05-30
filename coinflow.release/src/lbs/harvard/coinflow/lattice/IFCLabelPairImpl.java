package lbs.harvard.coinflow.lattice;

public class IFCLabelPairImpl implements IFCLabel<Pair>{

	Pair<IFCLabel, IFCLabel> contents = null;
	
	@Override
	public Pair<IFCLabel, IFCLabel> getLabel() {
		return contents;
	}

	public IFCLabelPairImpl(IFCLabel l,  IFCLabel r){
		contents = new Pair<IFCLabel, IFCLabel>(l, r);
	}
	
	public IFCLabelPairImpl(Pair<IFCLabel, IFCLabel>  p){
		contents = p;
	}
	
}
