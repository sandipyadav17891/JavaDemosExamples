package MapExample;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStreamExamples20Jul26 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String,Integer> temp=new HashMap();
		temp.put("A", 60);
		temp.put("B", 10);
		temp.put("C", 40);
		temp.put("D", 20);
		
		//collect element more than 15
		System.out.println(temp.entrySet().stream()
		.filter(x ->x.getValue()>15)
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//Descending
		System.out.println(temp.entrySet().stream()
		.sorted((s1,s2) ->s2.getValue().compareTo(s1.getValue()))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a1,a2)->a1, LinkedHashMap::new)));
		
		//Ascending
		System.out.println(temp.entrySet().stream()
		.sorted((s1,s2) ->s1.getValue().compareTo(s2.getValue()))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a1,a2)->a1, LinkedHashMap::new)));
		
		//Map to List Key
		System.out.println(temp.keySet().stream().collect(Collectors.toList()));
		
		//Map to List Value
		System.out.println(temp.values().stream().collect(Collectors.toList()));
		
		//nth Element
		System.out.println(temp.entrySet().stream().filter(x ->x.getValue().equals(20)).findFirst().get().getKey());
		
		//group based on  even values
		System.out.println(temp.entrySet().stream()
		.filter(x ->x.getValue()%2==0)
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//find Min and Max
		System.out.println(temp.entrySet().stream()
		.sorted((s1,s2)->s1.getValue().compareTo(s2.getValue())).limit(1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		System.out.println(temp.entrySet().stream()
				.sorted((s1,s2)->s2.getValue().compareTo(s1.getValue())).limit(1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//get Only enabled feature
		
		
		//apply 10% discount
		Map<String,Double> discounted=Map.of(
				"Book", 100.0, 
				"pen",15.0);

		System.out.println(discounted.entrySet().stream()
		.collect(Collectors.toMap(Map.Entry::getKey, e ->e.getValue()*0.9)));
	
	}


	
}
