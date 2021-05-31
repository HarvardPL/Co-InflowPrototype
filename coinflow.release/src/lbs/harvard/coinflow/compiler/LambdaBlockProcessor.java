package lbs.harvard.coinflow.compiler;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtLambda;
import spoon.reflect.code.CtStatement;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.factory.CoreFactory;

/**
 * Dealing with lambda: create a block for lambda expressions that do not have a bracket
 * @author Jian Xiang(jxiang@seas.harvard.edu)
 *
 */
public class LambdaBlockProcessor extends AbstractProcessor<CtLambda> {

	@Override
	public void process(CtLambda lambda) {
		CodeFactory codeFactory = getFactory().Code();
		CoreFactory coreFactory = getFactory().Core();
		if(lambda.getBody() == null) {
			CtBlock block = coreFactory.createBlock();
			if(lambda.getExpression() instanceof CtStatement) {
				block.addStatement((CtStatement)lambda.getExpression().clone() );
				lambda.getExpression().delete();
				lambda.setBody(block);
				lambda.setExpression(null);
			}
//			block.addStatement(lambda.getExpression());
		}
	}

}
