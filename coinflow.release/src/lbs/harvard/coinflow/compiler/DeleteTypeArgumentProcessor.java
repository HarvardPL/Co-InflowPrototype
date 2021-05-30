package lbs.harvard.coinflow.compiler;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtImport;
import spoon.reflect.declaration.CtTypeParameter;

public class DeleteTypeArgumentProcessor extends AbstractProcessor<CtElement>{

	@Override
	public void process(CtElement element) {
		// testing the contents
		if(element instanceof CtInvocation) {
			CtInvocation ctInv = (CtInvocation)element;
		}
	}

	


}
