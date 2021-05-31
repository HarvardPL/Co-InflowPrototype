package lbs.harvard.coinflow.compiler;

import lbs.harvard.coinflow.internal.IFCUtil;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtElement;

/**
 * A bit optimization that removes useless label joins; 
 * (More optimization may be added later) 
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 *
 */
public class RemoveUnnecessaryCode extends AbstractProcessor<CtElement>{

	@Override
	public void process(CtElement element) {
		
		// The following code is unless, so we remove it
		if(element.toString().contains("IFCUtil.joinTopLabel(IFCUtil.getCurrentLabel())")){
			element.delete();
		}
	}
}
