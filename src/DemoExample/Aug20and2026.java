package DemoExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import DemoExample.tempObjects.Employee;

//copilot-ignore
public class Aug20and2026 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Given List of Int print only even number
		List<Integer> num=List.of(12,2,11,33,22,44,54,62);
		System.out.println(num.stream().filter(x ->x%2==0).collect(Collectors.toList()));
		
		//from List print the nums equals to 10
		List<Integer> numbers=List.of(2,1,5,7,4,5,8);
		compute(numbers);
		
		//print even or odd
		num.stream().map(x ->x%2==0 ? "even":"odd").forEach(System.out::println);
		System.out.println(num.stream()
				//.map(x ->x%2==0 ? "even":"odd")
				.collect(Collectors.toMap(Function.identity(), x ->x%2==0 ? "even":"odd" )));
		
		//return the common element
		List<String> lista=List.of("sandip","alice","saroj","sinhi");
        List<String> listb=List.of("raghav","alice","saroj","sinhi");
        System.out.println(listb.stream().filter(lista::contains).collect(Collectors.toList()));
        
     // find the list of Strings that starts with s
        List<String> strs= List.of("sandip","alice","saroj","sinhi");
        System.out.println(strs.stream().filter(x ->x.startsWith("s")).collect(Collectors.toList()));
        
      //find teh sum of square of the no in the list
        List<Integer> numb=List.of(2,45,22,12,4,55,11);
        System.out.println(numb.stream().map(x ->x*x).reduce(0,Integer::sum));
         
		//sort the list of String in descending oreder
		List<String> str= List.of("sandip","alice","saroj","sinhi");
		str.stream()
		.sorted(Comparator.reverseOrder()).forEach(System.out::println);
		
		//given an array retrun the list of element appears more than once in ascending order
         String[] strin= {"sandip","alice","saroj","sinhi","sandip","saroj","raghav"};
         List<String> temp=Arrays.asList(strin);
         System.out.println(temp.stream()
         .filter(x ->Collections.frequency(temp, x)>1).distinct().collect(Collectors.toList()));
         
 		//groups words by there length 
 		List<String> str2= List.of("sandip","alice","saroj","sinhi","sinhi");
 		System.out.println(str2.stream()
 		.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
 		
		//convert the Lists of List into single List
		List<List<String>> listofList=List.of(
				List.of("a","b"),
				List.of("c","d"),
				List.of("e","f")
				);
		listofList.stream().flatMap(List::stream); 
			
		//given "hello World" pring the frequency of each charactre
		String hello="Hello World";
		System.out.println(hello.chars().mapToObj(x ->(char)x)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
		//find the first no repeating character
		hello.chars().mapToObj(x ->(char)x).toList();
		System.out.println(hello.chars().mapToObj(x ->(char) x).
		filter(x ->Collections.frequency(hello.chars().mapToObj(y ->(char)y).toList(), x)==1)
		.findFirst().get());
		
		//collect the list of names into set
		List<String> names=List.of("sandip","sandip","ram","jeevan");
		Set<String> newset=new HashSet();
		System.out.println(newset.addAll(names));
		
		//find teh 2nd highest salary
		List<Integer> salary=List.of(10000,12000,4500,45333,50000);
		System.out.println(salary.stream()
		.sorted(Comparator.reverseOrder()).limit(2).skip(1).collect(Collectors.toList()));
		
		//display the duplicate String 
		String dup="Hi I am Sandip Sandip";
		String[] dupliactes=dup.split(" ");
		System.out.println(Arrays.asList(dupliactes).stream()
		.filter(x ->Collections.frequency(Arrays.asList(dupliactes), x)>1).distinct().collect(Collectors.toList()));
		
		
		//Object based examples ****************---------------********
		//find the largest salary
		List<Employee> emp=new ArrayList<>();
		emp=List.of(new Employee("sandip",12,5000.00,"IT"),new Employee("manjeet",13,4000.00,"IT"),new Employee("suraj",43,51999.00,"hardware"),
				new Employee("sandip",12,5200.00,"hardware"));
		emp.stream()
		.sorted((s1,s2) -> s2.getSalary().compareTo(s1.getSalary())).limit(1).forEach(System.out::println);
		
		//Grouping by salary
		System.out.println(emp.stream()
		.collect(Collectors.groupingBy(x ->x.getSalary())));
		
		//Grouping by salary and get just name
		System.out.println(emp.stream()
		.collect(Collectors.groupingBy(x ->x.getSalary(),
				Collectors.mapping(x ->x.getName(), Collectors.toList()))));
		
		//Sum Salaries by Dept id
		System.out.println(emp.stream()
		.collect(Collectors.groupingBy(x ->x.getDept(), Collectors.summarizingDouble(x ->x.getSalary()))));
		
		//hashing the password
		String password="Sandip@123";
		int pass1=Objects.hash(password);
		System.out.println(pass1);
		String password2=password.substring(0, password.length()-4);
		System.out.println(password2);
		int pass2=Objects.hash(password);
		System.out.println(pass2);
		/**
		private final Map<String, Transaction> processedTransactions = new HashMap<>();
		 
		public void process(Transaction transaction) {

		    String id = transaction.getId();
		  //db - storing the transaction in the database
		  //database , index and while calling the db call it 
		  //should not use wild 
		    //read operation 
		    //--1000 thread 200  800 
		    //struture -oracle  txn id, customerid
		    //nosql date MongoDB
		   synchronized (processedTransactions) {
		    if (!processedTransactions.containsKey(id)) {

		        processedTransactions.put(id, transaction);
               //store in DB
		        sendToDownstream(transaction);

		    }
		   }
		}
		 
		Transaction 
		-id
		-name
		-amount
		-date 
		
		ConcurrentHashMap<String, Transaction> processedTransactions = new ConcurrentHashMap<>();
		park in lst 2 hours 
		for{
		processedTransactions.put(password2, transaction);
		processedTransactions.put(password2, transaction);
		...}
		
		for {
			processedTransactions1=
		thread1 =updating to mongo1; for messagcenter update
		thread2 =updating to mongo2; for notifiaction update
		}
		
		@Document
		Class Transaction {
		    @Id
		    private String id;
		    private String name;
		    private double amount;
		    private Date date;
		}
		
		class dbconnect extends JPARepository<Transaction, String> {
		    // Custom query methods can be defined here if needed
		}
			
		@Configuration
		class genericconfig{
			
			@Bean
			dbconnect dbconnect() {
				return new dbconnect();
			}
		}
		
		@Service
		class TransactionService {
		    private final dbconnect transactionRepository;

		    @Autowired
		    public TransactionService(dbconnect transactionRepository) {
		        this.transactionRepository = transactionRepository;
		    }

		    public void processTransaction(Transaction transaction) {
		        String id = transaction.getId();
		        if (!transactionRepository.existsById(id)) {
		            transactionRepository.save(transaction);
		            sendToDownstream(transaction);
		        }
		    }

		    private void sendToDownstream(Transaction transaction) {
		        // Logic to send the transaction to downstream systems
		    }
		}
		**/
	}

	public static void compute(List<Integer> temp){
		Map<Integer,Integer> tempmap=new HashMap();
		for(Integer i:temp) {
			for(Integer j:temp) {
				if(i+j==10) {
					tempmap.putIfAbsent(i, j);
				}
			}
		}
		System.out.println(tempmap);
	}
}
