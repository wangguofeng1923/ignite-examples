package com.wangguofeng1923.ignite.examples.domain;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.apache.ignite.cache.query.annotations.QueryTextField;

public class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/*Group Index Demo
	 // Indexed in a group index with "salary". 
	  @QuerySqlField(orderedGroups={@QuerySqlField.Group(
	    name = "age_salary_idx", order = 0, descending = true)})
	  private int age;

	  // Indexed separately and in a group index with "age". 
	  @QuerySqlField(index = true, orderedGroups={@QuerySqlField.Group(
	    name = "age_salary_idx", order = 3)})
	  private double salary;
	  
	  */
	  
	 /** Person ID (indexed). */
	/** Will be indexed in descending order. */
	  @QuerySqlField(index = true, descending = true)
	  private long id;

	  /** Organization ID (indexed). */
	  @QuerySqlField(index = true)
	  private long orgId;

	  /** First name */
	  @QuerySqlField(index = true)
	  private String firstName;

	  /** Last name (not indexed). */
	  /** Will be visible in SQL, but not indexed. */
	  @QuerySqlField
	  private String lastName;

	  /** Resume text (create LUCENE-based TEXT index for this field). */
	  /** Will be visible in SQL, but not indexed. */
	  @QueryTextField
	  private String resume;

	  /** Salary (indexed). */
	  @QuerySqlField(index = true)
	  private double salary;
	  @QuerySqlField(index = true)
	  private int age;
	  
	  private PersonKey personKey;
	  
	public PersonKey getPersonKey() {
		return personKey;
	}

	public void setPersonKey(PersonKey personKey) {
		this.personKey = personKey;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", orgId=" + orgId + ", age=" + age +", firstName=" + firstName + ", lastName=" + lastName
				+ ", resume=" + resume + ", salary=" + salary + "]";
	}
	
}
