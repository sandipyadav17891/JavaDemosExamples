package MapExample;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStreamExampes01Aug26 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<String, Integer> hashMap1 =
		        Map.of("manoj", 20,
		               "suraj", 30,
		               "sandip", 40,
		                "rathi", 11);
		
		//Collect Elements more than 15
		System.out.println(hashMap1.entrySet().stream()
		.filter(x ->x.getValue()>15).
		collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		
		//sort in Ascending
      System.out.println(hashMap1.entrySet().stream()
      .sorted((s1,s2)->s1.getValue().compareTo(s2.getValue())).collect(
      Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (s1,s2) ->s1, LinkedHashMap::new)));
      
		//Map to List -key
      System.out.println(hashMap1.keySet().stream().collect(Collectors.toList()));
		//Map to List -key and sorted in Ascending 
      System.out.println(hashMap1.keySet().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
      
		//Map to List -value
      System.out.println(hashMap1.values().stream().collect(Collectors.toList()));
      
      //find min
      System.out.println(hashMap1.entrySet().stream()
      .min((s1,s2)->s1.getValue().compareTo(s2.getValue())).get());
      
      //findMAx
      System.out.println(hashMap1.entrySet().stream()
      .min((s1,s2)->s2.getValue().compareTo(s1.getValue())).get());
		
	}

}
