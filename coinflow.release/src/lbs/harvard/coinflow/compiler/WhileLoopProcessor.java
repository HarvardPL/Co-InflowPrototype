package lbs.harvard.coinflow.compiler;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.CtWhile;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;

public class WhileLoopProcessor extends AbstractProcessor<CtWhile>{

	@Override
	public void process(CtWhile cwhile) {
		CtExpression guard = cwhile.getLoopingExpression();
		
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		
		CtStatement body = cwhile.getBody();
		if(body instanceof CtBlock) {
			CtBlock block = (CtBlock) body;
			
			CtIf newIf = coreFactory.createIf();
			
			CtUnaryOperator negation = coreFactory.createUnaryOperator();
			negation.setOperand(guard);
			negation.setKind(UnaryOperatorKind.NOT);
			
			newIf.setCondition(negation);
			newIf.setThenStatement(coreFactory.createBreak());
			block.insertBegin(newIf);
		}
		
		
		CtExpression<Boolean> trueExpr = codeFactory.createLiteral(true);
		cwhile.setLoopingExpression(trueExpr);
	}

	
}
