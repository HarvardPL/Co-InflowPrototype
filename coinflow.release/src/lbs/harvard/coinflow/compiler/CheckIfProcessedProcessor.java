package lbs.harvard.coinflow.compiler;



import java.util.List;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtElement;


/**
 * Check if the source project has been rewritten: if yes, we cannot proceed
 * @author Jian Xiang(jxiang@seas.harvard.edu)
 *
 */
public class CheckIfProcessedProcessor extends AbstractProcessor<CtElement>{

	public static boolean processed = false;
	
	@Override
	public void process(CtElement element) {	
		if(element instanceof CtLocalVariable) {
			CtLocalVariable lv = (CtLocalVariable)element;
			if(lv.getSimpleName().startsWith(RewriteHelper.var_prefix)){
				processed = true;
				return;
			}
		}
		
	}

}
