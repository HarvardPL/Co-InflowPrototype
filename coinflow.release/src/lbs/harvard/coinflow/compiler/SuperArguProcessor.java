package lbs.harvard.coinflow.compiler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;

import lbs.harvard.coinflow.util.RewriteHelper;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtArrayRead;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtNewArray;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnumValue;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.TypeFilter;

public class SuperArguProcessor extends AbstractProcessor<CtElement> {

	
	public final static String argu_prefix = "$$super_argu_method";
	static int idx = 0;
	
	private void buildPrivateMethodForArgus(CoreFactory coreFactory, CodeFactory codeFactory, 
			CtInvocation superCall, CtConstructor cur_cons) {

		for(int i =0; i< superCall.getArguments().size() ; i++) {
			// for every argument of superCall, create a new method
			CtExpression argu = (CtExpression) superCall.getArguments().get(i);
			if(argu instanceof CtInvocation || argu instanceof CtConstructorCall || 
					argu instanceof CtFieldRead || argu instanceof CtArrayRead
					|| argu instanceof CtBinaryOperator) {
				// rewrite it to a static private method
				// add a new method
				CtMethod newM = coreFactory.createMethod();
				CtReturn ret = coreFactory.createReturn();
				CtExpression exp = argu.clone();
				ret.setReturnedExpression(exp);
				newM.setBody(ret);
				if(superCall.getExecutable() != null) {
					CtTypeReference p = (CtTypeReference)superCall.getExecutable().getParameters().get(i);
					newM.setType(p);
				}
				newM.setSimpleName(argu_prefix + idx++);
				CtClass parentClazz = (CtClass)cur_cons.getParent();
				CtType topLevelType = parentClazz.getTopLevelType();
				
				boolean staticOrNot = false;
				if(cur_cons.getDeclaringType() == topLevelType) {
					// top level superCall, i.e., not inner class
					newM.addModifier(ModifierKind.STATIC);
					staticOrNot = true;
				}
				if(cur_cons.getDeclaringType().isStatic()) {
					// top level superCall, i.e., not inner class
					newM.addModifier(ModifierKind.STATIC);
					staticOrNot = true;
				}
				
				// add throw exceptions
				for(Object o : cur_cons.getThrownTypes()) {
					newM.addThrownType((CtTypeReference)o);
				}
				
				topLevelType.addMethod(newM);
				for(Object o: cur_cons.getParameters()) {
					CtParameter op = (CtParameter)o;
					CtParameter p = op.clone();
					newM.addParameter(p);
					List<CtVariableAccess> paraRefs = exp.getElements(new TypeFilter<CtVariableAccess>(CtVariableAccess.class));
					for(CtVariableAccess paraRef : paraRefs) {
						if(paraRef.getVariable() instanceof CtParameterReference) {
							if(((CtParameterReference) paraRef.getVariable()).getSimpleName().equals(p.getSimpleName())) {
								paraRef.setType(p.getType());
								paraRef.setVariable(p.getReference());	
							}
						}
					}
				}
				
				if(cur_cons.getDeclaringType() != null ) {
					CtExpression[] exps = new CtExpression[cur_cons.getParameters().size()];
//					List<CtExpression> expsInList = new ArrayList<>();
					for(int j = 0; j < cur_cons.getParameters().size(); j++) {
						CtVariableAccess ref = codeFactory.createVariableRead(((CtParameter)cur_cons.getParameters().get(j)).getReference(), false);
						exps[j] = ref;
					}
					if(staticOrNot) {
						CtExpression replacement = 
								codeFactory.createInvocation(
										codeFactory.createTypeAccess(topLevelType.getReference()),
										newM.getReference(), 
										exps);
						argu.replace(replacement);
					}else {
						
						CtExpression target = codeFactory.createThisAccess(topLevelType.getReference());
						CtExpression replacement = 
								codeFactory.createInvocation(
										target,
										newM.getReference(), 
										exps);
						argu.replace(replacement);
					}
					
					
				}
			}
		}
	}
	
	@Override
	public void process(CtElement element) {
		
		if(element instanceof CtConstructor) {
			
			CoreFactory coreFactory = getFactory().Core();
			CodeFactory codeFactory = getFactory().Code();
			
			CtConstructor cons = (CtConstructor)element;
			CtBlock body = cons.getBody();
			if(body.getStatements().size() > 0) {
				CtStatement fstStmt = body.getStatement(0);
				if(fstStmt instanceof CtInvocation) {
					if(fstStmt.toString().startsWith("super(") || fstStmt.toString().startsWith("this(") ){
						// call other constructors: super or this
						CtInvocation inv = (CtInvocation)fstStmt;
						buildPrivateMethodForArgus(coreFactory, codeFactory, inv, cons);
					}
				}
			}
		}
	}
	
}
