package entity.utilities.test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Test;

import entity.utilities.EntityUtil;
import entity.utilities.test.object.TestObject;

public class EntityUtilTest{

	@SuppressWarnings( {"rawtypes"} )
	@Test
	public void explodeList(){
		List<TestObject> testList = new ArrayList<>();
		IntStream.range( 0, 5 ).forEach( i -> {
			TestObject test = new TestObject();
			test.setTestInteger( i );
			test.setTestString( "Test" + i );
			test.setTestList( Arrays.asList( i ) );
			test.setTestDate( new Date( i ) );
			testList.add( test );
		} );

		List<Integer> intResult = EntityUtil.explode( testList, "testInteger" );
		assertArrayEquals( new Integer[]{0, 1, 2, 3, 4}, intResult.toArray() );
		List<String> stringResult = EntityUtil.explode( testList, "testString" );
		assertArrayEquals( new String[]{"Test0", "Test1", "Test2", "Test3", "Test4"}, stringResult.toArray() );
		List<Date> dateResult = EntityUtil.explode( testList, "testDate" );
		assertArrayEquals( new Date[]{new Date( 0 ), new Date( 1 ), new Date( 2 ), new Date( 3 ), new Date( 4 )}, dateResult.toArray() );
		List<List> listResult = EntityUtil.explode( testList, "testList" );
		assertTrue( (Integer) listResult.size() == 5 );
		IntStream.range( 0, 5 ).forEach( i -> assertTrue( (Integer) listResult.get( i ).get( 0 ) == i ) );

	}

	@Test
	public void findBy(){
		List<TestObject> testList = new ArrayList<>();
		IntStream.range( 0, 5 ).forEach( i -> {
			TestObject test = new TestObject();
			test.setTestInteger( i );
			test.setTestString( "Test" + i );
			test.setTestList( Arrays.asList( i ) );
			test.setTestDate( new Date( i ) );
			testList.add( test );
		} );

		List<TestObject> intSearchResult = EntityUtil.findBy( testList, "testInteger", 1 );
		assertTrue( intSearchResult.size() == 1 );
		intSearchResult.forEach( elem -> {
			assertTrue( elem.getTestInteger().intValue() == 1 );
			assertTrue( elem.getTestString().equals( "Test1" ) );
			assertTrue( elem.getTestDate().equals( new Date( 1 ) ) );
			assertTrue( (Integer) elem.getTestList().get( 0 ) == 1 );

		} );

		final List<TestObject> foundIntSearchResult = EntityUtil.findIn( testList, "testInteger", Arrays.asList( 1, 2 ) );
		assertTrue( foundIntSearchResult.size() == 2 );
		IntStream.range( 0, 2 ).forEach( i -> {
			assertTrue( foundIntSearchResult.get( i ).getTestInteger().intValue() == i + 1 );
			assertTrue( foundIntSearchResult.get( i ).getTestString().equals( "Test" + ( i + 1 ) ) );
			assertTrue( foundIntSearchResult.get( i ).getTestDate().equals( new Date( i + 1 ) ) );
			assertTrue( (Integer) foundIntSearchResult.get( i ).getTestList().get( 0 ) == i + 1 );
		} );

		final List<TestObject> foundDateSearchResult = EntityUtil.findIn( testList, "testDate", Arrays.asList( new Date( 1 ), new Date( 2 ) ) );
		assertTrue( foundDateSearchResult.size() == 2 );
		IntStream.range( 0, 2 ).forEach( i -> {
			assertTrue( foundDateSearchResult.get( i ).getTestInteger().intValue() == i + 1 );
			assertTrue( foundDateSearchResult.get( i ).getTestString().equals( "Test" + ( i + 1 ) ) );
			assertTrue( foundDateSearchResult.get( i ).getTestDate().equals( new Date( i + 1 ) ) );
			assertTrue( (Integer) foundDateSearchResult.get( i ).getTestList().get( 0 ) == i + 1 );
		} );

	}

	@SuppressWarnings( "unchecked" )
	@Test
	public void reverseList(){
		List<Integer> intList = Arrays.asList( 1, 2, 3, 4, 5 );
		List<String> stringList = Arrays.asList( "Greg", "John", "Skyler", "Damith", "Kulwinder" );
		intList = (List<Integer>) EntityUtil.reverse( intList );
		assertArrayEquals( new Integer[]{5, 4, 3, 2, 1}, intList.toArray() );
		stringList = (List<String>) EntityUtil.reverse( stringList );
		assertArrayEquals( new String[]{"Kulwinder", "Damith", "Skyler", "John", "Greg"}, stringList.toArray() );
	}

	@SuppressWarnings( "unchecked" )
	@Test
	public void removeDuplicates(){
		List<Integer> intList = Arrays.asList( 1, 2, 3, 4, 3, 4, 7 );
		intList = (List<Integer>) EntityUtil.removeDuplicates( intList );
		assertTrue( intList.size() == 5 );
		assertThat( intList, hasItems( 1, 2, 3, 4, 7 ) );

		List<String> stringList = Arrays.asList( "Greg", "John", "Skyler", "Damith", "John", "Skyler", "Damith", "Kulwinder" );
		stringList = (List<String>) EntityUtil.removeDuplicates( stringList );
		assertTrue( stringList.size() == 5 );
		assertThat( stringList, hasItems( "Greg", "John", "Skyler", "Damith", "Kulwinder" ) );
	}

	@Test
	public void isNullOrEmpty(){
		List<? > nullList = null;
		List<Object> emptyList = new ArrayList<>();
		List<String> nonEmptyList = new ArrayList<>();
		nonEmptyList.add( "String" );
		assertTrue( EntityUtil.isNullOrEmpty( nullList ) == true );
		assertTrue( EntityUtil.isNullOrEmpty( emptyList ) == true );
		assertTrue( EntityUtil.isNullOrEmpty( nonEmptyList ) == false );

	}

	@SuppressWarnings( {"unchecked", "rawtypes"} )
	@Test
	public void fromList(){
		List<String> stringList = Arrays.asList( "A", "B", "C", "C", "C", "B", "D", "A" );
		List<TestObject> testList = new ArrayList<>();
		IntStream.range( 0, stringList.size() ).forEach( i -> {
			TestObject test = new TestObject();
			test.setTestString( stringList.get( i ) );
			testList.add( test );

		} );

		Map<String, List<String>> result = (Map) EntityUtil.mapFromList( testList, "testString" );
		List<String> a = result.get( "A" );
		List<String> b = result.get( "B" );
		List<String> c = result.get( "C" );
		List<String> d = result.get( "D" );
		assertThat( a, hasItems( "A", "A" ) );
		assertThat( b, hasItems( "B", "B" ) );
		assertThat( c, hasItems( "C", "C", "C" ) );
		assertThat( d, hasItems( "D" ) );

		result = (Map) EntityUtil.mapFromList( stringList );
		a = result.get( "A" );
		b = result.get( "B" );
		c = result.get( "C" );
		d = result.get( "D" );
		assertThat( a, hasItems( "A", "A" ) );
		assertThat( b, hasItems( "B", "B" ) );
		assertThat( c, hasItems( "C", "C", "C" ) );
		assertThat( d, hasItems( "D" ) );
	}

	@SuppressWarnings( "rawtypes" )
	@Test
	public void implodeDelimiter(){
		List<Integer> intList = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8 );
		String joinedList = EntityUtil.implode( intList, "-" );
		assertThat( joinedList, is( "1-2-3-4-5-6-7-8" ) );
		List<HashSet> objectList = Arrays.asList( new HashSet<>(), new HashSet<>(), new HashSet<>() );
		joinedList = EntityUtil.implode( objectList, "," );
		assertThat( joinedList, is( "[],[],[]" ) );
	}

	@Test
	public void trimList(){
		List<String> stringList = Arrays.asList( " ", " test", " the", "trim " );
		assertThat( EntityUtil.trimStringList( stringList ), is( Arrays.asList( "test", "the", "trim" ) ) );
	}

	@Test
	public void trimArray(){
		String[] stringArr = new String[]{" ", " test", " the", "trim "};
		assertThat( EntityUtil.trimStringArray( stringArr ), is( new String[]{"test", "the", "trim"} ) );
	}

}
