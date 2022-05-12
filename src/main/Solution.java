package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import structures.ListNode;
import structures.MyQueue;
import structures.node;
import structures.pair;

public class Solution {
    // public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedReader br;
    public static BufferedWriter bw;
    
    // ~~~~~~~~~~~~~ MAIN METHOD ~~~~~~~~~~~~~
    public static void main(String[] args) {
        try{
        	System.out.println(System.getProperty("user.dir"));
            br = new BufferedReader(new FileReader("src/resources/data.txt"));
            bw = new BufferedWriter(new FileWriter("src/resources/result.txt"));
            generateParenthesis();
            br.close();
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }      
    }

    // problem_statements/search/ctci-ice-cream-parlor-English.pdf
    public static void whatFlavorsInput() throws IOException{
        int t = Integer.parseInt(br.readLine().trim());
        for(int i=0; i<t ;i++){
            int money = Integer.parseInt(br.readLine().trim());
            int size = Integer.parseInt(br.readLine().trim());
            List<Integer> cost = Stream.of(br.readLine().trim().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            whatFlavors(cost,money);
        }
    }
    public static void whatFlavors(List<Integer> cost,int money){
        Map<Integer,Integer> map = new HashMap<>();
        int size = cost.size();
        for(int i=0;i<size;i++){
            int c = cost.get(i);
            if(!map.containsKey(money - c))
                map.put(c, i);
            else{
                if(c < map.get(money-c))
                    { System.out.println(i+1 + " "+ (map.get(money-c)+1));break; }
                else
                    { System.out.println(map.get(money - c)+1 + " " + (i+1));break; }
            }
        }
    }

    // problem_statements/search/swap-nodes-algo-English.pdf  
    public static void swapNodesInput() throws IOException{
        int n = Integer.parseInt(br.readLine().trim());
        List<List<Integer>> indexes = new ArrayList<>();
        for(int i=0 ;i < n ;i++)
            indexes.add(Stream.of(br.readLine().replaceAll("\\s+$", "").split(" ") ).map(Integer::parseInt).collect(Collectors.toList()));
    

        n = Integer.parseInt(br.readLine().trim());
        List<Integer> queries = new ArrayList<>();
        for(int i=0; i<n ;i++)
            queries.add(Integer.parseInt(br.readLine().trim()));
        
        swapNodes(indexes, queries).forEach(l->System.out.println(l.toString()));
    }
    public static void inorder(node n,List<Integer> list){
        if(n==null)
            return;
        if(n.left!=null)
            inorder(n.left,list);

        // System.out.print(n.value+" ");
        list.add(n.value);
        
        if(n.right!=null)
            inorder(n.right,list);
    }
    public static void depth_calc(node n,ArrayList<Integer> depth[],int index){
        depth[index].add(n.value);
        if(n.left!=null)
            depth_calc(n.left, depth, index+1);
        if(n.right!=null)
            depth_calc(n.right, depth, index+1);

    } 
    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries){
        List<List<Integer>> resultList = new ArrayList<>();
        int n = indexes.size();
        node tree[] = new node[n+1];
        ArrayList<Integer> depths[] = new ArrayList[n+1];

        //initialising the tree(made of nodes) and the depth(list of nodes at depth i) array
        for(int i=1;i<=n;i++){
            tree[i] = new node(i);
            depths[i] = new ArrayList<>();
        }

        //setting the left and right child of nodes from the given input
        for(int i=1;i<=n;i++){
            int left = indexes.get(i-1).get(0);
            int right = indexes.get(i-1).get(1);
            if(right!=-1)
                tree[i].right = tree[right];
            if(left!=-1)
                tree[i].left = tree[left];
        }

        //calculating node depth and storing them in depths array
        depth_calc(tree[1], depths, 1);

        for(Integer query : queries){
            int depth = query;
            while(depth<=n){
                //swap nodes at the given depth
                for(Integer index: depths[depth]){
                    node temp = tree[index].left;
                    tree[index].left = tree[index].right;
                    tree[index].right = temp;
                }
                depth+=query;   //have to swap for multiples of the given depth 
            }
            List<Integer> list = new ArrayList<>();
            inorder(tree[1],list);  //DFS on the tree
            // System.out.println(list.toString());
            resultList.add(list);
        }
        return resultList;
    }

    // problem_statements/search/pairs-English.pdf
    public static void pairsInput() throws IOException{
        String line1[] = br.readLine().trim().split(" ");
        int k = Integer.parseInt(line1[1]);
        List<Integer> arr = Arrays.asList(br.readLine().trim().split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());

        System.out.println(pairs(k,arr));
    }
    public static int pairs(int k,List<Integer> arr){
        HashSet<Integer> set = new HashSet<>();
        arr.forEach( num -> set.add(num));
        //using a array since you cannot access variables from inside 
        //lambda functions unless it is final
        //since it is an array it is passing an reference
        int count[] = { 0 }; 
        arr.forEach(num -> {
            if(set.contains(k+num))
                count[0]++;
        });

        return count[0];
    }

    // problem_statements/search/triple-sum-English.pdf
    public static void tripletsInput() throws IOException{
        br.readLine();
        
        String as[] = br.readLine().trim().split(" ");
        String bs[] = br.readLine().trim().split(" ");
        String cs[] = br.readLine().trim().split(" ");

        int a[] = new int[as.length];
        int b[] = new int[bs.length]; 
        int c[] = new int[cs.length];

        for(int i=0;i<as.length;i++)
            a[i]=Integer.parseInt(as[i]);
         
        for(int i=0;i<bs.length;i++)
            b[i]=Integer.parseInt(bs[i]);
        
        for(int i=0;i<cs.length;i++)
            c[i]=Integer.parseInt(cs[i]);
        
        System.out.println();
        System.out.println(triplets(a,b,c));
    }    
    public static long triplets(int a[], int b[], int c[]){
        
        long count =0;
        
        // remove duplicate elements
        int[] ar = Arrays.stream(a).sorted().distinct().toArray();
        int[] br = Arrays.stream(b).sorted().distinct().toArray();
        int[] cr = Arrays.stream(c).sorted().distinct().toArray();

        int bi = 0;
        int ai = 0;
        int ci = 0;

        while(bi < br.length){
            while(ai<ar.length && ar[ai]<=br[bi])
                ai++; //number of elements smaller than the element in b
            while(ci<cr.length && cr[ci]<=br[bi])
                ci++; // same as above
            count+= (long)ai*ci; // this gives all the permutations 
            // since ai and ci are integers, there can be a overflow
            // if it is not casted to long
            bi++;
        }

        return count;
    }

    // problem_statements/stacksAndQueues/balanced-brackets-English.pdf
    public static void balancedBracketsInput() throws NumberFormatException, IOException{
        int n = Integer.parseInt(br.readLine().trim());

        for(int i=0;i<n;i++){
            String str = br.readLine().trim();
            // System.out.println(balancedBrackets(str));
            // bw.write(balancedBrackets(str));
            bw.write(elegantBalancedBrackets(str));
            bw.newLine();
        }
    }
    public static String balancedBrackets(String str){
        int flag = 1;
        LinkedList<Character> stack = new LinkedList<>();

        for(int i=0;i<str.length() && flag==1;i++){
            char c = str.charAt(i);
            switch(c){
                case '[': { stack.add(c);break; }
                case '{': { stack.add(c);break; }
                case '(': { stack.add(c);break; }
                case ')': { 
                        if(stack.isEmpty()) flag=0;
                        if( !stack.isEmpty() && stack.removeLast()!='(') flag=0;
                        break;
                    }
                case '}': { 
                    if(stack.isEmpty()) flag=0;
                    if(!stack.isEmpty() && stack.removeLast()!='{') flag=0;
                    break;
                 }
                case ']': {
                    if(stack.isEmpty()) flag=0;  
                    if(!stack.isEmpty() && stack.removeLast()!='[') flag=0;
                    break;
                }
            }
            // System.out.println(stack.toString());
        }
        
        return flag == 1 && stack.isEmpty() ? "YES" : "NO";
    }    
    public static String elegantBalancedBrackets(String str)
    {
        //credit : parasou79 - hackerrank
        int n = -1;
        while(str.length()!=n){
            n=str.length();
            str=str.replace("()", "");
            str=str.replace("{}", "");
            str=str.replace("[]", "");
        }
        return str.length() == 0 ? "YES" : "NO";
    }

    // problem_statements/stacksAndQueues/ctci-queue-using-two-stacks-English.pdf
    public static void queueInput() throws NumberFormatException, IOException{
        int k = Integer.parseInt(br.readLine().trim());
        MyQueue<Integer> q = new MyQueue<>();

        for(int i=0;i<k;i++){
            //int n = br.read();
            String input[] = br.readLine().trim().split(" ");
            int n = Integer.parseInt(input[0]);
            if(n==1)
                q.enqueue(Integer.parseInt(input[1]));
            else  if(n==2)
                q.dequeue();
            else{
                // System.out.println(q.peek());
                bw.write(q.peek().toString());
                bw.newLine();
            }
        }
    }    

    // problem_statements/stacksAndQueues/largest-rectangle.pdf
    public static void largestRectangleInput() throws NumberFormatException, IOException{
        Integer.parseInt(br.readLine().trim());
        List<Integer> h = Arrays.asList(br.readLine().trim().split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());
        bw.write(largestRectangle(h)+" ");
    }
    public static long largestRectangle(List<Integer> h){
        int area = 0 ;    
        // add 0 to the end of the list so that the stack will be emptied in the end
        h.add(0);

        // this stack will store the indexes of elements 
        List<Integer> stack = new ArrayList<Integer>();

        // start with pushing the first element into the stack
        //push - stack.add(0,val)
        //pop - stack.remove(0)
        //peek - stack.get(0)
        int i =0;

        while(i<h.size()){
            // if number from array is greater than top element push to the stack
            if( stack.isEmpty() || h.get(i) > h.get(stack.get(0))  ){
                stack.add(0,i);
                i++;
            }else{
                // if the number from the array is smaller than the top element then
                //pop from the stack and then find the area
                int height_i = stack.remove(0); //pop 
                int areaT =0 ;
                if(!stack.isEmpty()){
                    int left=stack.get(0);  //peek
                    int right=i;
                    int width= right-left-1;
                    areaT = width*h.get(height_i) ;// width * popped element
                }else{
                    // no smaller element is there on the left of the right element
                    int width = i;
                    areaT = width*h.get(height_i) ;
                }
                if(area<areaT) area=areaT;
            }
        }
        
        return area;
    }

    // Given an array of integers heights representing the histogram's bar height where the width 
    // of each bar is 1, return the area of the largest rectangle in the histogram.
    // https://leetcode.com/problems/largest-rectangle-in-histogram/
    public static int max(int a,int b){
        return a>b?a:b;
    }
    public static int largestRectangleArea(int[] heights) {
        List<Integer> stack = new ArrayList<>();
        int sum=0,tval;
        for(int i=0;i<=heights.length;i++){
            // i==heights.length is for the last case when you have to empty the stack
            // and compare the area after each pop
            while(!stack.isEmpty() && (i==heights.length || heights[i] < heights[stack.get(0)])){
                tval = heights[stack.remove(0)];
                sum = max(sum,tval * (stack.isEmpty() ? i : i-stack.get(0)-1));
            }
            stack.add(0,i);
        }
        return sum;
    }

    // online algo - can be improved using tree - binary search is used for finding 
    // element then insertion is performed - somewhat optimised insertion sort
    // https://www.geeksforgeeks.org/median-of-stream-of-integers-running-integers/
    public static void medianStreamIntegers(){
        // this is supposed to represent incoming stream of integers
        int ar[] = {4,6,1,2,9,10,7,8,1,0,9}; 
        List<Integer> l= new ArrayList<>();
        for(int i: ar) l.add(i);
        System.out.println(l);
      
        List<Integer> a = new ArrayList<>();
        for(Integer i : l){
          if(a.size()==0) a.add(i);
          else {
            int loc = ceil(a,i);
            // System.out.println(loc);
            if(loc == -1) a.add(i);
            else a.add(loc, i);
          }
          System.out.println(a);
        } 
    }
    public static int ceil(List<Integer> ar,int key){
        if(key <= ar.get(0)) return 0;
        if(key>ar.get(ar.size()-1)) return -1;
        return ceil(ar,0,ar.size()-1, key);
      }
    public static int ceil(List<Integer> ar, int l, int r, int key){
        int m;
        while( r - l > 1 ){
            m = l + (r - l)/2;
            if( ar.get(m) >= key ) r = m;
            else l = m;
        }
        return r;
    } 
      
    //You are climbing a staircase. It takes n steps to reach the top.
    //Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
    public static void countStairsInput(){
        int n = 8;
        int output = countStairs(n);
        System.out.println(output);
    }
    public static int countStairs(int n){
        if(n==0) return 1;
        if(n==1) return 1;
        
        int i=2;
        int Ci=0,CiMinus1=1,CiMinus2=1;
        while(i<=n){
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
    public static void maxProfitInput(){
        int[] n={7,1,5,3,6,4};
        int profit = maxProfit(n);
        System.out.println(profit);
    }
    public static int maxProfit(int[] prices){
        int min = Integer.MAX_VALUE;
        int profit = 0;

        for(int price: prices){
            if(price < min) min = price;
            else if ( (price - min) > profit ) 
                profit = price - min;
        }

        return profit;
    }

    // https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/dynamic-programming/566/
    // Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
    // A subarray is a contiguous part of an array.
    public static void maxContSum(){
        int n[] = {-2,1,-3,4,-1,2,1,-5,4};
        int sum = maxContSumEfficient(n);
        System.out.println(sum);
    }
    public static int maxContSum(int nums[]){
        int max=Integer.MIN_VALUE,temp=0;
        for(int i=0;i<nums.length;i++){
            temp=0;
            for(int j=i;j<nums.length;j++){
                //temp = sumOfSubset(i,j)
                temp+=nums[j];
                if(temp>max) max=temp;
            }
        }
        return max;
    }
    public static int maxContSumEfficient(int nums[]){
        int max = nums[0];
        int temp = nums[0];

        for(int num: nums){
            //temp is the sum of the current subset
            temp = Math.max(num, temp+num);
            max = Math.max(max, temp);
        }

        return max;
    }


    //https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/dynamic-programming/576/
    // You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint 
    // stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police 
    // if two adjacent houses were broken into on the same night.Given an integer array nums representing the amount of money of each house, return 
    // the maximum amount of money you can rob tonight without alerting the police.
    public static void houseRobber(){
        int n[] = {2,7,9,3,1};
        int max = houseRobber(n);
        System.out.println(max);
    }
    public static int houseRobber(int nums[]){
        int _1=nums[0];
        if(nums.length==1) return _1;
        
        int _2=Math.max(nums[0], nums[1]);
        if(nums.length==2) return _2;
        
        int _3=0;
        
        // we mainly have to consider 3 elements ie _1,_2,_3
        // f(n) = max(f(n-1),f(n-2)+n)
        // f(0) = n0
        // f(1) = max(n0,n1)
        // f(2) = max(f(1), f(0) + n2)
        for(int i = 2 ; i<nums.length;i++){
            
            _3 = Math.max(_2, nums[i]+_1);
            _1=_2;
            _2=_3;
        }
        
        return _3;
    }

    //https://leetcode.com/problems/is-subsequence/submissions/
    public static void isSubsequence() throws IOException{
        String s = br.readLine();
        String t = br.readLine();
        bw.write(isSubsequence(s,t) ? "true" : "false");
    }
    public static boolean isSubsequence(String s,String t){
        int k=0;
        for(int i=0;i<t.length();i++){
            if(k==s.length()) break;
            if(s.charAt(k)==t.charAt(i)) k++;
        }
        return k==s.length()? true:false;
    }

    //https://leetcode.com/problems/count-sorted-vowel-strings
    //https://leetcode.com/problems/count-sorted-vowel-strings/discuss/1459936/100-Faster 
    // see the pattern and you will find dp[j] = dp[j - 1] + dp[j];
    //CompetativeProgrammingPractice\src\problem_statements\images
    public static int kc=0;
    public static void countVowelString(){
        String vowels[] = "aeiou".split("");
        System.out.println(Arrays.toString(vowels));
        countVowelString(568,"","aeiou",0);
        System.out.println(kc);
    }
    public static void countVowelString(int n,String a,String b,int k){ 
        if(k==n) { 
        	kc++;
        	System.out.println(a); 
        	return; 
        }
        for(int i=0;i<b.length();i++){
            String aN = a+b.charAt(i);
            String bN=b.substring(i, b.length());
            countVowelString(n,aN,bN,k+1);
        }
        
    }


    //https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
    public static void removeDuplicates() throws NumberFormatException, IOException{
        String s,result;
        int k;

        int n = Integer.parseInt(br.readLine().trim());
        for(int i=0;i<n;i++){
            s = br.readLine().trim();
            s = s.substring(1,s.length()-1);
            k = Integer.parseInt(br.readLine().trim());

            result = removeDuplicates(s, k);
            bw.write(result);
            bw.write("\n");
        }
    }    
    public static String removeDuplicatesUnoptimised(String s,int k){
        String tempString = "";
        // int startIndex = 0;
        boolean flag = true;

        int l=0;
        int r=0;


        while (flag){
            flag = false;
            l = 0;
            r = 0;

            while(r<s.length()){
                //check if l and r diff = k
                if(r-l == k){
                    l=r;
                    flag=true;
                }

                //if l != r then move l to r and add to string 
                if(s.charAt(l)!=s.charAt(r)){
                    tempString+=s.substring(l, r);
                    l=r;
                }

                r++;
            }

            if(l<r && (r-l!=k) )
                tempString+=s.substring(l, r);

            s = tempString;
            tempString = "";
            System.out.println(s);
        }
        
        return s;
    }
    public static String removeDuplicates(String s,int k){
        Stack<pair> stack = new Stack<>();
        
        //use a stack to push characters and if the top k items are same then start popping
        //store data in [char,count] way in stack to make the popping operation easier 
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(!stack.isEmpty() && stack.peek().ch == c )
                stack.peek().count += 1;
            else
                stack.push(new pair(c,1));
            while(!stack.isEmpty() && stack.peek().count == k)
                stack.pop();
        }

        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            pair p = stack.pop();
            for(int j=0;j<p.count;j++){
                sb.append(p.ch);
            }
        }
        return sb.reverse().toString();
    }

    
    //https://leetcode.com/problems/add-two-numbers/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int res=0;
        boolean carryOver=false;
        ListNode head = new ListNode();
        ListNode currentNode = head;
        while(l1 !=null || l2 != null || carryOver){
            res=0;
            if(carryOver) res = 1;
            
            if(l1 != null && l2 != null){
                res += l1.val + l2.val;
                if(res>9) carryOver = true;
                else carryOver = false;
                res = res % 10;
                
                l1 = l1.next;
                l2 = l2.next;
            }else if(l1 == null && l2 == null){
                res = 1;
                carryOver = false;
            }else if(l1 == null ){
                res += l2.val;
                if(res>9) carryOver = true;
                else carryOver = false;
                res = res % 10;
                l2 = l2.next;
            }
            else{
                res += l1.val;
                if(res>9) carryOver = true;
                else carryOver = false;
                res = res % 10;
                l1 = l1.next;
            }
            
           
            
            if(currentNode == head){
                head.val = res;
                head.next = new ListNode(res);
                currentNode = head.next;
            }
            else{
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
    	 int carry = 0 ;
    	 
    	 
    	 while(l1!=null || l2!=null || carry==1)
    	 {
    	     int sum=0;
    	     
    	     if(l1!=null)
    	     {
    	         sum+=l1.val;
    	         l1=l1.next;
    	     }
    	     
    	     if(l2!=null)
    	     {
    	         sum+=l2.val;
    	         l2=l2.next;
    	     }
    	     
    	     
    	     sum+=carry;
    	     carry = sum/10;
    	     ListNode node = new ListNode(sum%10);
    	     temp.next=node;
    	     temp=temp.next;
    	 }

    	return dummy.next;

    }
    
    
    //https://leetcode.com/problems/generate-parentheses/submissions/
    public static void generateParenthesis() {
    	int n=16;
    	ArrayList<String> list = new ArrayList<>();
    	generateParenthesis("",n,n,list);
    	System.out.println(list.size());
    	
    }
    public static void generateParenthesis(String str,int l, int r, ArrayList<String> list) {
    	//if count ( and ) = 0 return string
    	if(l==0 && r==0) {
    		//System.out.println(str);
    		list.add(str);
    		return;
    	} 
    	
    	// count ( = 0  then just add ) and reduce count of ) 
    	else if (l==0) {
    		generateParenthesis(str+")",l,r-1,list);
    	}
    	//if count of ( and ) is greater than 0 then
    	//recurse by one adding ( or one adding ) and reducing count
    	// count (  < count )
    	else {
    		generateParenthesis(str+"(", l-1, r,list);
    		if(l<r)
    			generateParenthesis(str+")", l, r-1,list);
    	}
    }
    
    
}
