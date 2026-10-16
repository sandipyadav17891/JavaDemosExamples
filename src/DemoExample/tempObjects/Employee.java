package DemoExample.tempObjects;

import java.util.Objects;

public class Employee {

	String name;
	int empID;
	Double salary;
	String dept;
	
	
public Employee(String name, int empID, Double salary, String dept) {
		super();
		this.name = name;
		this.empID = empID;
		this.salary = salary;
		this.dept = dept;
	}
	
/*** uniqueness **/

	@Override
	public boolean equals(Object o) {
		/*
		 * if(this ==o) { 
		 * return true; 
		 * } 
		 * else if(!(o instanceof Employee)) 
		 * { 
		 * return false; 
		 * }
		 */
		Employee e =(Employee) o;
		return empID==e.getEmpID();
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(empID);
	}


	/** Getter and Setters ***/
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Employee(String name, int empID, Double salary) {
		super();
		this.name = name;
		this.empID = empID;
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", empID=" + empID + ", salary=" + salary + ", dept=" + dept + "]";
	}
	
	
}
