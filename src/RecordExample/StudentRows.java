package RecordExample;

import java.util.Objects;

public class StudentRows {

	String name;
	int age;
	public StudentRows(String string, int i) {
		this.name = string;
		this.age = i;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRows other = (StudentRows) obj;
		return Objects.equals(name, other.name);
	}
	@Override
	public String toString() {
		return "StudentRows [name=" + name + ", age=" + age + "]";
	}
	
	
	
	
}
