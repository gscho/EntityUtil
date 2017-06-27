package entity.utilities;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import entity.utilities.function.Explode;
import entity.utilities.function.FindBy;
import entity.utilities.function.Is;

public class EntityUtil{

	public static List<Integer> explodeId( List<? > list ){
		return explode( list, "id" );
	}

	@SuppressWarnings( {"unchecked", "rawtypes"} )
	public static <T> List<T> explode( List<? > list, String propertyName ){
		Explode explode = new Explode();
		return (List<T>) list.stream().map( elem -> explode.apply( elem, propertyName ) ).filter( elem -> elem != null ).collect( Collectors.toList() );
	}

	public static <T> List<T> findById( List<T> list, Integer id ){
		return findBy( list, "id", id );
	}

	public static <T> List<T> findByName( List<T> list, String name ){
		return findBy( list, "name", name );
	}

	public static <T> List<T> findIn( List<T> list, final String propertyName, final List<? > propertyValues ){
		return (List<T>) propertyValues.stream().map( elem -> findBy( list, propertyName, elem ) ).flatMap( List::stream ).collect( Collectors.toList() );

	}

	@SuppressWarnings( {"unchecked", "rawtypes"} )
	public static <T> List<T> findBy( List<T> list, final String propertyName, final Object propertyValue ){
		FindBy findBy = new FindBy();
		Explode explode = new Explode();
		return (List<T>) list.stream().filter( elem -> findBy.test( explode.apply( elem, propertyName ), propertyValue ) ).collect( Collectors.toList() );
	}

	public static List<? > reverse( List<? > list ){
		return IntStream.range( 0, list.size() ).mapToObj( i -> list.get( list.size() - i - 1 ) ).collect( Collectors.toList() );
	}

	public static String implode( List<? > list ){
		return implode( list, "," );
	}

	public static String implode( List<? > list, String delimiter ){
		return list.stream().map( elem -> elem.toString() ).collect( Collectors.joining( delimiter ) );
	}

	public static String implodeDelimeter( List<? > list, String str ){
		return implodeDelimeter( list, str, "," );
	}

	public static String implodeDelimeter( List<? > list, String str, String delimiter ){
		return list.stream().map( elem -> str ).collect( Collectors.joining( delimiter ) );
	}

	public static List<? > removeDuplicates( List<? > list ){
		return list.stream().distinct().collect( Collectors.toList() );
	}

	public static boolean isNullOrEmpty( List<? > list ){
		return list == null || list.isEmpty();
	}

	@SuppressWarnings( {"unchecked", "rawtypes"} )
	public static <T, K, V> Map<K, V> mapFromList( List<T> list, String property ){
		Explode explode = new Explode();
		return (Map<K, V>) list.stream().map( elem -> explode.apply( elem, property ) ).collect( Collectors.groupingBy( Function.identity() ) );
	}

	@SuppressWarnings( {"unchecked"} )
	public static <T, K, V> Map<Object, T> mapFromList( List<T> list ){
		return (Map<Object, T>) list.stream().collect( Collectors.groupingBy( Function.identity() ) );
	}

	public static String[] trimStringArray( String[] sArray ){
		return Stream.of( sArray ).map( String::trim ).filter( Is.not( String::isEmpty ) ).toArray( String[]::new );
	}

	public static List<String> trimStringList( List<String> list ){
		return list.stream().map( String::trim ).filter( Is.not( String::isEmpty ) ).collect( Collectors.toList() );
	}
}
