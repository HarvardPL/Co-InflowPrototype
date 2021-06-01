package demo;


public class Person {

	String phoneNum;
	int personId;
	
	public Person(String phoneNum, int personId) {
		super();
		this.phoneNum = phoneNum;
		this.personId = personId;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	
}
