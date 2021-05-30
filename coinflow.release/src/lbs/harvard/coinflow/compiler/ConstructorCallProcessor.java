package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.PrimitiveType.Code;

import lbs.harvard.coinflow.internal.IFCUtil;
import lbs.harvard.coinflow.lattice.IFCLabel;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;

public class ConstructorCallProcessor extends AbstractProcessor<CtConstructorCall>{

		@Override
		public void process(CtConstructorCall element) {

			CodeFactory codeFactory = getFactory().Code();
			CoreFactory coreFactory = getFactory().Core();
			
			
			if(element.getExecutable().getDeclaringType() != null && 
					! element.getExecutable().getDeclaringType().getQualifiedName().startsWith("lbs.harvard.coinflow")) {
				List paras = element.getExecutable().getParameters();
				String classCalled = element.getExecutable().getDeclaringType().getQualifiedName();
				ArgumentProcessor.processArgu(element.getExecutable(), element);
				/**
				if(MainProcessor.classesProcessed.contains(classCalled)) {
					for(int i= 0; i< element.getArguments().size(); i++) {
						
						CtTypeReference paraTypeRef = (CtTypeReference) paras.get(i);
						
						// if there is type mismatch between parameter and argument
						CtExpression argu = (CtExpression)element.getArguments().get(i);
						ArgumentProcessor.processArgu(element.getExecutable(), argu, coreFactory, codeFactory, paraTypeRef);
					}
				}else {
				} */
			}
		}
}
