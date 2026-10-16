package DemoExample;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class IntraedgePizzaOrder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Online Java Compiler
		// Use this editor to write, compile and run your Java code online
		/*
		* ## Pizza Party!
		* 
		* The office is throwing a pizza party and we need to figure out how many pizzas to order.
		* 
		* Collect everyone's choice of pizza topping so we can place the order:
		* Each colleague can choose a topping and it will grant them 2 slices of that type of pizza.
		* As long as one colleague chooses a topping, order at least one of that type of pizza.
		* 
		### Objective 1

		Create a utility which factors in colleagues' topping choices. Output each colleague's name and topping choice.
		Output the total number of pizzas needed by topping.

		#### Example Output:

		Name and Order:

		Tina: Pepperoni
		Gina: Pepperoni
		Sarah: Olives
		Tony: Pepperoni
		Garry: Pepperoni
		Sam: Pepperoni


		Pizzas:

		Pepperoni: 2
		Olives: 1
		*/
		//Pepperoni: 5
		//Olives: 1
		//logic 5*2 /pizzaSize;
		 int pizzasize=4;

		 Map<String,String> pizzas=Map.of(
		 "Tina","Pepperoni", 
		 "Gina","Pepperoni",
		 "sarah","Olives",
		 "Tony","Pepperoni",
		 "garry","Pepperoni",
		 "sam","Pepperoni");

		 Map<Object, Long> temp=pizzas.entrySet().stream().
		 collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.counting()));
				 //.groupingBy(Map.Entry::getValue));
      
 
		Map<Object, Object> pizzaorder=temp.entrySet().stream()
		//.map(x ->x.getValue()*2 /pizzasize)
		.collect(Collectors.toMap(Map.Entry::getKey, x ->x.getValue() / pizzasize *2 + (x.getValue() % pizzasize > 0 ? 1 : 0)));

		 System.out.println(pizzaorder);

		    }
		}
	


