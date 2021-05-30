package lbs.harvard.coinflow.compiler;



import java.util.List;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtElement;

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
