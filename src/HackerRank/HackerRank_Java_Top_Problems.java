package HackerRank;

import java.util.*;

/**
 * HackerRank Top Problems - Java Edition
 * Most commonly asked by: Amazon, Google, Microsoft, Apple, Facebook, etc.
 * 
 * All solutions include:
 * - Multiple approaches
 * - Time & Space complexity analysis
 * - Interview tips
 * - Common follow-up questions
 */

public class HackerRank_Java_Top_Problems {

    // =========================================================================
    // 1. STRING PROBLEMS
    // =========================================================================

    /**
     * 1.1 Reverse a String
     * Company: Amazon, Google, Apple
     * Difficulty: Easy
     * Frequency: Very High
     */
    static class StringReversal {
        
        // Approach 1: Using StringBuilder (simplest)
        static String reverseSimple(String s) {
            return new StringBuilder(s).reverse().toString();
            // Time: O(n), Space: O(n)
        }
        
        // Approach 2: Two pointers (INTERVIEW PREFERRED)
        static String reverseTwoPointers(String s) {
            char[] chars = s.toCharArray();
            int left = 0, right = chars.length - 1;
            
            while (left < right) {
                // Swap
                char temp = chars[left];
                chars[left] = chars[right];
                chars[right] = temp;
                left++;
                right--;
            }
            
            return new String(chars);
            // Time: O(n), Space: O(n) for char array
        }
        
        // Approach 3: Recursion (shows thinking)
        static String reverseRecursive(String s) {
            if (s.isEmpty()) return s;
            return reverseRecursive(s.substring(1)) + s.charAt(0);
            // Time: O(n), Space: O(n) call stack
        }
    }

    /**
     * 1.2 Check if String is Palindrome
     * Company: Microsoft, Apple, LinkedIn
     * Difficulty: Easy
     */
    static class PalindromeCheck {
        
        // Approach 1: Two pointers (OPTIMAL)
        static boolean isPalindrome(String s) {
            int left = 0, right = s.length() - 1;
            
            while (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            
            return true;
            // Time: O(n), Space: O(1)
        }
        
        // Approach 2: Ignore spaces and special chars
        static boolean isPalindromeAlphanumeric(String s) {
            int left = 0, right = s.length() - 1;
            
            while (left < right) {
                // Skip non-alphanumeric
                while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                    left++;
                }
                while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                    right--;
                }
                
                // Compare (case-insensitive)
                if (Character.toLowerCase(s.charAt(left)) != 
                    Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                
                left++;
                right--;
            }
            
            return true;
            // Time: O(n), Space: O(1)
        }
    }

    /**
     * 1.3 Longest Common Prefix
     * Company: Amazon, Google, Facebook
     * Difficulty: Easy
     */
    static class LongestCommonPrefix {
        
        // Approach 1: Character by character
        static String longestCommonPrefix_v1(String[] strs) {
            if (strs == null || strs.length == 0) return "";
            
            for (int i = 0; i < strs[0].length(); i++) {
                char c = strs[0].charAt(i);
                
                for (int j = 1; j < strs.length; j++) {
                    if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                        return strs[0].substring(0, i);
                    }
                }
            }
            
            return strs[0];
            // Time: O(n*m), Space: O(1)
        }
        
        // Approach 2: Sorting (clever)
        static String longestCommonPrefix_v2(String[] strs) {
            if (strs == null || strs.length == 0) return "";
            
            Arrays.sort(strs);
            String first = strs[0];
            String last = strs[strs.length - 1];
            
            int i = 0;
            while (i < first.length() && first.charAt(i) == last.charAt(i)) {
                i++;
            }
            
            return first.substring(0, i);
            // Time: O(n*m*log(n)), Space: O(1)
        }
    }

    /**
     * 1.4 Group Anagrams
     * Company: Amazon, Google, Microsoft, Uber
     * Difficulty: Medium
     */
    static class GroupAnagrams {
        
        // Approach 1: Sort characters as key
        static List<List<String>> groupAnagrams_v1(String[] strs) {
            Map<String, List<String>> map = new HashMap<>();
            
            for (String word : strs) {
                char[] chars = word.toCharArray();
                Arrays.sort(chars);  // Sort to get canonical form
                String key = new String(chars);
                
                map.putIfAbsent(key, new ArrayList<>());
                map.get(key).add(word);
            }
            
            return new ArrayList<>(map.values());
            // Time: O(n*k*log(k)), Space: O(n*k)
        }
        
        // Approach 2: Character count as key (BETTER)
        static List<List<String>> groupAnagrams_v2(String[] strs) {
            Map<String, List<String>> map = new HashMap<>();
            
            for (String word : strs) {
                int[] count = new int[26];  // a-z
                
                for (char c : word.toCharArray()) {
                    count[c - 'a']++;
                }
                
                String key = Arrays.toString(count);
                map.putIfAbsent(key, new ArrayList<>());
                map.get(key).add(word);
            }
            
            return new ArrayList<>(map.values());
            // Time: O(n*k), Space: O(n*k) - NO SORTING!
        }
    }

    // =========================================================================
    // 2. ARRAY & LIST PROBLEMS
    // =========================================================================

    /**
     * 2.1 Two Sum
     * Company: ALL TOP COMPANIES (especially Amazon)
     * Difficulty: Easy
     * Frequency: EXTREMELY HIGH
     */
    static class TwoSum {
        
        // WRONG approach: Brute force O(n²)
        static int[] twoSum_Brute(int[] nums, int target) {
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[]{};
            // Time: O(n²), Space: O(1) - DON'T USE!
        }
        
        // CORRECT approach: Hash Map (OPTIMAL)
        static int[] twoSum_Hash(int[] nums, int target) {
            Map<Integer, Integer> seen = new HashMap<>();  // {num: index}
            
            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];
                
                if (seen.containsKey(complement)) {
                    return new int[]{seen.get(complement), i};
                }
                
                seen.put(nums[i], i);
            }
            
            return new int[]{};
            // Time: O(n), Space: O(n) - USE THIS!
        }
        
        // Follow-up: If array is SORTED, use two pointers
        static int[] twoSum_Sorted(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    return new int[]{left + 1, right + 1};  // 1-indexed
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
            
            return new int[]{};
            // Time: O(n), Space: O(1) - EVEN BETTER IF SORTED!
        }
    }

    /**
     * 2.2 Contains Duplicate
     * Company: Amazon, Google, Microsoft
     * Difficulty: Easy
     */
    static class ContainsDuplicate {
        
        // Approach 1: Hash Set (OPTIMAL)
        static boolean containsDuplicate_Hash(int[] nums) {
            Set<Integer> seen = new HashSet<>();
            
            for (int num : nums) {
                if (seen.contains(num)) {
                    return true;
                }
                seen.add(num);
            }
            
            return false;
            // Time: O(n), Space: O(n)
        }
        
        // Approach 2: Sorting (space optimal)
        static boolean containsDuplicate_Sort(int[] nums) {
            Arrays.sort(nums);
            
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] == nums[i + 1]) {
                    return true;
                }
            }
            
            return false;
            // Time: O(n*log(n)), Space: O(1)
        }
    }

    /**
     * 2.3 Maximum Subarray (Kadane's Algorithm)
     * Company: Amazon, Google, Microsoft, Apple, Facebook
     * Difficulty: Medium
     * Frequency: EXTREMELY HIGH - MUST KNOW!
     */
    static class MaximumSubarray {
        
        // WRONG: Brute force
        static int maxSubarray_Brute(int[] nums) {
            int maxSum = Integer.MIN_VALUE;
            
            for (int i = 0; i < nums.length; i++) {
                int sum = 0;
                for (int j = i; j < nums.length; j++) {
                    sum += nums[j];
                    maxSum = Math.max(maxSum, sum);
                }
            }
            
            return maxSum;
            // Time: O(n²), Space: O(1) - DON'T USE!
        }
        
        // CORRECT: Kadane's Algorithm (MUST KNOW THIS!)
        static int maxSubarray_Kadane(int[] nums) {
            int maxSum = nums[0];
            int currentSum = nums[0];
            
            for (int i = 1; i < nums.length; i++) {
                // Either extend current subarray or start new
                currentSum = Math.max(nums[i], currentSum + nums[i]);
                maxSum = Math.max(maxSum, currentSum);
            }
            
            return maxSum;
            // Time: O(n), Space: O(1) - USE THIS!
            
            // Example: [-2, 1, -3, 4, -1, 2, 1, -5, 4]
            // Output: 6 (subarray [4, -1, 2, 1])
        }
        
        // Follow-up: Return the subarray itself
        static List<Integer> maxSubarray_WithIndices(int[] nums) {
            int maxSum = nums[0];
            int currentSum = nums[0];
            int start = 0, end = 0, tempStart = 0;
            
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] > currentSum + nums[i]) {
                    currentSum = nums[i];
                    tempStart = i;
                } else {
                    currentSum += nums[i];
                }
                
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                    start = tempStart;
                    end = i;
                }
            }
            
            List<Integer> result = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                result.add(nums[i]);
            }
            return result;
        }
    }

    /**
     * 2.4 Rotate Array
     * Company: Amazon, Google, Microsoft
     * Difficulty: Medium
     */
    static class RotateArray {
        
        // Approach 1: Extra space (not optimal)
        static void rotate_v1(int[] nums, int k) {
            int n = nums.length;
            k = k % n;  // Handle k > n
            
            int[] rotated = new int[n];
            for (int i = 0; i < n; i++) {
                rotated[i] = nums[(n - k + i) % n];
            }
            
            for (int i = 0; i < n; i++) {
                nums[i] = rotated[i];
            }
            // Time: O(n), Space: O(n)
        }
        
        // Approach 2: Reverse (OPTIMAL - In-place!)
        static void rotate_v2(int[] nums, int k) {
            int n = nums.length;
            k = k % n;
            
            // Reverse entire array
            reverse(nums, 0, n - 1);
            // Reverse first k elements
            reverse(nums, 0, k - 1);
            // Reverse remaining elements
            reverse(nums, k, n - 1);
            // Time: O(n), Space: O(1)
        }
        
        static void reverse(int[] nums, int start, int end) {
            while (start < end) {
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
                start++;
                end--;
            }
        }
    }

    // =========================================================================
    // 3. SORTING & SEARCHING
    // =========================================================================

    /**
     * 3.1 Merge Sorted Arrays
     * Company: Amazon, Google, Microsoft, Facebook
     * Difficulty: Easy
     */
    static class MergeSortedArrays {
        
        // Approach 1: Simple merge (not optimal)
        static void merge_Simple(int[] nums1, int m, int[] nums2, int n) {
            int[] temp = new int[m];
            System.arraycopy(nums1, 0, temp, 0, m);
            
            int p1 = 0, p2 = 0, p = 0;
            while (p1 < m && p2 < n) {
                if (temp[p1] <= nums2[p2]) {
                    nums1[p++] = temp[p1++];
                } else {
                    nums1[p++] = nums2[p2++];
                }
            }
            
            while (p1 < m) nums1[p++] = temp[p1++];
            while (p2 < n) nums1[p++] = nums2[p2++];
            // Time: O(m+n), Space: O(m)
        }
        
        // Approach 2: Backward traversal (OPTIMAL)
        static void merge_Backward(int[] nums1, int m, int[] nums2, int n) {
            int p1 = m - 1;          // Pointer to end of nums1
            int p2 = n - 1;          // Pointer to end of nums2
            int p = m + n - 1;       // Pointer to end of result
            
            while (p1 >= 0 && p2 >= 0) {
                if (nums1[p1] > nums2[p2]) {
                    nums1[p] = nums1[p1];
                    p1--;
                } else {
                    nums1[p] = nums2[p2];
                    p2--;
                }
                p--;
            }
            
            // If nums2 has remaining (nums1 remaining already in place)
            while (p2 >= 0) {
                nums1[p] = nums2[p2];
                p2--;
                p--;
            }
            // Time: O(m+n), Space: O(1) - USE THIS!
        }
    }

    /**
     * 3.2 Binary Search
     * Company: ALL COMPANIES
     * Difficulty: Medium
     * Frequency: EXTREMELY HIGH
     */
    static class BinarySearch {
        
        // Basic binary search
        static int binarySearch(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;  // Avoid overflow
                
                if (nums[mid] == target) {
                    return mid;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return -1;  // Not found
            // Time: O(log n), Space: O(1)
        }
        
        // Find first occurrence
        static int findFirst(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            int result = -1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                
                if (nums[mid] == target) {
                    result = mid;
                    right = mid - 1;  // Keep searching left
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return result;
        }
        
        // Find last occurrence
        static int findLast(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            int result = -1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                
                if (nums[mid] == target) {
                    result = mid;
                    left = mid + 1;  // Keep searching right
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return result;
        }
        
        // Search in rotated sorted array
        static int searchRotated(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                
                if (nums[mid] == target) {
                    return mid;
                }
                
                // Determine which side is sorted
                if (nums[left] <= nums[mid]) {  // Left side sorted
                    if (nums[left] <= target && target < nums[mid]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                } else {  // Right side sorted
                    if (nums[mid] < target && target <= nums[right]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
            
            return -1;
            // Time: O(log n), Space: O(1)
        }
    }

    // =========================================================================
    // 4. TREE PROBLEMS
    // =========================================================================

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    /**
     * 4.1 Tree Traversals
     * Company: ALL COMPANIES
     * Difficulty: Medium
     */
    static class TreeTraversals {
        
        // Inorder (Left → Root → Right)
        static List<Integer> inorder(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            inorderHelper(root, result);
            return result;
        }
        
        static void inorderHelper(TreeNode node, List<Integer> result) {
            if (node == null) return;
            inorderHelper(node.left, result);
            result.add(node.val);
            inorderHelper(node.right, result);
        }
        
        // Inorder iterative (using stack)
        static List<Integer> inorderIterative(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            TreeNode current = root;
            
            while (current != null || !stack.isEmpty()) {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
                
                current = stack.pop();
                result.add(current.val);
                current = current.right;
            }
            
            return result;
            // Time: O(n), Space: O(h)
        }
        
        // Level order (BFS)
        static List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) return result;
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                List<Integer> level = new ArrayList<>();
                
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    level.add(node.val);
                    
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
                
                result.add(level);
            }
            
            return result;
            // Time: O(n), Space: O(w) - w is max width
        }
    }

    /**
     * 4.2 Lowest Common Ancestor (LCA)
     * Company: Google, Amazon, Microsoft, Facebook
     * Difficulty: Medium
     */
    static class LCA {
        
        static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null) return null;
            
            // If both p and q are smaller, LCA in left
            if (p.val < root.val && q.val < root.val) {
                return lowestCommonAncestor(root.left, p, q);
            }
            
            // If both p and q are greater, LCA in right
            if (p.val > root.val && q.val > root.val) {
                return lowestCommonAncestor(root.right, p, q);
            }
            
            // Root is LCA
            return root;
            // Time: O(n), Space: O(h)
        }
    }

    // =========================================================================
    // 5. DYNAMIC PROGRAMMING
    // =========================================================================

    /**
     * 5.1 Fibonacci Sequence
     * Company: Many
     * Difficulty: Easy
     */
    static class Fibonacci {
        
        // WRONG: Recursion without memoization
        static int fib_Recursive(int n) {
            if (n <= 1) return n;
            return fib_Recursive(n - 1) + fib_Recursive(n - 2);
            // Time: O(2^n) - EXPONENTIAL, don't use!
        }
        
        // Memoization (top-down DP)
        static int fib_Memo(int n) {
            return fib_Memo(n, new int[n + 1]);
        }
        
        static int fib_Memo(int n, int[] memo) {
            if (memo[n] > 0) return memo[n];
            if (n <= 1) return n;
            
            memo[n] = fib_Memo(n - 1, memo) + fib_Memo(n - 2, memo);
            return memo[n];
            // Time: O(n), Space: O(n)
        }
        
        // Tabulation (bottom-up DP) - PREFERRED
        static int fib_Tabulation(int n) {
            if (n <= 1) return n;
            
            int[] dp = new int[n + 1];
            dp[1] = 1;
            
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            
            return dp[n];
            // Time: O(n), Space: O(n)
        }
        
        // Space optimized - BEST
        static int fib_Optimized(int n) {
            if (n <= 1) return n;
            
            int prev = 0, curr = 1;
            for (int i = 2; i <= n; i++) {
                int next = prev + curr;
                prev = curr;
                curr = next;
            }
            
            return curr;
            // Time: O(n), Space: O(1)
        }
    }

    /**
     * 5.2 Longest Increasing Subsequence (LIS)
     * Company: Amazon, Google, Microsoft, Facebook
     * Difficulty: Medium
     */
    static class LongestIncreasingSubsequence {
        
        // Approach 1: DP O(n²)
        static int lis_DP(int[] nums) {
            if (nums.length == 0) return 0;
            
            int n = nums.length;
            int[] dp = new int[n];
            Arrays.fill(dp, 1);  // Each element is LIS of length 1
            
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
            }
            
            return Arrays.stream(dp).max().orElse(0);
            // Time: O(n²), Space: O(n)
        }
        
        // Approach 2: Binary search O(n log n) - OPTIMAL
        static int lis_BinarySearch(int[] nums) {
            List<Integer> tails = new ArrayList<>();
            
            for (int num : nums) {
                int pos = Collections.binarySearch(tails, num);
                
                if (pos < 0) {
                    pos = -(pos + 1);
                }
                
                if (pos == tails.size()) {
                    tails.add(num);
                } else {
                    tails.set(pos, num);
                }
            }
            
            return tails.size();
            // Time: O(n log n), Space: O(n)
        }
    }

    // =========================================================================
    // DEMO & TESTING
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   HackerRank Top Problems - Java Solutions    ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        // String Problems
        System.out.println("=== STRING PROBLEMS ===\n");
        
        String s = "hello";
        System.out.println("Reverse '" + s + "': " + StringReversal.reverseTwoPointers(s));
        
        String palindrome = "racecar";
        System.out.println("Is '" + palindrome + "' palindrome: " + 
                          PalindromeCheck.isPalindrome(palindrome));
        
        String[] words = {"eat", "tea", "ate", "bat", "tab"};
        System.out.println("Group anagrams: " + GroupAnagrams.groupAnagrams_v2(words));

        // Array Problems
        System.out.println("\n=== ARRAY PROBLEMS ===\n");
        
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println("Two sum " + Arrays.toString(nums) + ", target=" + target + 
                          ": " + Arrays.toString(TwoSum.twoSum_Hash(nums, target)));
        
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Maximum subarray " + Arrays.toString(arr) + ": " + 
                          MaximumSubarray.maxSubarray_Kadane(arr));

        // Searching
        System.out.println("\n=== SEARCHING ===\n");
        
        int[] sorted = {1, 3, 5, 7, 9, 11};
        int searchTarget = 7;
        System.out.println("Binary search " + Arrays.toString(sorted) + ", target=" + searchTarget + 
                          ": " + BinarySearch.binarySearch(sorted, searchTarget));

        // Dynamic Programming
        System.out.println("\n=== DYNAMIC PROGRAMMING ===\n");
        
        int n = 10;
        System.out.println("Fibonacci(" + n + "): " + Fibonacci.fib_Optimized(n));
        
        int[] lisArr = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("LIS " + Arrays.toString(lisArr) + ": " + 
                          LongestIncreasingSubsequence.lis_BinarySearch(lisArr));

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║              Tests Complete!                   ║");
        System.out.println("╚════════════════════════════════════════════════╝");
    }
}
