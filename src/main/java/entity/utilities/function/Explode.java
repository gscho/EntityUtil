package entity.utilities.function;

import java.util.function.BiFunction;

public class Explode<T, U, R> implements BiFunction<T, U, R>{

	@SuppressWarnings( "unchecked" )
	@Override
	public R apply( T t, U u ){
		if( t == null || u == null ){
			return null;
		}
		try{
			return (R) t.getClass().getField( (String) u ).get( t );
		}
		catch( NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e ){}
		return null;
	}

}
