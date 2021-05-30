package lbs.harvard.coinflow.compiler;

import spoon.Launcher;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.visitor.PrettyPrinter;

public class ClassVisitedProcessor extends AbstractProcessor<CtElement>{

	
	
	@Override
	public void process(CtElement element) {
		if(element instanceof CtClass) {
			CtClass clazz = (CtClass)element;
			CoInflowCompiler.classesProcessed.add(clazz.getQualifiedName());
		}else if(element instanceof CtInterface) {
			CtInterface itf = (CtInterface)element;
			CoInflowCompiler.classesProcessed.add(itf.getQualifiedName());
		}
	}
	
	
	

}
