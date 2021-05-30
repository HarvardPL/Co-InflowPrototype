package lbs.harvard.coinflow;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;


@Documented
@Target({ FIELD, METHOD, PARAMETER })
public @interface CoInflow_channel {
	
	public enum SinkTypes {
		SINK, SOURCE 
	}
	String IDOfSinkLabel();
	SinkTypes SinkType();
}

