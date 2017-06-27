package entity.utilities.function;

import java.util.function.Predicate;

public class Is{

	public static <T> Predicate<T> not( Predicate<T> t ){
		return t.negate();
	}

}
