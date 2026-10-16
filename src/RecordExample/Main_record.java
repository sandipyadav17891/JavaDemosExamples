package RecordExample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main_record {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Record student1 = new Student("John Doe", 20, "123 Main St");
		Record student2 = new Student("John Doe", 20, "123 Main St");
		Set<Record> students = new HashSet<>();
		students.add(student1);
		students.add(student2);
		System.out.println(students);
		
		StudentRows studentRows = new StudentRows("sandip",20);
		StudentRows studentRows1 = new StudentRows("sandip",20);
		StudentRows studentRows2 = new StudentRows("Randip",21);
		Set<StudentRows> studentRowsSet = new HashSet<>();
		studentRowsSet.add(studentRows);
		studentRowsSet.add(studentRows1);
			studentRowsSet.add(studentRows2);
			System.out.println(studentRowsSet);

		
	}

}
