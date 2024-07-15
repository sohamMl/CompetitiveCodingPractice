package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.time.Duration;
import java.time.LocalTime;

import structures.ListNode;
import structures.MyQueue;
import structures.Robot;
import structures.node;
import structures.pair;


public class Solution {
    // public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedReader br;
    public static BufferedWriter bw;
    public static LocalTime start;
    public static LocalTime end;

    public static void setStartTime() {
        start = LocalTime.now();
    }

    public static void setEndTime() {
        end = LocalTime.now();
    }

    public static void showTimeTaken() {
        System.out.println("=========================");
        System.out.println(((float) Duration.between(start, end).toMillis() / 1000) + "s");
        System.out.println("=========================");
    }

    public static void showTimeTaken(String msg) {
        System.out.println("=========================");
        System.out.println(msg + "\n" + ((float) Duration.between(start, end).toMillis() / 1000) + "s");
        System.out.println("=========================");
    }

    // ~~~~~~~~~~~~~ MAIN METHOD ~~~~~~~~~~~~~
    public static void main(String[] args) {
        try {
//        	System.out.println(System.getProperty("user.dir"));
            br = new BufferedReader(new FileReader("src/resources/data.txt"));
            bw = new BufferedWriter(new FileWriter("src/resources/result.txt"));

            setStartTime();
            mergeKLists();
            setEndTime();
            showTimeTaken("Total time taken : ");

            br.close();
            bw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // problem_statements/search/ctci-ice-cream-parlor-English.pdf
    public static void whatFlavorsInput() throws IOException {
        int t = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < t; i++) {
            int money = Integer.parseInt(br.readLine().trim());
            int size = Integer.parseInt(br.readLine().trim());
            List<Integer> cost = Stream.of(br.readLine().trim().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            whatFlavors(cost, money);
        }
    }

    public static void whatFlavors(List<Integer> cost, int money) {
        Map<Integer, Integer> map = new HashMap<>();
        int size = cost.size();
        for (int i = 0; i < size; i++) {
            int c = cost.get(i);
            if (!map.containsKey(money - c))
                map.put(c, i);
            else {
                if (c < map.get(money - c)) {
                    System.out.println(i + 1 + " " + (map.get(money - c) + 1));
                    break;
                } else {
                    System.out.println(map.get(money - c) + 1 + " " + (i + 1));
                    break;
                }
            }
        }
    }

    // problem_statements/search/swap-nodes-algo-English.pdf  
    public static void swapNodesInput() throws IOException {
        int n = Integer.parseInt(br.readLine().trim());
        List<List<Integer>> indexes = new ArrayList<>();
        for (int i = 0; i < n; i++)
            indexes.add(Stream.of(br.readLine().replaceAll("\\s+$", "").split(" ")).map(Integer::parseInt).collect(Collectors.toList()));


        n = Integer.parseInt(br.readLine().trim());
        List<Integer> queries = new ArrayList<>();
        for (int i = 0; i < n; i++)
            queries.add(Integer.parseInt(br.readLine().trim()));

        swapNodes(indexes, queries).forEach(l -> System.out.println(l.toString()));
    }

    public static void inorder(node n, List<Integer> list) {
        if (n == null)
            return;
        if (n.left != null)
            inorder(n.left, list);

        // System.out.print(n.value+" ");
        list.add(n.value);

        if (n.right != null)
            inorder(n.right, list);
    }

    public static void depth_calc(node n, ArrayList<Integer> depth[], int index) {
        depth[index].add(n.value);
        if (n.left != null)
            depth_calc(n.left, depth, index + 1);
        if (n.right != null)
            depth_calc(n.right, depth, index + 1);

    }

    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {
        List<List<Integer>> resultList = new ArrayList<>();
        int n = indexes.size();
        node tree[] = new node[n + 1];
        ArrayList<Integer> depths[] = new ArrayList[n + 1];

        //initialising the tree(made of nodes) and the depth(list of nodes at depth i) array
        for (int i = 1; i <= n; i++) {
            tree[i] = new node(i);
            depths[i] = new ArrayList<>();
        }

        //setting the left and right child of nodes from the given input
        for (int i = 1; i <= n; i++) {
            int left = indexes.get(i - 1).get(0);
            int right = indexes.get(i - 1).get(1);
            if (right != -1)
                tree[i].right = tree[right];
            if (left != -1)
                tree[i].left = tree[left];
        }

        //calculating node depth and storing them in depths array
        depth_calc(tree[1], depths, 1);

        for (Integer query : queries) {
            int depth = query;
            while (depth <= n) {
                //swap nodes at the given depth
                for (Integer index : depths[depth]) {
                    node temp = tree[index].left;
                    tree[index].left = tree[index].right;
                    tree[index].right = temp;
                }
                depth += query;   //have to swap for multiples of the given depth
            }
            List<Integer> list = new ArrayList<>();
            inorder(tree[1], list);  //DFS on the tree
            // System.out.println(list.toString());
            resultList.add(list);
        }
        return resultList;
    }

    // problem_statements/search/pairs-English.pdf
    public static void pairsInput() throws IOException {
        String line1[] = br.readLine().trim().split(" ");
        int k = Integer.parseInt(line1[1]);
        List<Integer> arr = Arrays.asList(br.readLine().trim().split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());

        System.out.println(pairs(k, arr));
    }

    public static int pairs(int k, List<Integer> arr) {
        HashSet<Integer> set = new HashSet<>();
        arr.forEach(num -> set.add(num));
        //using a array since you cannot access variables from inside 
        //lambda functions unless it is final
        //since it is an array it is passing an reference
        int count[] = {0};
        arr.forEach(num -> {
            if (set.contains(k + num))
                count[0]++;
        });

        return count[0];
    }

    // problem_statements/search/triple-sum-English.pdf
    public static void tripletsInput() throws IOException {
        br.readLine();

        String as[] = br.readLine().trim().split(" ");
        String bs[] = br.readLine().trim().split(" ");
        String cs[] = br.readLine().trim().split(" ");

        int a[] = new int[as.length];
        int b[] = new int[bs.length];
        int c[] = new int[cs.length];

        for (int i = 0; i < as.length; i++)
            a[i] = Integer.parseInt(as[i]);

        for (int i = 0; i < bs.length; i++)
            b[i] = Integer.parseInt(bs[i]);

        for (int i = 0; i < cs.length; i++)
            c[i] = Integer.parseInt(cs[i]);

        System.out.println();
        System.out.println(triplets(a, b, c));
    }

    public static long triplets(int a[], int b[], int c[]) {

        long count = 0;

        // remove duplicate elements
        int[] ar = Arrays.stream(a).sorted().distinct().toArray();
        int[] br = Arrays.stream(b).sorted().distinct().toArray();
        int[] cr = Arrays.stream(c).sorted().distinct().toArray();

        int bi = 0;
        int ai = 0;
        int ci = 0;

        while (bi < br.length) {
            while (ai < ar.length && ar[ai] <= br[bi])
                ai++; //number of elements smaller than the element in b
            while (ci < cr.length && cr[ci] <= br[bi])
                ci++; // same as above
            count += (long) ai * ci; // this gives all the permutations
            // since ai and ci are integers, there can be a overflow
            // if it is not casted to long
            bi++;
        }

        return count;
    }

    // problem_statements/stacksAndQueues/balanced-brackets-English.pdf
    public static void balancedBracketsInput() throws NumberFormatException, IOException {
        int n = Integer.parseInt(br.readLine().trim());

        for (int i = 0; i < n; i++) {
            String str = br.readLine().trim();
            // System.out.println(balancedBrackets(str));
            // bw.write(balancedBrackets(str));
            bw.write(elegantBalancedBrackets(str));
            bw.newLine();
        }
    }

    public static String balancedBrackets(String str) {
        int flag = 1;
        LinkedList<Character> stack = new LinkedList<>();

        for (int i = 0; i < str.length() && flag == 1; i++) {
            char c = str.charAt(i);
            switch (c) {
                case '[': {
                    stack.add(c);
                    break;
                }
                case '{': {
                    stack.add(c);
                    break;
                }
                case '(': {
                    stack.add(c);
                    break;
                }
                case ')': {
                    if (stack.isEmpty()) flag = 0;
                    if (!stack.isEmpty() && stack.removeLast() != '(') flag = 0;
                    break;
                }
                case '}': {
                    if (stack.isEmpty()) flag = 0;
                    if (!stack.isEmpty() && stack.removeLast() != '{') flag = 0;
                    break;
                }
                case ']': {
                    if (stack.isEmpty()) flag = 0;
                    if (!stack.isEmpty() && stack.removeLast() != '[') flag = 0;
                    break;
                }
            }
            // System.out.println(stack.toString());
        }

        return flag == 1 && stack.isEmpty() ? "YES" : "NO";
    }

    public static String elegantBalancedBrackets(String str) {
        //credit : parasou79 - hackerrank
        int n = -1;
        while (str.length() != n) {
            n = str.length();
            str = str.replace("()", "");
            str = str.replace("{}", "");
            str = str.replace("[]", "");
        }
        return str.length() == 0 ? "YES" : "NO";
    }

    // problem_statements/stacksAndQueues/ctci-queue-using-two-stacks-English.pdf
    public static void queueInput() throws NumberFormatException, IOException {
        int k = Integer.parseInt(br.readLine().trim());
        MyQueue<Integer> q = new MyQueue<>();

        for (int i = 0; i < k; i++) {
            //int n = br.read();
            String input[] = br.readLine().trim().split(" ");
            int n = Integer.parseInt(input[0]);
            if (n == 1)
                q.enqueue(Integer.parseInt(input[1]));
            else if (n == 2)
                q.dequeue();
            else {
                // System.out.println(q.peek());
                bw.write(q.peek().toString());
                bw.newLine();
            }
        }
    }

    // problem_statements/stacksAndQueues/largest-rectangle.pdf
    public static void largestRectangleInput() throws NumberFormatException, IOException {
        Integer.parseInt(br.readLine().trim());
        List<Integer> h = Arrays.asList(br.readLine().trim().split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());
        bw.write(largestRectangle(h) + " ");
    }

    public static long largestRectangle(List<Integer> h) {
        int area = 0;
        // add 0 to the end of the list so that the stack will be emptied in the end
        h.add(0);

        // this stack will store the indexes of elements 
        List<Integer> stack = new ArrayList<Integer>();

        // start with pushing the first element into the stack
        //push - stack.add(0,val)
        //pop - stack.remove(0)
        //peek - stack.get(0)
        int i = 0;

        while (i < h.size()) {
            // if number from array is greater than top element push to the stack
            if (stack.isEmpty() || h.get(i) > h.get(stack.get(0))) {
                stack.add(0, i);
                i++;
            } else {
                // if the number from the array is smaller than the top element then
                //pop from the stack and then find the area
                int height_i = stack.remove(0); //pop 
                int areaT = 0;
                if (!stack.isEmpty()) {
                    int left = stack.get(0);  //peek
                    int right = i;
                    int width = right - left - 1;
                    areaT = width * h.get(height_i);// width * popped element
                } else {
                    // no smaller element is there on the left of the right element
                    int width = i;
                    areaT = width * h.get(height_i);
                }
                if (area < areaT) area = areaT;
            }
        }

        return area;
    }

    // Given an array of integers heights representing the histogram's bar height where the width 
    // of each bar is 1, return the area of the largest rectangle in the histogram.
    // https://leetcode.com/problems/largest-rectangle-in-histogram/
    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static int largestRectangleArea(int[] heights) {
        List<Integer> stack = new ArrayList<>();
        int sum = 0, tval;
        for (int i = 0; i <= heights.length; i++) {
            // i==heights.length is for the last case when you have to empty the stack
            // and compare the area after each pop
            while (!stack.isEmpty() && (i == heights.length || heights[i] < heights[stack.get(0)])) {
                tval = heights[stack.remove(0)];
                sum = max(sum, tval * (stack.isEmpty() ? i : i - stack.get(0) - 1));
            }
            stack.add(0, i);
        }
        return sum;
    }

    // online algo - can be improved using tree - binary search is used for finding 
    // element then insertion is performed - somewhat optimised insertion sort
    // https://www.geeksforgeeks.org/median-of-stream-of-integers-running-integers/
    public static void medianStreamIntegers() {
        // this is supposed to represent incoming stream of integers
        int ar[] = {4, 6, 1, 2, 9, 10, 7, 8, 1, 0, 9};
        List<Integer> l = new ArrayList<>();
        for (int i : ar) l.add(i);
        System.out.println(l);

        List<Integer> a = new ArrayList<>();
        for (Integer i : l) {
            if (a.size() == 0) a.add(i);
            else {
                int loc = ceil(a, i);
                // System.out.println(loc);
                if (loc == -1) a.add(i);
                else a.add(loc, i);
            }
            System.out.println(a);
        }
    }

    public static int ceil(List<Integer> ar, int key) {
        if (key <= ar.get(0)) return 0;
        if (key > ar.get(ar.size() - 1)) return -1;
        return ceil(ar, 0, ar.size() - 1, key);
    }

    public static int ceil(List<Integer> ar, int l, int r, int key) {
        int m;
        while (r - l > 1) {
            m = l + (r - l) / 2;
            if (ar.get(m) >= key) r = m;
            else l = m;
        }
        return r;
    }

    //You are climbing a staircase. It takes n steps to reach the top.
    //Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
    public static void countStairsInput() {
        int n = 8;
        int output = countStairs(n);
        System.out.println(output);
    }

    public static int countStairs(int n) {
        if (n == 0) return 1;
        if (n == 1) return 1;

        int i = 2;
        int Ci = 0, CiMinus1 = 1, CiMinus2 = 1;
        while (i <= n) {
            Ci = CiMinus1 + CiMinus2;

            CiMinus2 = CiMinus1;
            CiMinus1 = Ci;
            i++;
        }

        return Ci;
    }

    //https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/dynamic-programming/572/
    //You are given an array prices where prices[i] is the price of a given stock on the ith day.
    //You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
    //Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
    public static void maxProfitInput() {
        int[] n = {7, 1, 5, 3, 6, 4};
        int profit = maxProfit(n);
        System.out.println(profit);
    }

    public static int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int profit = 0;

        for (int price : prices) {
            if (price < min) min = price;
            else if ((price - min) > profit)
                profit = price - min;
        }

        return profit;
    }

    // https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/dynamic-programming/566/
    // Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
    // A subarray is a contiguous part of an array.
    public static void maxContSum() {
        int n[] = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int sum = maxContSumEfficient(n);
        System.out.println(sum);
    }

    public static int maxContSum(int nums[]) {
        int max = Integer.MIN_VALUE, temp = 0;
        for (int i = 0; i < nums.length; i++) {
            temp = 0;
            for (int j = i; j < nums.length; j++) {
                //temp = sumOfSubset(i,j)
                temp += nums[j];
                if (temp > max) max = temp;
            }
        }
        return max;
    }

    public static int maxContSumEfficient(int nums[]) {
        int max = nums[0];
        int temp = nums[0];

        for (int num : nums) {
            //temp is the sum of the current subset
            temp = Math.max(num, temp + num);
            max = Math.max(max, temp);
        }

        return max;
    }


    //https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/dynamic-programming/576/
    // You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint 
    // stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police 
    // if two adjacent houses were broken into on the same night.Given an integer array nums representing the amount of money of each house, return 
    // the maximum amount of money you can rob tonight without alerting the police.
    public static void houseRobber() {
        int n[] = {2, 7, 9, 3, 1};
        int max = houseRobber(n);
        System.out.println(max);
    }

    public static int houseRobber(int nums[]) {
        int _1 = nums[0];
        if (nums.length == 1) return _1;

        int _2 = Math.max(nums[0], nums[1]);
        if (nums.length == 2) return _2;

        int _3 = 0;

        // we mainly have to consider 3 elements ie _1,_2,_3
        // f(n) = max(f(n-1),f(n-2)+n)
        // f(0) = n0
        // f(1) = max(n0,n1)
        // f(2) = max(f(1), f(0) + n2)
        for (int i = 2; i < nums.length; i++) {

            _3 = Math.max(_2, nums[i] + _1);
            _1 = _2;
            _2 = _3;
        }

        return _3;
    }

    //https://leetcode.com/problems/is-subsequence/submissions/
    public static void isSubsequence() throws IOException {
        String s = br.readLine();
        String t = br.readLine();
        bw.write(isSubsequence(s, t) ? "true" : "false");
    }

    public static boolean isSubsequence(String s, String t) {
        int k = 0;
        for (int i = 0; i < t.length(); i++) {
            if (k == s.length()) break;
            if (s.charAt(k) == t.charAt(i)) k++;
        }
        return k == s.length() ? true : false;
    }

    //https://leetcode.com/problems/count-sorted-vowel-strings
    //https://leetcode.com/problems/count-sorted-vowel-strings/discuss/1459936/100-Faster 
    //see the pattern and you will find dp[j] = dp[j - 1] + dp[j];
    //CompetativeProgrammingPractice\src\problem_statements\images
    public static int kc = 0;

    public static void countVowelString() {
        String vowels[] = "aeiou".split("");
        System.out.println(Arrays.toString(vowels));
        countVowelString(568, "", "aeiou", 0);
        System.out.println(kc);
    }

    public static void countVowelString(int n, String a, String b, int k) {
        if (k == n) {
            kc++;
            System.out.println(a);
            return;
        }
        for (int i = 0; i < b.length(); i++) {
            String aN = a + b.charAt(i);
            String bN = b.substring(i, b.length());
            countVowelString(n, aN, bN, k + 1);
        }

    }


    //https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
    public static void removeDuplicates() throws NumberFormatException, IOException {
        String s, result;
        int k;

        int n = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < n; i++) {
            s = br.readLine().trim();
            s = s.substring(1, s.length() - 1);
            k = Integer.parseInt(br.readLine().trim());

            result = removeDuplicates(s, k);
            bw.write(result);
            bw.write("\n");
        }
    }

    public static String removeDuplicatesUnoptimised(String s, int k) {
        String tempString = "";
        // int startIndex = 0;
        boolean flag = true;

        int l = 0;
        int r = 0;


        while (flag) {
            flag = false;
            l = 0;
            r = 0;

            while (r < s.length()) {
                //check if l and r diff = k
                if (r - l == k) {
                    l = r;
                    flag = true;
                }

                //if l != r then move l to r and add to string 
                if (s.charAt(l) != s.charAt(r)) {
                    tempString += s.substring(l, r);
                    l = r;
                }

                r++;
            }

            if (l < r && (r - l != k))
                tempString += s.substring(l, r);

            s = tempString;
            tempString = "";
            System.out.println(s);
        }

        return s;
    }

    public static String removeDuplicates(String s, int k) {
        Stack<pair> stack = new Stack<>();

        //use a stack to push characters and if the top k items are same then start popping
        //store data in [char,count] way in stack to make the popping operation easier 
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty() && stack.peek().ch == c)
                stack.peek().count += 1;
            else
                stack.push(new pair(c, 1));
            while (!stack.isEmpty() && stack.peek().count == k)
                stack.pop();
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            pair p = stack.pop();
            for (int j = 0; j < p.count; j++) {
                sb.append(p.ch);
            }
        }
        return sb.reverse().toString();
    }


    //https://leetcode.com/problems/add-two-numbers/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int res = 0;
        boolean carryOver = false;
        ListNode head = new ListNode();
        ListNode currentNode = head;
        while (l1 != null || l2 != null || carryOver) {
            res = 0;
            if (carryOver) res = 1;

            if (l1 != null && l2 != null) {
                res += l1.val + l2.val;
                if (res > 9) carryOver = true;
                else carryOver = false;
                res = res % 10;

                l1 = l1.next;
                l2 = l2.next;
            } else if (l1 == null && l2 == null) {
                res = 1;
                carryOver = false;
            } else if (l1 == null) {
                res += l2.val;
                if (res > 9) carryOver = true;
                else carryOver = false;
                res = res % 10;
                l2 = l2.next;
            } else {
                res += l1.val;
                if (res > 9) carryOver = true;
                else carryOver = false;
                res = res % 10;
                l1 = l1.next;
            }


            if (currentNode == head) {
                head.val = res;
                head.next = new ListNode(res);
                currentNode = head.next;
            } else {
                currentNode.next = new ListNode(res);
                currentNode = currentNode.next;
            }

        }

        return head.next;
    }

    //not mine
    public ListNode addTwoNumbersBetterSolution(ListNode l1, ListNode l2) {

        ListNode dummy = new ListNode();
        ListNode temp = dummy;
        int carry = 0;


        while (l1 != null || l2 != null || carry == 1) {
            int sum = 0;

            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }


            sum += carry;
            carry = sum / 10;
            ListNode node = new ListNode(sum % 10);
            temp.next = node;
            temp = temp.next;
        }

        return dummy.next;

    }


    //https://leetcode.com/problems/generate-parentheses/submissions/
    public static void generateParenthesis() {
        int n = 16;
        ArrayList<String> list = new ArrayList<>();
        generateParenthesis("", n, n, list);
        System.out.println(list.size());

    }

    public static void generateParenthesis(String str, int l, int r, ArrayList<String> list) {
        //if count ( and ) = 0 return string
        if (l == 0 && r == 0) {
            //System.out.println(str);
            list.add(str);
            return;
        }

        // count ( = 0  then just add ) and reduce count of )
        else if (l == 0) {
            generateParenthesis(str + ")", l, r - 1, list);
        }
        //if count of ( and ) is greater than 0 then
        //recurse by one adding ( or one adding ) and reducing count
        // count (  < count )
        else {
            generateParenthesis(str + "(", l - 1, r, list);
            if (l < r)
                generateParenthesis(str + ")", l, r - 1, list);
        }
    }

    //https://leetcode.com/problems/walking-robot-simulation-ii/submissions/
    //my solution is faster - very weird 
    public static void controlRobot() {
        Robot robot = new Robot(4, 5);
        robot.step(44);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(19);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(8);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(36);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(17);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(49);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(14);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(40);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(18);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(7);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(8);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(5);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(2);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(36);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());
        robot.step(22);
        System.out.println(Arrays.toString(robot.getPos()) + "   " + robot.getDir());


//    	robot = new Robot(6,3);
//    	robot.step(2);
//    	System.out.println(Arrays.toString(robot.getPos())+"   "+robot.getDir());
//    	robot.step(2);
//    	System.out.println(Arrays.toString(robot.getPos())+"   "+robot.getDir());
//    	robot.step(2);
//    	System.out.println(Arrays.toString(robot.getPos())+"   "+robot.getDir());
//    	robot.step(1);
//    	System.out.println(Arrays.toString(robot.getPos())+"   "+robot.getDir());
//    	robot.step(4);
//    	System.out.println(Arrays.toString(robot.getPos())+"   "+robot.getDir());

    }


    public static void lengthOfLongestSubstring() {
        String s = "abcabcbb";
        int answer = lengthOfLongestSubstring(s);
        System.out.println(answer);
    }

    private static int lengthOfLongestSubstring(String s) {

        int maxLength = 0;
        if (s.length() == 1) return 1;

        HashSet<Character> currentStatus = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            // reset the character tracker
            currentStatus.clear();

            currentStatus.add(s.charAt(i));

            for (int j = i + 1; j < s.length(); j++) {

                char ch = s.charAt(j);
                if (currentStatus.contains(ch)) {
                    int uniqueLength = j - i;
                    if (uniqueLength > maxLength)
                        maxLength = uniqueLength;
                    break;
                } else {
                    currentStatus.add(ch);
                    if (j == s.length() - 1) {
                        int uniqueLength = s.length() - i;
                        if (uniqueLength > maxLength)
                            maxLength = uniqueLength;
                    }
                }
            }
        }

        return maxLength;
    }


    //https://leetcode.com/problems/integer-to-roman/submissions/
    public static void intToRoman() {
        int num = 3765;
        String roman = intToRomanElegant(num);

        System.out.println(roman);
    }

    public static String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();

        Map<Integer, String> symbols = new HashMap<>();
        String[] nums = "1 2 3 4 5 6 7 8 9 10 20 30 40 50 60 70 80 90 100 200 300 400 500 600 700 800 900 1000 2000 3000".split(" ");
        String[] romans = "I II III IV V VI VII VIII IX X XX XXX XL L LX LXX LXXX XC C CC CCC CD D DC DCC DCCC CM M MM MMM".split(" ");

        for (int i = 0; i < nums.length; i++)
            symbols.put(Integer.parseInt(nums[i]), romans[i]);

        int place = 1000;

        while (place > 0) {
            int digit = num / place;
            roman.append(symbols.get(digit * place) == null ? "" : symbols.get(digit * place));
            num %= place;
            place /= 10;
        }

        return roman.toString();
    }

    //from discussion
    public static String intToRomanElegant(int num) {
        String ones[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String tens[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String hrns[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String ths[] = {"", "M", "MM", "MMM"};

        return ths[num / 1000] + hrns[(num % 1000) / 100] + tens[(num % 100) / 10] + ones[num % 10];
    }


    //https://leetcode.com/problems/divide-two-integers/submissions/
    public static void divide() {
        int dividend = Integer.MIN_VALUE;
        int divisor = 1;

//    	int answer = divide(dividend,divisor);
        int answer = divide_eff(dividend, divisor);

        System.out.println(answer);
    }

    public static int divide(int dividend, int divisor) {

        long dt = dividend, d = divisor, q = 0;

        boolean pos = true;
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0))
            pos = false;

        if (dividend < 0) dt = -dt;
        if (divisor < 0) d = -d;


        if (d == 1) q = pos ? dt : -dt;
        else if (dt == d)
            return pos ? 1 : -1;
        else {
            while (dt >= d) {
                dt -= d;

                if (pos) q++;
                else q--;
            }
        }

        int qoutient = 0;

        if (q > Integer.MAX_VALUE)
            qoutient = Integer.MAX_VALUE;
        else if (q < Integer.MIN_VALUE)
            qoutient = Integer.MIN_VALUE;
        else
            qoutient = (int) q;

        return qoutient;
    }

    public static int divide_eff(int dividend, int divisor) {

        boolean isPos = (dividend < 0) ^ (divisor < 0) ? false : true;

        long dt = dividend;
        long d = divisor;

        if (dt < 0) dt = -dt;
        if (d < 0) d = -d;

        long q = 0, t = dt;

        for (int i = 31; i >= 0; i--) {

            //this gets the most significant bit of the qoutient
            //for the remaining dividend
            if (d << i <= t) {

                //getting value from the significant bit
                if (isPos) q += 1L << i;
                else q -= 1L << i;

                //updating the current dividend value
                t -= d << i;
            }
        }


        if (q > Integer.MAX_VALUE) return Integer.MAX_VALUE;

        if (q < Integer.MIN_VALUE) return Integer.MIN_VALUE;

        return (int) q;
    }


    //https://leetcode.com/problems/container-with-most-water/
    public static void maxArea() throws NumberFormatException, IOException {
        int inputs = Integer.parseInt(br.readLine());
        for (int i = 0; i < inputs; i++) {

            setStartTime();
            int[] heights = Stream.of(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
            setEndTime();
            showTimeTaken("For reading input - ");

            setStartTime();
            int area = maxAreaEff(heights);
            setEndTime();

            System.out.println(area);
            showTimeTaken("For method execution - ");
        }
    }


    public static int maxArea(int[] height) {
        int max = 0, l, r, area;

        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                l = height[i];
                r = height[j];

                area = l < r ? l * (j - i) : r * (j - i);

                if (area > max) max = area;
            }
        }

        return max;

    }


    public static int maxAreaEff(int[] height) {
        int max = 0, i = 0, j = height.length - 1, area, l, r;

        while (i < j) {
            l = height[i];
            r = height[j];
            area = l < r ? l * (j - i) : r * (j - i);

            if (area > max) max = area;

            if (l < r) i++;
            else j--;
        }

        return max;
    }


    //https://leetcode.com/problems/search-in-rotated-sorted-array/
    public static void search() {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 1;

        int index = search(nums, target);
        System.out.println(index);
    }

    public static int search(int[] nums, int target) {
        int l, r, m;
        //find pivot point
        l = 0;
        r = nums.length - 1;
        m = (l + r) / 2;

        if (nums[0] > nums[nums.length - 1]) {
            while (!(nums[m] < nums[m - 1] && nums[m] < nums[l + 1])) {
                if (nums[m] > nums[r]) l = m + 1;  //selecting the right half
                else r = m - 1;  //selecting the left half
                m = (l + r) / 2;
            }
        }


        //then apply binary search on the right or left side of pivot point
        if (target == nums[m]) return m;

        if (target < nums[m] || target > nums[m - 1]) return -1;

        if (nums[0] <= target && target <= nums[m - 1]) {
            l = 0;
            r = m - 1;
        }

        if (nums[m + 1] <= target && target <= nums[nums.length - 1]) {
            l = m + 1;
            r = nums.length - 1;
        }

        m = (l + r) / 2;

        if (nums[0] > nums[nums.length - 1]) {
            while (l <= r) {
                if (target == nums[m]) return m;

                if (target < nums[m]) r = m - 1;
                else l = m + 1;
                m = (l + r) / 2;
            }
        }

        return -1;
    }


    //https://leetcode.com/problems/group-anagrams/description/
    public static void groupAnagrams() {
        String[] strs = {"cab", "tin", "pew", "duh", "may", "ill", "buy", "bar", "max", "doc"};
        System.out.println(groupAnagrams(strs));
    }

    public static String sortedWord(String word) {
        char[] ar = word.toCharArray();
        Arrays.sort(ar);
        return String.valueOf(ar);
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> anagramMap = new HashMap<>();
        for (String word : strs) {
            String wordHash = sortedWord(word);
            if (anagramMap.containsKey(wordHash)) anagramMap.get(wordHash).add(word);
            else {
                anagramMap.put(wordHash, new ArrayList<String>());
                anagramMap.get(wordHash).add(word);
            }
        }

        return new ArrayList<>(anagramMap.values());
    }


    //https://leetcode.com/problems/longest-palindromic-substring/
    // this is can be further improved with Manacher's algorithm
    public static void longestPalindrome() {
        String word;
        word = "rirfadyqgztixemwswtcctwsdfnchcovrmiooffbbijkecuvlvukecutasfxqcqygltrogrdxlrslbnzktlanycgtniprjlospzhhgdrqcwlukbpsrumxguskubokxcmswjnssbkutdhppsdckuckcbwbxpmcmdicfjxaanoxndlfpqwneytatcbyjmimyawevmgirunvmdvxwdjbiqszwhfhjmrpexfwrbzkipxfowcbqjckaotmmgkrbjvhihgwuszdrdiijkgjoljjdubcbowvxslctleblfmdzmvdkqdxtiylabrwaccikkpnpsgcotxoggdydqnuogmxttcycjorzrtwtcchxrbbknfmxnonbhgbjjypqhbftceduxgrnaswtbytrhuiqnxkivevhprcvhggugrmmxolvfzwadlnzdwbtqbaveoongezoymdrhywxcxvggsewsxckucmncbrljskgsgtehortuvbtrsfisyewchxlmxqccoplhlzwutoqoctgfnrzhqctxaqacmirrqdwsbdpqttmyrmxxawgtjzqjgffqwlxqxwxrkgtzqkgdulbxmfcvxcwoswystiyittdjaqvaijwscqobqlhskhvoktksvmguzfankdigqlegrxxqpoitdtykfltohnzrcgmlnhddcfmawiriiiblwrttveedkxzzagdzpwvriuctvtrvdpqzcdnrkgcnpwjlraaaaskgguxzljktqvzzmruqqslutiipladbcxdwxhmvevsjrdkhdpxcyjkidkoznuagshnvccnkyeflpyjzlcbmhbytxnfzcrnmkyknbmtzwtaceajmnuyjblmdlbjdjxctvqcoqkbaszvrqvjgzdqpvmucerumskjrwhywjkwgligkectzboqbanrsvynxscpxqxtqhthdytfvhzjdcxgckvgfbldsfzxqdozxicrwqyprgnadfxsionkzzegmeynyee";
        word = "reifadyqgztixemwswtccodfnchcovrmiooffbbijkecuvlvukecutasfxqcqygltrogrdxlrslbnzktlanycgtniprjlospzhhgdrqcwlukbpsrumxguskubokxcmswjnssbkutdhppsdckuckcbwbxpmcmdicfjxaanoxndlfpqwneytatcbyjmimyawevmgirunvmdvxwdjbiqszwhfhjmrpexfwrbzkipxfowcbqjckaotmmgkrbjvhihgwuszdrdiijkgjoljjdubcbowvxslctleblfmdzmvdkqdxtiylabrwaccikkpnpsgcotxoggdydqnuogmxttcycjorzrtwtcchxrbbknfmxnonbhgbjjypqhbftceduxgrnaswtbytrhuiqnxkivevhprcvhggugrmmxolvfzwadlnzdwbtqbaveoongezoymdrhywxcxvggsewsxckucmncbrljskgsgtehortuvbtrsfisyewchxlmxqccoplhlzwutoqoctgfnrzhqctxaqacmirrqdwsbdpqttmyrmxxawgtjzqjgffqwlxqxwxrkgtzqkgdulbxmfcvxcwoswystiyittdjaqvaijwscqobqlhskhvoktksvmguzfankdigqlegrxxqpoitdtykfltohnzrcgmlnhddcfmawiriiiblwrttveedkxzzagdzpwvriuctvtrvdpqzcdnrkgcnpwjlraaaaskgguxzljktqvzzmruqqslutiipladbcxdwxhmvevsjrdkhdpxcyjkidkoznuagshnvccnkyeflpyjzlcbmhbytxnfzcrnmkyknbmtzwtaceajmnuyjblmdlbjdjxctvqcoqkbaszvrqvjgzdqpvmucerumskjrwhywjkwgligkectzboqbanrsvynxscpxqxtqhthdytfvhzjdcxgckvgfbldsfzxqdozxicrwqyprgnadfxsionkzzegmeynye";
        word = "bananas";
        word = "aaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaa";
        System.out.println(longestPalindrome(word));
        System.out.println(longestPalindrome2(word));
    }

    public static boolean palindromCheck(String word) {
        for (int i = 0, j = word.length() - 1; i < word.length() / 2; i++, j--) {
            if (word.charAt(i) != word.charAt(j)) return false;
        }
        return true;
    }

    public static boolean palindromCheck(char[] ar, int start, int end) {
        for (int i = start; i <= end; i++) System.out.print(ar[i]);
        while (start < end) {
            if (ar[start] != ar[end]) {
                System.out.println();
                return false;
            }
            start++;
            end--;
        }
        System.out.println("---");
        return true;
    }

    public static String longestPalindrome(String word) {
        for (int k = word.length(); k > 0; k--) {
            for (int i = 0; i <= word.length() - k; i++) {
                String temp = word.substring(i, i + k);
                if (palindromCheck(temp)) return temp;
            }
        }
        return "";
    }

    public static String longestPalindrome2(String word) {
        char[] ar = word.toCharArray();
        int length = word.length();
        int s = 0, e = 1;
        int fs = 0, fe = 0;
        int size = 0;

        //check for even length palindromes
        for (int i = 1; i < length; i++) {
            s = i - 1;
            e = i;
            while (s >= 0 && e < length && ar[s] == ar[e]) {
                s--;
                e++;
            }
            s++;
            e--;
//            for(int k=s;k<=e;k++) System.out.print(ar[k]);
//            System.out.println(String.format(" start :%d-%c end:%d-%c",s,ar[s],e,ar[e]));
            if (e - s > size) {
                size = e - s;
                fs = s;
                fe = e;
            }

        }

        // check for odd length palindromes
        for (int i = 1; i < length - 1; i++) {
            s = i - 1;
            e = i + 1;
            while (s >= 0 && e < length && ar[s] == ar[e]) {
                s--;
                e++;
            }
            s++;
            e--;
//            for(int k=s;k<=e;k++) System.out.print(ar[k]);
//            System.out.println(String.format(" start :%d-%c end:%d-%c",s,ar[s],e,ar[e]));
            if (e - s > size) {
                size = e - s;
                fs = s;
                fe = e;
            }

        }

//        for(int i=fs;i<=fe;i++) System.out.print(ar[i]);
//        System.out.println();
        return word.substring(fs, fe + 1);
    }

    //https://leetcode.com/problems/median-of-two-sorted-arrays/description/
    public static void findMedianSortedArrays() {
        System.out.println(findMedianSortedArrays(new int[]{1, 6, 8, 99, 1000}, new int[]{3, 8, 10}));
    }

    //this is O(m+n) but required is O(log(m+n))
    public static double findMedianSortedArrays(int[] a, int[] b) {
        int m, i = 0, j = 0;
        double curr = 0.0;
        int aSize = a.length, bSize = b.length;

        m = (aSize + bSize) / 2;
        double prev = 0;
        while (m >= 0) {
            prev = curr;
            if (i < aSize && j < bSize) {
                if (a[i] <= b[j]) curr = a[i++];
                else curr = b[j++];
            } else if (i == aSize && j < bSize) curr = b[j++];
            else if (j == bSize && i < aSize) curr = a[i++];
            m--;
        }

        if ((aSize + bSize) % 2 == 0)
            return (prev + curr) / 2;
        else return curr;
    }

    //https://leetcode.com/problems/reverse-integer/description/
    public static void convert() {
        System.out.println(convert("A", 1));
    }

    public static String convert(String s, int numRows) {
        if (numRows == 1) return s;
        int i = 0, n = numRows - 1, d1, d2, f = 0, curr, len = s.length();
        StringBuilder newS = new StringBuilder();

        curr = i;
        while (curr < len) {
            newS.append(s.charAt(curr));
            curr += 2 * n;
        }

        i++;
        while (i < n) {
            d1 = 2 * (n - i);
            d2 = 2 * i;
            curr = i;
            f = 0;
            while (curr < len) {
                f = f % 2;
                newS.append(s.charAt(curr));
                curr += f == 0 ? d1 : d2;
                f++;
            }
            i++;
        }

        curr = i;
        while (curr < len) {
            newS.append(s.charAt(curr));
            curr += 2 * n;
        }

        return newS.toString();
    }


    //https://leetcode.com/problems/reverse-integer/description/
    public static void reverse() {
        System.out.println(reverse(-123456));
    }

    public static int reverse(int x) {
        long f = 0, r;
        boolean isNeg = x < 0;
        if (isNeg) x = -x;
        while (x > 0) {
            r = x % 10;
            x = x / 10;
            f = 10 * f + r;
        }
        if (isNeg) f = -f;
        return f < Integer.MIN_VALUE || f > Integer.MAX_VALUE ? 0 : (int) f;
    }


    // https://leetcode.com/problems/regular-expression-matching/
    // there is a much faster dp solution
    public static void isMatch() {
        System.out.println(isMatch("aaabbccddeeff", "a*b*cc...*"));
    }

    public static boolean isMatch(String s, String p) {
        int slen=s.length(),plen=p.length();
        if (p.charAt(0) == '*') return false;
        else if(plen == 1) {
            if(slen>1) return false;
            return p.charAt(0) == s.charAt(0) || '.' == p.charAt(0);
        }
        return isMatch(s, p, 0, 0,slen,plen);
    }

    public static boolean isMatch(String s, String p, int i, int j,int slen, int plen) {
        if((i == slen && p.charAt(plen-1) != '*' ) || j == plen) {
            return i == slen && j == plen;
        }

        char curr = p.charAt(j);

        boolean matched = false;
        if(i<slen)
            matched = !('.' != curr && s.charAt(i) != curr);

        if(j<plen-1 && p.charAt(j+1)=='*')
            return isMatch(s,p,i,j+2,slen,plen) || (matched && isMatch(s,p,i+1,j,slen,plen));
        else {
            return matched && isMatch(s,p,i+1,j+1,slen,plen);
        }
    }

    //https://leetcode.com/problems/3sum/description/
    public static void threeSum() throws IOException {
        int n = Integer.parseInt(br.readLine());
        for(int i=0;i<n;i++){
            int[] tempInt = Arrays.stream(br.readLine().trim().split(",")).mapToInt(Integer::parseInt).toArray();
            List<List<Integer>> t = threeSum2(tempInt);
//            System.out.println(t);
            for(List<Integer> l : t){
                if(l.get(0)+l.get(1)+l.get(2)!=0)
                    System.out.println(l);
            }
        }
    }

    public static int swap(int a, int b) { return a; }
    public static List<List<Integer>> threeSum(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        Set<List<Integer>> set = new HashSet<>();
        Arrays.sort(nums);
        int a,b,c,k=0,len=nums.length,count_0=0,m;

        while(k<nums.length) {
            if (nums[k++]==0) count_0++;
        }
        if(count_0>3) {
            m=0;
            while(m<nums.length) {
                if(nums[m++]==0) break;
            }
            m++;
            k=m-3+count_0;
            while(k<nums.length) nums[m++]=nums[k++];
            len=nums.length-count_0+3;
        } else if (count_0==2) {
            m=0;
            while(m<nums.length) {
                if(nums[m++]==0) break;
            }
            k=m+1;
            while(k<nums.length) nums[m++]=nums[k++];
            len--;
        }

        for(int i=0;i<len;i++) {
            if(map.containsKey(nums[i])) map.put(nums[i],map.get(nums[i])+1);
            else map.put(nums[i],1);
        }

        for(int i=0;i<len;i++) {
            for(int j=0;j<len;j++) {
                if(i==j) continue;
                a = nums[i];
                b = nums[j];
                c = - (a+b);

                if (map.containsKey(c)) {
                    if ((c == a || c == b) && map.get(c) == 1) continue;
                    if(a>b) a=swap(b,b=a);
                    if(a>c) a=swap(c,c=a);
                    if(b>c) b=swap(c,c=b);
                    set.add(List.of(a,b,c));
                }

            }
        }
        return set.stream().toList();
    }

    //a cleaner but slower solution
    public static List<List<Integer>> threeSum2(int[] nums) {
        Arrays.sort(nums);
        int a,b,c;
        Set<List<Integer>> set = new HashSet<>();
        for(int i=0;i<nums.length;i++) {
            for(int j=i+1;j<nums.length;j++) {
                a = nums[i];
                b = nums[j];
                c = -(a+b);
                if(Arrays.binarySearch(nums,j+1,nums.length,c)>=0) {
                    set.add(List.of(a,b,c));
                }
            }
        }
        return set.stream().toList();
    }

    //https://leetcode.com/problems/3sum-closest/description/
    public static void sum3Closest() {
        int[] nums = Arrays.stream("0,0,0".split(",")).mapToInt(Integer::parseInt).toArray();
        int target = 1;
        System.out.println(threeSumClosest(nums,target));
    }

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        //System.out.println(Arrays.toString(nums));
        int smallestSum=Integer.MAX_VALUE,start,end,sum;
        for(int i=0;i<nums.length-2;i++) {
            start=i+1;
            end=nums.length-1;
           if(i>0 &&nums[i]==nums[i-1]) continue;
            while(start<end) {
                sum=nums[i]+nums[start]+nums[end];
                //System.out.println(String.format("%d %d %d = %d %d",nums[i],nums[start],nums[end],sum,target-sum));
                if(Math.abs(target-sum)<Math.abs(target-smallestSum)) smallestSum = sum;
                if(sum>target) end--;
                else start++;
            }
        }
        return smallestSum;
    }

    //https://leetcode.com/problems/letter-combinations-of-a-phone-number/
    public static void letterCombinations() {
        String digits = "9923242539";
        System.out.println(letterCombinations(digits));
    }

    public static List<String> letterCombinations(String digits) {
        char[][] nums = new char[10][];
        nums[2] = new char[]{'a','b','c'};
        nums[3] = new char[]{'d','e','f'};
        nums[4] = new char[]{'g','h','i'};
        nums[5] = new char[]{'j','k','l'};
        nums[6] = new char[]{'m','n','o'};
        nums[7] = new char[]{'p','q','r','s'};
        nums[8] = new char[]{'t','u','v'};
        nums[9] = new char[]{'w','x','y','z'};
        List<String> combinations = new ArrayList<>();
        if(digits.isEmpty()) return combinations;
        letterCombinations(digits,combinations,nums,"");
        return combinations;
    }

    public static void letterCombinations(String digits, List<String> combinations, char[][] nums, String combination) {
        if(digits.isEmpty()) combinations.add(combination);
        else {
            int num = Integer.parseInt(digits.substring(0,1));
            for(char c : nums[num]) letterCombinations(digits.substring(1),combinations,nums,combination.concat(String.valueOf(c)));
        }
    }

    //https://leetcode.com/problems/4sum/description/
    public static void fourSum() throws IOException {
        int n = Integer.parseInt(br.readLine());
        for(int i=0;i<n;i++) {
            String ar[] = br.readLine().trim().split(" ");
            int target = Integer.parseInt(ar[0]);
            int[] nums = Arrays.stream(ar[1].split(",")).mapToInt(Integer::parseInt).toArray();
            System.out.println(fourSum2(nums,target));
        }
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> set = new HashSet<>();
        for(int i=0;i<nums.length;i++) {
            for(int j=i+1;j<nums.length;j++) {
                for(int k=j+1;k<nums.length;k++) {
                    int a = nums[i];
                    int b = nums[j];
                    int c = nums[k];
                    long sum = (long)a + (long)b + (long)c;
                    if( sum > (long)Integer.MAX_VALUE || sum < (long)Integer.MIN_VALUE) continue;
                    int d = target - (a+b+c);
                    if(Arrays.binarySearch(nums,k+1,nums.length,d)>=0) set.add(List.of(a,b,c,d));
                }
            }
        }
        return set.stream().toList();
    }

    //way way faster two pointer solution
    public static List<List<Integer>> fourSum2(int[] nums, int target) {
        List<List<Integer>> list = new LinkedList<>();
        if(nums.length<4) return list;
        Arrays.sort(nums);
        int a,b,low,high,len=nums.length;
        for(int i=0;i<len-3;i++) {
            if(i>0 && nums[i]==nums[i-1]) continue;
            for(int j=i+1;j<len-2;j++) {
                if(j>i+1 && nums[j]==nums[j-1]) continue;
                a = nums[i];
                b = nums[j];
                long sum = (long) target - a - b;
                low = j+1;
                high = len-1;
                while(low<high) {
                    long twoSum = nums[low] + nums[high];
                    if(sum == twoSum) {
                        list.add(List.of(a,b,nums[low],nums[high]));
                        while(low<high && nums[low] == nums[low+1]) low++;
                        while(low<high && nums[high] == nums[high-1]) high--;
                        low++;
                        high--;
                    } else if(twoSum>sum) high--;
                    else low++;
                }
            }
        }

        return list;
    }

    //https://leetcode.com/problems/remove-nth-node-from-end-of-list/
    public static void removeNthFromEnd() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        ListNode current = head.next;
        current.next = new ListNode(3);
        current = current.next;
        current.next = new ListNode(4);
        current = current.next;
        current.next = new ListNode(5);

        head = removeNthFromEnd(head,2);
        while(head!=null){
            System.out.print(head.val+ " ");
            head=head.next;
        }
        System.out.println();
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        int len=0;
        ListNode current = head;
        while(current!=null) {
            current=current.next;
            len++;
        }
        // System.out.println(len);
        len=len-n;
        if(len==0){
            head=head.next;
            return head;
        }
        current = head;
        while(len>1){
            current = current.next;
            len--;
        }
        current.next = current.next.next;
        return head;
    }

    public static void printListNode(ListNode node) {
        while(node!=null){
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }

    //https://leetcode.com/problems/merge-k-sorted-lists/description/
    public static void mergeKLists() {
        String d;
        d = "[[1,4,5],[1,3,4],[2,6]]";
        String[] l = d.substring(2,d.length()-2).split("\\],\\[");
        System.out.println(Arrays.toString(l));
        ListNode[] lists = new ListNode[l.length];
        for(int i=0;i<l.length;i++) {
            int[] nums = Arrays.stream(l[i].split(",")).mapToInt(Integer::parseInt).toArray();
            ListNode curr=null;
            for(int j=0;j<nums.length;j++){
                if(j==0) {
                    lists[i] = new ListNode(nums[j]);
                    curr = lists[i];
                } else {
                    curr.next = new ListNode(nums[j]);
                    curr = curr.next;
                }
            }
            printListNode(lists[i]);
        }
//        printListNode(mergeKLists(lists));
        printListNode(mergeKLists(lists, 0, lists.length - 1));
    }

    // highly optimised nlogn solution
    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode head=null,curr=null;
        int endFlag=0,minVal,minInd=0;
        while(true) {
            minVal=Integer.MAX_VALUE; endFlag=0;
            for(int i=0;i<lists.length;i++) {
                if(lists[i]==null) {
                    endFlag++;
                    continue;
                }
                if(lists[i].val<minVal) {
                    minVal = lists[i].val;
                    minInd = i;
                }
            }

            if(endFlag==lists.length) break;

            if(head==null) {
                head=lists[minInd];
                lists[minInd] = lists[minInd].next;
                curr=head;
            } else {
                curr.next = lists[minInd];
                lists[minInd] = lists[minInd].next;
                curr = curr.next;
            }
        }

        return head;
    }

    public static ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if(lists.length == 0) return null;
        if(start==end) return lists[start];

        while(start<end) {
            int mid = start + (end-start)/2;
            ListNode start1 = mergeKLists(lists,start,mid);
            ListNode start2 = mergeKLists(lists,mid+1,end);
            return mergeLists(lists, start1, start2);
        }
        return lists[0];
    }

    public static ListNode mergeLists(ListNode[] lists, ListNode start1, ListNode start2) {
        ListNode head = new ListNode();
        ListNode curr = head;
        while(start1!=null && start2!=null) {
            if(start1.val<start2.val) {
                curr.next = start1;
                curr = curr.next;
                start1 = start1.next;
            } else {
                curr.next = start2;
                curr = curr.next;
                start2 = start2.next;
            }
        }

        while(start1!=null) {
            curr.next = start1;
            curr = curr.next;
            start1 = start1.next;
        }

        while(start2!=null){
            curr.next = start2;
            curr = curr.next;
            start2 = start2.next;
        }

        return head.next;
    }


}
