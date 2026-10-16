package CollectionExamples;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

public class CollectionExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/** only for List and Set **/ //work on cloned copy
		//CopyOnWriteArrayList
		//CopyOnWriteArraySet
		
		
		/** Collections.synchronized **/ //Slow in nature and NO SYNCHRONIZEDQUEUE
		//Collections.synchronizedList(List)
		//Collections.synchronizedSet(set);
		//Collections.synchronizedMap(map);
		
		/** Collections.UnmodifiableList **///to create unmodifiable List,set and Map But No QUEUE
		//Collections.unmodifiableCollection(null)
		//Collections.unmodifiableList({List})
		//Collections.unmodifiableMap({Map})
		//Collections.unmodifiableSet({Set})
		
		/**ConcurrentHashMap **/ //Fast way to iterate and fast for synchronized operation
		/**BlockingQueue - ArrayBlockingQueue, LinkedBlockingQueue **/
		
		//CopyOnWriteArrayList
		List<String> copywriteArr=new CopyOnWriteArrayList();
		
		copywriteArr.add("cha1");
		copywriteArr=List.of("char3");
		Set<String> copyonWriteSet=new CopyOnWriteArraySet();
		
		Queue<String> pQue=new PriorityQueue();
		
		List<String> synList=Collections.synchronizedList(copywriteArr);
		
		synchronized(synList){   //Iterate over List using Synchronized its mandatory
		Iterator itr=synList.iterator();
		while(itr.hasNext()){
		System.out.println(itr.next());
	      }
		}
   
		/**ConcurrentHashMap **/ //Fast way to iterate and fast for synchronized operation
		//The problem with Hashtable and Collections.synchronizedMap(new HashMap()):
            //They use one global lock for all operations.
			//Reads block writes; writes block reads.
			//Under load, threads spend more time waiting than doing work.
		 /** ConcurrentHashMap divides the map into buckets of 16,32,64,128... and each bucket 
		  * contain the address based on hash values of key and values so that each bucket can 
		  * independently used by threads
		  * each bucket can hold multiple {HashMaps pairs}
		  * Buckets
		  * [0]  		Key ->(hash) bucket 0  ->HashMap pair 1
		  * [1]  		key ->(hash) bucket 1  ->HashMap pair 2
		  * [2]		    key ->(hash) bucket 2  ->HashMap pair 3
		  * [3]  		key ->(hash) bucket 3  ->HashMap pair 4
		  * ..
		  * [15]
		  */
		
		/**BlockingQueue - ArrayBlockingQueue, LinkedBlockingQueue **/
		 //A BlockingQueue is a thread‑safe queue that blocks:
			//producers when the queue is full
			//consumers when the queue is empty
		      /** - ArrayBlockingQueue **/ 
		      //⭐ Key characteristics
		      //Fixed capacity (must be given at construction)
			  //Single lock for both put and take
			  //Predictable memory footprint
			  //Fast, low overhead
			  //Optional fairness policy
		/** Why does ArrayBlockingQueue use a single lock?
		-> Because it stores elements in a fixed array:
		items[]  ← fixed-size array
		head     ← index for take
		tail     ← index for put
		count    ← number of elements  **/

		     /** -LinkedBlockingQueue **/
		     //⭐ Key characteristics
			//Optionally bounded (default = Integer.MAX_VALUE)
			//Two locks → higher concurrency
			//More throughput under heavy load
			//More GC overhead (nodes allocated per element)
		      //LBQ uses two locks
				//putLock   → used by producers
				//takeLock  → used by consumers

	
	}
}
