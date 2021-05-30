package lbs.harvard.coinflow.compiler;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtCompilationUnit;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnum;

public class DebugProcessor extends AbstractProcessor<CtElement>{

	@Override
	public void process(CtElement element) {
		if(element.toString().contains("source.indexOf(")) {
//			System.out.println();
		}
	}

}
