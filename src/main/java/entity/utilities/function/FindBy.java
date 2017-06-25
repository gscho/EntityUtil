package entity.utilities.function;

import java.util.function.BiPredicate;

public class FindBy<T, R> implements BiPredicate<T, R>{

	@Override
	public boolean test( T t, R u ){
		if( t == null || u == null ){
			return false;
		}
		try{
			return t.equals( u );
		}
		catch( Exception ex ){
			return false;
		}
	}

}
