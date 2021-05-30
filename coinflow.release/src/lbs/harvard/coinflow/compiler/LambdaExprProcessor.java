package lbs.harvard.coinflow.compiler;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLambda;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.reference.CtTypeReference;

public class LambdaExprProcessor extends AbstractProcessor<CtLambda>{

	@Override
	public void process(CtLambda lambda) {
		if(lambda.getParameters() != null && lambda.getParameters().size() > 0) {
			CtParameter p = (CtParameter)(lambda.getParameters().get(0));
			CtTypeReference pT = (CtTypeReference) p.getType();
			// System.out.println(p.getType());
			if(lambda.getExpression() != null) {
				CtExpression expr = lambda.getExpression();
				expr.setTypeCasts(null);
			}
		}
	}

}
