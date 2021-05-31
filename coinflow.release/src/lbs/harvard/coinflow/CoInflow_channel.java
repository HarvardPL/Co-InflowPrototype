package lbs.harvard.coinflow;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

/**
 * 
 * @author Jian Xiang (jxiang@seas.harvard.edu)
 * Users can annotate the source and sink channels, including 
 * (1) field read (as source) and field write (as sink), 
 * (2) method signatures (as source) and method return values (as sink)
 * (3) parameter (as sink)   
 */
@Documented
@Target({ FIELD, METHOD, PARAMETER })
public @interface CoInflow_channel {
	
	public enum SinkTypes {
		SINK, SOURCE 
	}
	String IDOfSinkLabel();
	SinkTypes SinkType();
}

