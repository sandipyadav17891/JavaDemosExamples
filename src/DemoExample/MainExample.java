package DemoExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import DemoExample.tempObjects.Employee;

public class MainExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Given List of Int print only even number
		List<Integer> num=List.of(12,2,11,33,22,44,54,62);
		System.out.println(num.stream().filter(x ->x%2==0).collect(Collectors.toList()));
		
		//from List print the nums equals to 10
		List<Integer> numbers=List.of(2,1,5,7,4,5,8);
		compute(numbers);
		compute1();
		
		//print even or odd
		System.out.println(numbers.stream().map(x ->x%2==0 ? "even":"odd").collect(Collectors.toList()));
		
		//return the common element
		List<Integer> lista=List.of(1,4,67,55);
		List<Integer> listb=List.of(5,4,68,55);
		System.out.println(lista.stream().filter(listb::contains));
			
		// find the list of Strings that starts with s
		List<String> strs= List.of("sandip","alice","saroj","sinhi");
		System.out.println(strs.stream().filter(x ->x.startsWith("s")).collect(Collectors.toList()));
		
		//find teh sum of square of the no in the list
		List<Integer> numb=List.of(2,45,22,12,4,55,11);
		System.out.println(numb.stream().map(x ->x*x).collect(Collectors.toList()));
		//find the sum of Integer to do ******
		System.out.println("Sum of Number:: "+numb.stream().map(x ->(int)x).reduce(0, Integer::sum));
		//find the squre of every number and total finally
		System.out.println("Sum of square of Number:: "+numb.stream().map(x ->x*x).reduce(Integer::sum).get());
		
		//sort the list of String in descending oreder
		List<String> str= List.of("sandip","alice","saroj","sinhi");
		System.out.println(str.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
		
		//given an array retrun the list of element appears more than once in ascending order
		String[] arr= {"sandip","alice","saroj","sinhi","sandip","saroj","raghav"};
		List<String> arrList=Arrays.asList(arr);
		System.out.println(arrList.stream().filter(x ->Collections.frequency(arrList,x)>1).distinct().collect(Collectors.toList()));
		
		//groups words by there length 
		List<String> str2= List.of("sandip","alice","saroj","sinhi");
		System.out.println(str2.stream().collect(Collectors.groupingBy(x ->x.length(),Collectors.toList())));
		
		//convert the Lists of List into single List
		List<List<String>> listofList=List.of(
				List.of("a","b"),
				List.of("c","d"),
				List.of("e","f")
				);	
		System.out.println(listofList.stream().flatMap(List::stream).collect(Collectors.toList()));
		
		//given "hello World" pring the frequency of each charactre
		String hello="Hello World";
		System.out.println(hello.chars().mapToObj(x ->(char)x).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
		//find the first no repeating character
		List<Character> listChar=hello.chars().mapToObj(x ->(char)x).collect(Collectors.toList());
		System.out.println(listChar.stream().filter(x ->Collections.frequency(listChar, x)==1).findFirst().get());
		
		//collect the list of names into set
		List<String> names=List.of("sandip","sandip","ram","jeevan");
		Set<String> setNames=new HashSet();
		System.out.println(setNames.addAll(names));
		System.out.println(setNames);
		
		//find teh 2nd highest salary
		List<Integer> salary=List.of(10000,12000,4500,45333,50000);
		System.out.println(salary.stream().sorted(Comparator.reverseOrder()).limit(2).skip(1).collect(Collectors.toList()));
		
		//display the duplicate String 
		String dup="Hi I am Sandip Sandip";
		String[] duplicate=dup.split(" ");
		System.out.println(Arrays.asList(duplicate).stream().filter(x -> Collections.frequency(Arrays.asList(duplicate), x)>1).collect(Collectors.toList()));
		
		String str22="sandipyadav";
		System.out.println(str22.chars().mapToObj(x -> (char) x).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).
		entrySet()
		.stream()
		.max((s1,s2) ->s1.getValue().compareTo(s2.getValue())).get());
		
		String str1="balguptaary";
		Map<Character, Integer> charCounts = new HashMap();
		str1.chars().mapToObj(c -> (char)c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
		.entrySet().stream().max((s1, s2) -> s1.getValue().compareTo(s2.getValue())).get();
		
		
		//Object based examples ****************---------------********
		//find the largest salary
		List<Employee> emp=new ArrayList<>();
		emp=List.of(new Employee("sandip",12,5000.00,"IT"),new Employee("manjeet",13,4000.00,"IT"),new Employee("suraj",43,51999.00,"hardware"),
				new Employee("sandip",12,5200.00,"hardware"));
		List<Employee> largeSal=emp.stream().
		sorted((e1,e2) ->e2.getSalary().compareTo(e1.getSalary())).limit(1)
		.collect(Collectors.toList());		
		System.out.println("Largest Salary"+largeSal.toString());
		
		//Converting List of object to set
		Set empSet=new HashSet();
		empSet.addAll(emp);
		System.out.println("Converting to Set:: "+empSet);
		
		//Grouping by salary
		System.out.println(emp.stream()
		.collect(Collectors.groupingBy(x ->x.getSalary())));
		
		//Grouping by salary and get just name
		System.out.println(emp.stream()
		.collect(Collectors.groupingBy(x ->x.getSalary(),
				Collectors.mapping(y ->y.getName(), Collectors.toList()))));
		
		//Sum Salaries by Dept id
		System.out.println(emp.stream()
		.collect(Collectors.groupingBy(x ->x.getDept(), Collectors.summarizingDouble(x ->x.getSalary()))));
		
		
		//Adhoc count the consecutive 1's [1,1,0,1,1,1,0,1,1,1,1,0] **
		Integer[] intnum= {1,1,0,1,1,1,0,1,1,1,1};
		List<Integer> list=Arrays.asList(intnum);
		int counter=0;
		int countHold=0;
		for(Integer a:list) {
			if(a==1) {
				counter +=1;
				countHold=counter;
			}
			else {
				countHold=counter;
				counter =0;
			}			
		} System.out.println(countHold);
		
		SingletonClass s1;
		System.out.println(SingletonClass.initiate());
		System.out.println(SingletonClass.initiate());
		System.out.println(SingletonClass.initiate());
		//System.out.println(s1.initiate());
	}

	private static void compute1() {
		// TODO Auto-generated method stub
		
	}

	private static void compute(List<Integer> numbers) {
	Map<Integer,Integer> temp=new HashMap<>();
		for(Integer i:numbers) {
			for(Integer j:numbers) {
				if(i+j==10) {
					temp.put(i,j);
				}
			}
		}
		System.out.println(temp);
	}

}
