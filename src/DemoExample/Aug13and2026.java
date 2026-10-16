package DemoExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import DemoExample.tempObjects.Employee;

public class Aug13and2026 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Given List of Int print only even number
		List<Integer> num=List.of(12,2,11,33,22,44,54,62);
		System.out.println(num.stream().filter(x ->x%2==0).collect(Collectors.toList()));
		
		//from List print the nums equals to 10
		List<Integer> numbers=List.of(2,1,5,7,4,5,8);
		compute(numbers);
		
		//print even or odd
		System.out.println(num.stream().map(x ->x%2==0 ? "even":"odd").collect(Collectors.toList()));
		
		//return the common element
		List<Integer> lista=List.of(1,4,67,55);
		List<Integer> listb=List.of(5,4,68,55);
		System.out.println(lista.stream().filter(listb::contains).collect(Collectors.toList()));
		
		// find the list of Strings that starts with s
		List<String> strs= List.of("sandip","alice","saroj","sinhi");
		System.out.println(strs.stream().filter(x ->x.startsWith("s")).collect(Collectors.toList()));
		
		//find teh sum of square of the no in the list
		List<Integer> numb=List.of(2,45,22,12,4,55,11);
		System.out.println(numb.stream().map(x ->x*x).reduce(0, Integer::sum));
		
		//sort the list of String in descending oreder
		List<String> str= List.of("sandip","alice","saroj","sinhi");
		System.out.println(str.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
		
		//given an array retrun the list of element appears more than once in ascending order
		String[] arr= {"sandip","alice","saroj","sinhi","sandip","saroj","raghav"};
		List<String> arrString=Arrays.asList(arr);
		System.out.println(arrString.stream().filter(x ->Collections.frequency(arrString, x)>1).distinct().collect(Collectors.toList()));
		
		//groups words by there length 
		List<String> str2= List.of("sandip","alice","saroj","sinhi");
		System.out.println(str2.stream().collect(Collectors.groupingBy(x ->x.length())));
		
		//convert the Lists of List into single List
		List<List<String>> listofString=List.of(
				List.of("Sandip"),
				List.of("Ridhu"));
		System.out.println(listofString.stream().flatMap(List::stream).collect(Collectors.toList()));
		
		//given "hello World" print the frequency of each character
		String hello="Hello World";
		System.out.println(hello.chars().mapToObj(x ->(char)x).collect(Collectors.groupingBy(Function.identity(),Collectors.counting())));
		//find the first no repeating character
		List<Character> temp=hello.chars().mapToObj(x ->(char)x).collect(Collectors.toList());
		System.out.println(hello.chars().mapToObj(x ->(char)x).
				filter(x->Collections.frequency(temp, x)==1).findFirst());
		
		
		//find teh 2nd highest salary
		List<Integer> salary=List.of(10000,12000,4500,45333,50000);
		System.out.println(salary.stream().sorted((s1,s2) ->s2.compareTo(s1)).limit(2).skip(1).collect(Collectors.toList()));
		
		//display the duplicate String 
		String dup="Hi I am Sandip Sandip";
		String[] strings=dup.split(" ");
		System.out.println(Arrays.asList(strings).stream().filter(x ->Collections.frequency(Arrays.asList(strings), x)>1)
				.distinct()
				.collect(Collectors.toList()));
		
		//Object based examples ****************---------------********
		//find the largest salary
		List<Employee> emp=new ArrayList<>();
		emp=List.of(new Employee("sandip",12,5000.00,"IT"),new Employee("manjeet",13,4000.00,"IT"),new Employee("suraj",43,51999.00,"hardware"),
				new Employee("sandip",12,5200.00,"hardware"));	
		System.out.println(emp.stream().sorted((e1,e2) ->e2.getSalary().compareTo(e1.getSalary())).limit(1).collect(Collectors.toList()));
	
		//Grouping by salary
		System.out.println(emp.stream().collect(Collectors.groupingBy(x ->x.getSalary())));
		
		//Grouping by salary and get just name
		System.out.println(emp.stream().collect(Collectors.groupingBy(x ->x.getSalary(),Collectors.mapping(x ->x.getName(), Collectors.toList()))));
	
		//Sum Salaries by Dept id
		System.out.println(emp.stream().collect(Collectors.groupingBy(x ->x.getDept(), Collectors.summingDouble(x->x.getSalary()))));
		
	}

	private static void compute(List<Integer> numbers) {
	
		List<Integer> tmp=new ArrayList<>();
		for(Integer i:numbers) {
			for(Integer j:numbers) {
				if(i+j==10) {
					System.out.println(i+" "+j);
				}
			}
		}
		
	}

}
