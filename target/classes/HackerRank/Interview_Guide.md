# HackerRank & Coding Interview Complete Guide
## Most Common Problems Asked by Top Companies

## Key Strategies

### 1. The UMPIRE Framework

**U - Understand**
- Read problem twice
- Clarify assumptions with interviewer
- Identify inputs, outputs, constraints
- Ask edge cases

**M - Match**
- Match problem to a pattern (two pointers, hash map, etc.)
- Think of similar problems you've solved

**P - Plan**
- Write high-level approach
- Discuss complexity before coding
- Ask if approach is optimal

**I - Implement**
- Write clean, readable code
- Use meaningful variable names
- Add comments for complex logic

**R - Review**
- Test with examples (normal, edge cases, large inputs)
- Trace through code
- Check for off-by-one errors

**E - Evaluate**
- Discuss complexity with interviewer
- Can we optimize further?
- Are there trade-offs?

### 2. What Interviewers Look For

✅ **Good Signs:**
- Clear communication
- Asking clarifying questions
- Discussing trade-offs
- Clean, readable code
- Proper error handling
- Understanding complexity

❌ **Red Flags:**
- Jumping to code immediately
- Not asking questions
- Inefficient brute force
- No testing
- Ignoring edge cases
- Can't explain the approach

### 3. Time Management in Interview

- **First 5 min**: Understand the problem
- **5-15 min**: Discuss approach and complexity
- **15-35 min**: Write code
- **35-45 min**: Test and optimize

---

## Top Problems by Frequency

### Tier 1 (Asked >80% of times - MUST KNOW)

#### String Problems
1. **Reverse String** - Two pointers
2. **Palindrome Check** - Two pointers with alphanumeric handling
3. **Group Anagrams** - Character count or sorting
4. **Longest Common Prefix** - Character by character comparison
5. **Longest Substring Without Repeating** - Sliding window with set

#### Array Problems
1. **Two Sum** - Hash map (NOT two pointers unless sorted)
2. **Contains Duplicate** - Hash set
3. **Maximum Subarray (Kadane)** - MUST MEMORIZE
4. **Rotate Array** - Three reverses in-place
5. **Merge Sorted Arrays** - Backward traversal (don't overwrite)

#### Searching
1. **Binary Search** - Basic and variations
2. **Search in Rotated Array** - Know which side is sorted
3. **Find First/Last Occurrence** - Binary search variation

#### Tree Problems
1. **Inorder Traversal** - Recursive and iterative
2. **Level Order (BFS)** - Queue-based
3. **Lowest Common Ancestor** - DFS or iterative
4. **Path Sum** - DFS with backtracking

### Tier 2 (Asked 40-80% - SHOULD KNOW)

1. **Longest Increasing Subsequence** - DP O(n²) or binary search O(n log n)
2. **Coin Change** - DP
3. **Climbing Stairs** - DP or math
4. **Edit Distance** - DP 2D
5. **Merge K Sorted Lists** - Min heap or divide & conquer
6. **LRU Cache** - HashMap + DoubleLinkedList
7. **Valid Parentheses** - Stack
8. **Permutations** - Backtracking
9. **Combinations** - Backtracking
10. **Word Search** - DFS with backtracking

### Tier 3 (Asked 20-40% - GOOD TO KNOW)

1. **Median of Two Sorted Arrays** - Binary search (hard)
2. **Wildcard Matching** - DP (hard)
3. **Regular Expression Matching** - DP (hard)
4. **Serialize/Deserialize Tree** - BFS or DFS
5. **Number of Islands** - DFS/BFS
6. **Trapping Rain Water** - Two pointers or DP
7. **Longest Palindromic Substring** - Expand around center or DP

---

## Problem Patterns

### Pattern 1: Two Pointers
**When to use:** Sorted arrays, palindromes, partitioning

**Problems:**
- Two sum (sorted)
- Container with most water
- Merge sorted arrays
- Valid palindrome
- Three sum

**Template:**
```java
int left = 0, right = arr.length - 1;
while (left < right) {
    if (condition) {
        // process
        left++;
    } else {
        right--;
    }
}
```

### Pattern 2: Sliding Window
**When to use:** Substring, subarray, consecutive elements

**Problems:**
- Longest substring without repeating
- Minimum window substring
- Max sum subarray of size k
- Permutation in string

**Template:**
```java
int left = 0;
for (int right = 0; right < arr.length; right++) {
    add(arr[right]);
    
    while (invalid) {
        remove(arr[left]);
        left++;
    }
    
    update(result);
}
```

### Pattern 3: Hash Map / Set
**When to use:** Frequency, grouping, existence checks

**Problems:**
- Two sum
- Group anagrams
- Valid anagram
- Majority element
- First unique character

**Template:**
```java
Map<Type, Integer> map = new HashMap<>();
for (Type item : items) {
    map.put(item, map.getOrDefault(item, 0) + 1);
}
```

### Pattern 4: Binary Search
**When to use:** Sorted arrays, finding boundaries

**Problems:**
- Binary search
- Search rotated array
- Find first/last occurrence
- Sqrt(x)
- Median of sorted arrays

**Template:**
```java
int left = 0, right = arr.length - 1;
while (left <= right) {
    int mid = left + (right - left) / 2;  // Avoid overflow
    
    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) left = mid + 1;
    else right = mid - 1;
}
return -1;
```

### Pattern 5: Dynamic Programming
**When to use:** Overlapping subproblems, optimal substructure

**Problems:**
- Fibonacci
- Coin change
- Longest increasing subsequence
- Longest common subsequence
- Edit distance

**Template:**
```java
int[] dp = new int[n];
dp[0] = base_case;

for (int i = 1; i < n; i++) {
    dp[i] = recurrence_relation(dp[i-1], dp[i-2], ...);
}

return dp[n-1];
```

### Pattern 6: DFS/Backtracking
**When to use:** Permutations, combinations, paths

**Problems:**
- Permutations
- Combinations
- N-queens
- Word search
- Sudoku solver

**Template:**
```java
void dfs(current_state, path) {
    if (base_case) {
        add_to_result(path);
        return;
    }
    
    for (choice : choices) {
        make_choice(choice);
        dfs(new_state, path + choice);
        undo_choice(choice);  // Backtrack
    }
}
```

### Pattern 7: BFS/Level-order
**When to use:** Shortest path, level-based traversal

**Problems:**
- Level order traversal
- Shortest path in grid
- Number of islands
- Rotting oranges
- Word ladder

**Template:**
```java
Queue<Type> queue = new LinkedList<>();
queue.offer(start);

while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        Type current = queue.poll();
        process(current);
        
        for (Type neighbor : neighbors(current)) {
            queue.offer(neighbor);
        }
    }
}
```

---

## Time & Space Complexity Cheat Sheet

### Common Complexities (Best to Worst)

```
O(1)          - Constant
O(log n)      - Binary search, balanced trees
O(n)          - Single loop
O(n log n)    - Merge sort, quick sort, heap sort
O(n²)         - Nested loops, bubble sort, selection sort
O(n³)         - Triple nested loops
O(2^n)        - Subsets, recursive without memoization
O(n!)         - Permutations
```

### Complexity by Data Structure

| Operation | Array | LinkedList | Hash Set | Tree | Graph |
|-----------|-------|-----------|----------|------|-------|
| Search | O(n) | O(n) | O(1) avg | O(log n) | O(V+E) |
| Insert | O(n) | O(1) | O(1) avg | O(log n) | O(1) |
| Delete | O(n) | O(1) | O(1) avg | O(log n) | O(1) |
| Space | O(n) | O(n) | O(n) | O(n) | O(V+E) |

### Algorithm Complexities

| Algorithm | Time | Space | Notes |
|-----------|------|-------|-------|
| Bubble Sort | O(n²) | O(1) | Simple but slow |
| Selection Sort | O(n²) | O(1) | Minimum comparisons |
| Insertion Sort | O(n²) | O(1) | Good for small/sorted |
| Merge Sort | O(n log n) | O(n) | Stable, consistent |
| Quick Sort | O(n log n) avg | O(log n) | Fast in practice |
| Heap Sort | O(n log n) | O(1) | Good worst case |
| Binary Search | O(log n) | O(1) | Requires sorted input |
| DFS | O(V+E) | O(h) | Depth of tree |
| BFS | O(V+E) | O(w) | Width of tree |

---

## Spring Boot Specific Tips

### 1. Key Annotations to Know

```java
// Controller
@RestController, @RequestMapping, @GetMapping, @PostMapping

// Service/Business Logic
@Service, @Transactional

// Data Access
@Repository, @Entity, @Column

// Dependency Injection
@Autowired, @Qualifier, @Primary

// Validation
@Valid, @NotNull, @Size

// Caching
@Cacheable, @CacheEvict, @CachePut

// AOP
@Aspect, @Before, @After, @Around
```

### 2. Common Interview Questions

**Q: What's the difference between @Transactional and manual transaction management?**
A: @Transactional automatically manages commit/rollback. Manual gives more control but is error-prone.

**Q: How to handle N+1 query problem?**
A: Use FetchType.EAGER or @Query with JOIN FETCH to load related entities in one query.

**Q: What's the difference between @Entity and @Table?**
A: @Entity marks class as JPA entity, @Table specifies database table name.

**Q: How to implement pagination?**
A: Use PagingAndSortingRepository or PageRequest in method signature.

**Q: How to secure REST API?**
A: Spring Security with @EnableWebSecurity, SecurityFilterChain, authentication filters.

### 3. Best Practices

✅ Always use DTOs for API responses (never expose @Entity)
✅ Use @Transactional at service layer
✅ Implement proper exception handling with @RestControllerAdvice
✅ Use repository methods for queries, avoid JPQL in controllers
✅ Validate input with @Valid and custom validators
✅ Use lazy loading for related entities to avoid performance issues
✅ Cache frequently accessed data
✅ Use constructor injection instead of @Autowired for testability
✅ Write tests with @SpringBootTest and mock repositories

---

## Common Mistakes

### 1. String Problems
❌ Mutating string directly (strings are immutable)
✅ Convert to char array or use StringBuilder

❌ Off-by-one in comparisons
✅ Double-check boundary conditions

### 2. Array Problems
❌ Integer overflow in sum calculations
✅ Use long or check for overflow

❌ Modifying array while iterating
✅ Create new array or use index manipulation

### 3. Tree Problems
❌ Null pointer exceptions
✅ Always check if node is null before accessing

❌ Assuming binary search tree properties
✅ Read problem carefully - might just be binary tree

### 4. Dynamic Programming
❌ Wrong base case
✅ Test with smallest inputs

❌ Not considering all previous states
✅ Verify recurrence relation is correct

### 5. Spring Boot
❌ Modifying @Entity objects outside @Transactional
✅ Changes won't be persisted

❌ Using FetchType.EAGER for large collections
✅ Causes performance issues

❌ Mixing logic in controller
✅ Keep business logic in service layer

---

## Interview Roadmap (8-12 weeks)

### Week 1-2: Foundation
- Reverse string/array
- Palindrome check
- Two sum
- Contains duplicate
- Time & space complexity analysis

### Week 3-4: Arrays & Strings
- Maximum subarray (Kadane)
- Rotate array
- Group anagrams
- Longest common prefix
- Merge sorted arrays

### Week 5-6: Searching & Sorting
- Binary search and variations
- Merge sort
- Quick sort
- Search in rotated array

### Week 7-8: Trees & Graphs
- Tree traversals (DFS, BFS)
- Lowest common ancestor
- Number of islands
- Clone graph

### Week 9-10: Dynamic Programming
- Fibonacci
- Coin change
- Longest increasing subsequence
- Edit distance

### Week 11-12: Spring Boot
- REST API design
- JPA/Hibernate
- Service layer
- Exception handling
- Transactions

---

## Practice Strategy

### Daily (1-2 hours)
1. Solve 1 new problem
2. Review 1 previously solved problem
3. Discuss approach aloud

### Weekly (2-3 hours)
1. Do 1 timed mock interview (45 min)
2. Review mistakes
3. Learn new pattern

### Before Real Interview
1. Practice last 50 problems done
2. Do 3 mock interviews
3. Review Spring Boot concepts
4. Get good sleep!

---

## Interview Day Tips

### Before Interview
✅ Test microphone, camera, internet
✅ Have pen and paper ready
✅ Eliminate distractions
✅ Have water nearby
✅ Use the bathroom

### During Interview
✅ Read problem statement twice
✅ Ask clarifying questions
✅ Talk through your approach
✅ Write pseudocode first
✅ Test with examples
✅ Explain complexity analysis
✅ Discuss trade-offs

### Common Questions to Ask
1. "What are the constraints on input size?"
2. "Can I assume the input is always valid?"
3. "Should I handle null/empty inputs?"
4. "Is there a time/space constraint?"
5. "Can I modify the input?"
6. "Can I use extra space?"

---

## Expected Performance Levels

### Junior (0-2 years)
- Easy problems: 30-40 min
- Medium problems: Need hints
- Hard problems: Can't solve

### Mid-level (2-5 years)
- Easy problems: 5-10 min
- Medium problems: 20-30 min
- Hard problems: Partial solution

### Senior (5+ years)
- Easy problems: 2-5 min
- Medium problems: 10-20 min
- Hard problems: 30-40 min with solution

---

## Final Checklist

Before applying:
- [ ] Can solve 50+ easy problems
- [ ] Can solve 30+ medium problems
- [ ] Know all major patterns
- [ ] Understand Spring Boot basics
- [ ] Can explain complexity analysis
- [ ] Practiced mock interviews

---

## Resources

**Problem Websites:**
- LeetCode (1,000+ problems, best quality)
- HackerRank (Good for learning)
- CodeSignal (Interview practice)
- GeeksforGeeks (Theory + problems)

**Learning:**
- "Cracking the Coding Interview" book
- AlgoExpert (video explanations)
- InterviewBit (structured path)

**Spring Boot:**
- Official Spring documentation
- "Spring in Action" book
- Baeldung tutorials

---

## Summary

**MUST KNOW (Top 20):**
1. Two Sum
2. Valid Palindrome
3. Merge Sorted Arrays
4. Maximum Subarray (Kadane)
5. Contains Duplicate
6. Best Time to Buy Stock
7. Group Anagrams
8. Binary Search
9. Search Rotated Array
10. Merge K Sorted Lists
11. LRU Cache
12. Inorder Traversal
13. Level Order Traversal
14. Lowest Common Ancestor
15. Climbing Stairs
16. Coin Change
17. Longest Increasing Subsequence
18. Permutations
19. Word Search
20. Number of Islands

**SPRING BOOT ESSENTIALS:**
1. Entity & Repository design
2. Service layer patterns
3. REST API best practices
4. Exception handling
5. Transaction management
6. Caching strategies
7. Security basics

Good luck! 🎉
