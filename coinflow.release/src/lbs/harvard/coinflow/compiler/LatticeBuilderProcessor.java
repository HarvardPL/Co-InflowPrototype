package lbs.harvard.coinflow.compiler;

import java.io.File;
import java.util.Set;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtTypeReference;

public class LatticeBuilderProcessor extends AbstractProcessor<CtClass>{

	static String latticeBuilderFile = null;
	@Override
	public void process(CtClass clazz) {
		Set<CtTypeReference<?>> interfaces = clazz.getSuperInterfaces();
		for(CtTypeReference t : interfaces) {
			if(t.getQualifiedName().equals("lbs.harvard.coinflow.lattice.LatticeBuilder")) {
				latticeBuilderFile = t.getPosition().getFile().getAbsolutePath();
			}
		}
	}
}
