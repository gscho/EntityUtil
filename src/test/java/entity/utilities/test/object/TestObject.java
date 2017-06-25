package entity.utilities.test.object;

import java.util.Date;
import java.util.List;

public class TestObject{

	public String testString;

	public Integer testInteger;

	public Date testDate;

	public List<Object> testList;

	public TestObject(){

	}

	public String getTestString(){
		return testString;
	}

	public void setTestString( String testString ){
		this.testString = testString;
	}

	public Integer getTestInteger(){
		return testInteger;
	}

	public void setTestInteger( Integer testInteger ){
		this.testInteger = testInteger;
	}

	public Date getTestDate(){
		return testDate;
	}

	public void setTestDate( Date testDate ){
		this.testDate = testDate;
	}

	public List<Object> getTestList(){
		return testList;
	}

	public void setTestList( List<Object> testList ){
		this.testList = testList;
	}

	@Override
	public String toString(){
		return "TestObject [testString=" + testString + ", testInteger=" + testInteger + ", testDate=" + testDate + ", testList=" + testList + "]";
	}

}
