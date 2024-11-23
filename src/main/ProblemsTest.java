package main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.*;


public class ProblemsTest extends TestBase {


    //https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
    @ParameterizedTest
    @ValueSource(strings = {
            "8,3,4,5,7,7,8,8,10",
            "6,-1,-1,5,7,7,8,8,10",
            "0,-1,-1"
    })
    public void searchRange(String input) {
        int[] nums = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] arr = nums.length == 3 ? new int[0] : Arrays.stream(nums, 3, nums.length).toArray();
        int[] result = searchRange(arr, nums[0]);
        System.out.println(Arrays.toString(result));
        Assertions.assertEquals(nums[1], result[0]);
        Assertions.assertEquals(nums[2], result[1]);

    }

    public int[] searchRange(int[] nums, int target) {
        int l = -1, r = -1;
        if (nums.length == 0) return new int[]{l, r};

        int low = 0, high = nums.length - 1, mid;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                r = mid;
                low = mid + 1;
            } else if (target < nums[mid]) high = mid - 1;
            else low = mid + 1;
        }

        low = 0;
        high = nums.length - 1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (nums[mid] == target) {
                l = mid;
                high = mid - 1;
            } else if (target < nums[mid]) high = mid - 1;
            else low = mid + 1;
        }

        return new int[]{l, r};
    }

    //https://leetcode.com/problems/valid-sudoku/description/
    @ParameterizedTest
    @ValueSource(strings = {
            "true:[[\"5\",\"3\",\".\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\"6\",\".\",\".\",\"1\",\"9\",\"5\",\".\",\".\",\".\"],[\".\",\"9\",\"8\",\".\",\".\",\".\",\".\",\"6\",\".\"],[\"8\",\".\",\".\",\".\",\"6\",\".\",\".\",\".\",\"3\"],[\"4\",\".\",\".\",\"8\",\".\",\"3\",\".\",\".\",\"1\"],[\"7\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\",\"6\"],[\".\",\"6\",\".\",\".\",\".\",\".\",\"2\",\"8\",\".\"],[\".\",\".\",\".\",\"4\",\"1\",\"9\",\".\",\".\",\"5\"],[\".\",\".\",\".\",\".\",\"8\",\".\",\".\",\"7\",\"9\"]]",
            "false:[[\"8\",\"3\",\".\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\"6\",\".\",\".\",\"1\",\"9\",\"5\",\".\",\".\",\".\"],[\".\",\"9\",\"8\",\".\",\".\",\".\",\".\",\"6\",\".\"],[\"8\",\".\",\".\",\".\",\"6\",\".\",\".\",\".\",\"3\"],[\"4\",\".\",\".\",\"8\",\".\",\"3\",\".\",\".\",\"1\"],[\"7\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\",\"6\"],[\".\",\"6\",\".\",\".\",\".\",\".\",\"2\",\"8\",\".\"],[\".\",\".\",\".\",\"4\",\"1\",\"9\",\".\",\".\",\"5\"],[\".\",\".\",\".\",\".\",\"8\",\".\",\".\",\"7\",\"9\"]]",
            "false:[[\".\",\".\",\".\",\".\",\"5\",\".\",\".\",\"1\",\".\"],[\".\",\"4\",\".\",\"3\",\".\",\".\",\".\",\".\",\".\"],[\".\",\".\",\".\",\".\",\".\",\"3\",\".\",\".\",\"1\"],[\"8\",\".\",\".\",\".\",\".\",\".\",\".\",\"2\",\".\"],[\".\",\".\",\"2\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\".\",\"1\",\"5\",\".\",\".\",\".\",\".\",\".\",\".\"],[\".\",\".\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\"],[\".\",\"2\",\".\",\"9\",\".\",\".\",\".\",\".\",\".\"],[\".\",\".\",\"4\",\".\",\".\",\".\",\".\",\".\",\".\"]]",
    })
    public void isValidSudoku(String input) {
        Boolean expected = Boolean.parseBoolean(input.split(":")[0]);
        String data = input.split(":")[1];
        char board[][] = new char[9][9];
        String[] rowList = data.substring(2,data.length()-2).split("\\],\\[");
        String splitChar = "\",\"";
        for(int i=0;i<9;i++) {
            //System.out.println(Arrays.toString(rowList[i].substring(1, rowList[i].length() - 1).split(splitChar)));
            Character[] values = Arrays.stream(rowList[i].substring(1, rowList[i].length() - 1).split(splitChar)).map(s -> s.charAt(0)).toArray(Character[]::new);
            //System.out.println(Arrays.toString(values));
            for(int j=0;j<9;j++) board[i][j] = values[j];
        }
        //Arrays.stream(board).toList().forEach(row -> System.out.println(Arrays.toString(row)));
        Assertions.assertEquals(expected, isValidSudoku(board));
    }

    public boolean isValidSudoku(char[][] board) {
        List<Set<Character>> columnList = new ArrayList<>();
        for(int i=0;i<9;i++) columnList.add(new HashSet<Character>());

        Set<Character> block_1, block_2, block_3;
        block_1 = new HashSet<Character>();
        block_2 = new HashSet<Character>();
        block_3 = new HashSet<Character>();

        for(int i=0;i<9;i++) {
            Set<Character> rowSet = new HashSet<>();

            if(i==3 || i==6) {
                block_1 = new HashSet<Character>();
                block_2 = new HashSet<Character>();
                block_3 = new HashSet<Character>();
            }

            for(int j=0;j<9;j++) {
                if(board[i][j] == '.') continue;

                if(rowSet.contains(board[i][j])) return false;
                else rowSet.add(board[i][j]);

                if(columnList.get(j).contains(board[i][j])) return false;
                else columnList.get(j).add(board[i][j]);

                if(j<3) {
                    if (block_1.contains(board[i][j])) return false;
                    else block_1.add(board[i][j]);
                } else if(j<6) {
                    if (block_2.contains(board[i][j])) return false;
                    else block_2.add(board[i][j]);
                } else {
                    if (block_3.contains(board[i][j])) return false;
                    else block_3.add(board[i][j]);
                }
            }
        }

        return true;
    }

    //https://leetcode.com/problems/sudoku-solver/description/
    @ParameterizedTest
    @ValueSource(strings = {
            "[[\"5\",\"3\",\".\",\".\",\"7\",\".\",\".\",\".\",\".\"],[\"6\",\".\",\".\",\"1\",\"9\",\"5\",\".\",\".\",\".\"],[\".\",\"9\",\"8\",\".\",\".\",\".\",\".\",\"6\",\".\"],[\"8\",\".\",\".\",\".\",\"6\",\".\",\".\",\".\",\"3\"],[\"4\",\".\",\".\",\"8\",\".\",\"3\",\".\",\".\",\"1\"],[\"7\",\".\",\".\",\".\",\"2\",\".\",\".\",\".\",\"6\"],[\".\",\"6\",\".\",\".\",\".\",\".\",\"2\",\"8\",\".\"],[\".\",\".\",\".\",\"4\",\"1\",\"9\",\".\",\".\",\"5\"],[\".\",\".\",\".\",\".\",\"8\",\".\",\".\",\"7\",\"9\"]]",
    })
    public void solveSudoku_(String data) {
        char board[][] = new char[9][9];
        String[] rowList = data.substring(2,data.length()-2).split("\\],\\[");
        String splitChar = "\",\"";
        for(int i=0;i<9;i++) {
            //System.out.println(Arrays.toString(rowList[i].substring(1, rowList[i].length() - 1).split(splitChar)));
            Character[] values = Arrays.stream(rowList[i].substring(1, rowList[i].length() - 1).split(splitChar)).map(s -> s.charAt(0)).toArray(Character[]::new);
            //System.out.println(Arrays.toString(values));
            for(int j=0;j<9;j++) board[i][j] = values[j];
        }
//        Arrays.stream(board).toList().forEach(row -> System.out.println(Arrays.toString(row)));
        solveSudoku3(board);
        Assertions.assertEquals(true,isValidSudoku(board));
    }

    int charToInt(char c) {
        return (int)c - (int)'0';
    }

    char intToChar(int i){
        return (char)(i + (int)'0');
    }

    // around 20 ms in leetcode
    private boolean solveSudoku2(char[][] board){
        List<Set<Character>> row_set = new ArrayList<>();
        List<Set<Character>> col_set = new ArrayList<>();
        List<List<Set<Character>>> block_set = new ArrayList<>();

        for(int i=0;i<9;i++) {
            row_set.add(new HashSet<Character>());
            col_set.add(new HashSet<Character>());
        }

        for(int i=0;i<3;i++) {
            block_set.add(new ArrayList<>());
            for(int j=0;j<3;j++) {
                block_set.get(i).add(new HashSet<Character>());
            }
        }

        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if(board[i][j]!='.') {
                    row_set.get(i).add(board[i][j]);
                    col_set.get(j).add(board[i][j]);
                    block_set.get(i/3).get(j/3).add(board[i][j]);
                }
            }
        }
        return solveSudoku2(board,0,0, row_set, col_set, block_set);
    }


    public boolean solveSudoku2(char[][] board, int i, int j,
                                List<Set<Character>> row_set,
                                List<Set<Character>> col_set,
                                List<List<Set<Character>>> block_set) {
        if(i==9) {
            Arrays.stream(board).toList().forEach(row -> println(Arrays.toString(row)));
            return true;
        } else if (board[i][j]!='.') {
            if(j==8) {
                i+=1;j=0;
            } else j+=1;
            return solveSudoku2(board,i,j,row_set,col_set,block_set);
        } else {
            List<Character> possible_vals = new ArrayList<>();

            for(int k=1; k<=9; k++) {
                char c = Character.forDigit(k,10);

                if(row_set.get(i).contains(c) || col_set.get(j).contains(c) || block_set.get(i/3).get(j/3).contains(c))
                    continue;

                possible_vals.add(c);
            }

            if(possible_vals.size()==0) return false;

            int next_i, next_j;
            if(j==8) {
                next_i=i+1;
                next_j=0;
            } else {
                next_i=i;
                next_j=j+1;
            }

            for(int k=0;k<possible_vals.size();k++) {
                row_set.get(i).add(possible_vals.get(k));
                col_set.get(j).add(possible_vals.get(k));
                block_set.get(i/3).get(j/3).add(possible_vals.get(k));

                board[i][j] = possible_vals.get(k);

                if(!solveSudoku2(board,next_i,next_j,row_set,col_set,block_set)){
                    row_set.get(i).remove(board[i][j]);
                    col_set.get(j).remove(board[i][j]);
                    block_set.get(i/3).get(j/3).remove(board[i][j]);
                    board[i][j]='.';
                } else return true;
            }
            return false;
        }
    }


    // same approach without using collections 3ms and beats 95% and also used less memory
    // the least time solution is using bit operation
    private boolean solveSudoku3(char[][] board){

        boolean[][] row_set = new boolean[9][9];
        boolean[][] col_set = new boolean[9][9];
        boolean[][][] block_set = new boolean[3][3][9];

        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if(board[i][j]!='.') {
                    row_set[i][charToInt(board[i][j])-1] = true;
                    col_set[j][charToInt(board[i][j])-1] = true;
                    block_set[i/3][j/3][charToInt(board[i][j])-1] = true;
                }
            }
        }
        return solveSudoku3(board,0,0, row_set, col_set, block_set);
    }

    public boolean solveSudoku3(char[][] board, int i, int j, boolean[][] row_set, boolean[][] col_set,
                                boolean[][][] block_set) {
        if (i == 9) {
            Arrays.stream(board).toList().forEach(row -> println(Arrays.toString(row)));
            return true;
        } else if (board[i][j] != '.') {
            if (j == 8) {
                i += 1;
                j = 0;
            } else
                j += 1;
            return solveSudoku3(board, i, j, row_set, col_set, block_set);
        } else {
            int next_i, next_j;
            if (j == 8) {
                next_i = i + 1;
                next_j = 0;
            } else {
                next_i = i;
                next_j = j + 1;
            }

            for (int k = 1; k <= 9; k++) {
                if (row_set[i][k - 1] == true || col_set[j][k - 1] == true || block_set[i / 3][j / 3][k - 1] == true)
                    continue;
                row_set[i][k - 1] = true;
                col_set[j][k - 1] = true;
                block_set[i / 3][j / 3][k - 1] = true;

                board[i][j] = intToChar(k);

                if (!solveSudoku3(board, next_i, next_j, row_set, col_set, block_set)) {
                    row_set[i][k - 1] = false;
                    col_set[j][k - 1] = false;
                    block_set[i / 3][j / 3][k - 1] = false;
                    board[i][j]='.';
                } else
                    return true;
            }
            return false;
        }
    }


    //https://leetcode.com/problems/count-and-say/
    @ParameterizedTest
    @ValueSource(ints = {30})
    void countAndSay(int n) {
        String s = "1";
        StringBuffer temp = new StringBuffer();
        int count;
        char c;
        for (int i = 2; i <= n; i++) {
            count = 1;
            c = s.charAt(0);
            for (int k = 1; k < s.length(); k++) {
                if (c == s.charAt(k))
                    count++;
                else {
                    temp.append(count);
                    temp.append(c);
                    c = s.charAt(k);
                    count = 1;
                }
            }
            temp.append(count);
            temp.append(c);
            s = temp.toString();
            temp = new StringBuffer();
            System.out.println(s);
        }
        //return s;
    }

    //https://leetcode.com/problems/combination-sum/description/
    @ParameterizedTest
    @ValueSource(strings = {"7:[2,3,6,7]", "8:[2,3,5]", "1:[2]", "11:[8,7,4,3]",
    "19:[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]"})
    void combinationSum(String input) {
        int target = Integer.parseInt(input.split(":")[0]);
        String temp = input.split(":")[1];
        temp = temp.substring(1, temp.length() - 1);
        Integer[] candidatesTemp = Arrays.stream(temp.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
        int[] candidates = new int[candidatesTemp.length];
        for (int i = 0; i < candidates.length; i++)
            candidates[i] = candidatesTemp[i];
        System.out.println(combinationSum(candidates, target));
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> finalList = new ArrayList<>();
        combinationSum(candidates,0,path, target, finalList);

        return finalList;
    }

    void combinationSum(int[] ar, int index, List path, int target, List finalList) {
        if(target == 0) {
            finalList.add(new ArrayList<>(path));
            return;
        }

        if(target<0) return;

        if(index == ar.length) return;

        path.addLast(ar[index]);
        combinationSum(ar, index, path, target - ar[index], finalList);
        path.remove(path.size()-1);
        combinationSum(ar,index+1,path, target, finalList);
    }


    //https://leetcode.com/problems/combination-sum-ii/description/
    @ParameterizedTest
    @ValueSource(strings = {"8:[10,1,2,7,6,1,5]", "5:[2,5,2,1,2]",
            "30:[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]"})
    void combinationSum2(String input) {
        int target = Integer.parseInt(input.split(":")[0]);
        String temp = input.split(":")[1];
        String[] num = temp.substring(1, temp.length() - 1).split(",");
        int[] candidates = new int[num.length];
        for (int i = 0; i < num.length; i++) candidates[i] = Integer.parseInt(num[i]);
        //System.out.println(combinationSum2(candidates, target));
        println(combinationSum2Repeating(candidates, target));
    }

    //This works great with unique numbers in the list, repeating numbers wastes time
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        Set<List<Integer>> finalList = new HashSet<>();
        for (int i = 0; i < candidates.length; i++) {
            List<Integer> nums = new ArrayList<>();
            nums.addLast(candidates[i]);
            combinationSum2(i, target - candidates[i], candidates, nums, finalList);
        }

        return finalList.stream().toList();
    }

    public void combinationSum2(int k, int target, int[] candidates, List<Integer> nums, Set<List<Integer>> finalList) {
        if (target == 0) {
            finalList.add(new ArrayList<>(nums));
        } else if (target > 0) {
            for (int i = k + 1; i < candidates.length; i++) {
                nums.addLast(candidates[i]);
                combinationSum2(i, target - candidates[i], candidates, nums, finalList);
                nums.removeLast();
            }
        }
    }

    public List<List<Integer>> combinationSum2Repeating(int[] candidateList, int target) {
        List<List<Integer>> finalList = new ArrayList<>();
        Map<Integer,Integer> countMap = new HashMap<>();
        Map<Integer,Integer> trackerMap = new HashMap<>();

        for(int i : candidateList) {
            if(countMap.containsKey(i)) countMap.put(i, countMap.get(i)+1);
            else {
                countMap.put(i,1);
                trackerMap.put(i,0);
            }

        }

        int[] candidate = new int[countMap.size()];
        int[] candidateFreq = new int[countMap.size()];

        int k = 0;
        for(Map.Entry<Integer,Integer> e : countMap.entrySet()) {
            candidate[k] = e.getKey();
            candidateFreq[k] = e.getValue();
            k++;
        }

        for(int i=0;i<candidate.length;i++) {
            trackerMap.put(candidate[i],trackerMap.get(candidate[i])+1);
            candidateFreq[i]--;
            combinationSum2Repeating(i,target - candidate[i], trackerMap, candidate, candidateFreq, finalList);
            candidateFreq[i]++;
            trackerMap.put(candidate[i],trackerMap.get(candidate[i])-1);
        }

        return finalList;
    }

    private void combinationSum2Repeating(int i, int target, Map<Integer, Integer> trackerMap, int[] candidate, int[] candidateFreq, List<List<Integer>> finalList) {
        if(target>0) {
            if(candidateFreq[i]>0) {
                for(int j=i;j<candidate.length;j++) {
                    trackerMap.put(candidate[j],trackerMap.get(candidate[j])+1);
                    candidateFreq[j]--;
                    combinationSum2Repeating(j,target - candidate[j], trackerMap, candidate, candidateFreq, finalList);
                    candidateFreq[j]++;
                    trackerMap.put(candidate[j],trackerMap.get(candidate[j])-1);
                }
            } else {
                for(int j=i+1;j<candidate.length;j++) {
                    trackerMap.put(candidate[j],trackerMap.get(candidate[j])+1);
                    candidateFreq[j]--;
                    combinationSum2Repeating(j,target - candidate[j], trackerMap, candidate, candidateFreq, finalList);
                    candidateFreq[j]++;
                    trackerMap.put(candidate[j],trackerMap.get(candidate[j])-1);
                }
            }
        } else if(target == 0) {
            List<Integer> list = new ArrayList<>();
            for (Map.Entry<Integer, Integer> e: trackerMap.entrySet()) {
                for(int k=0;k<e.getValue();k++) {
                    list.add(e.getKey());
                }
            }
            finalList.add(list);
        }
    }

    public void solve(int[] candidates, int target,int idx, List<Integer> temp, List<List<Integer>> res, int sum) {
        if (idx > candidates.length || sum > target) {
            return;
        }
        if(sum == target) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int j = idx; j < candidates.length; j++) {
            if (j > idx && candidates[j] == candidates[j-1]) continue;
            temp.add(candidates[j]);
            solve(candidates, target, j+1, temp, res, sum+candidates[j]);
            temp.remove(temp.size()-1);
        }

        return;
    }

    //much more elegant solution
    public List<List<Integer>> combinationSum2FROMleetcode(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        solve(candidates, target, 0, new ArrayList<>(), res, 0);
        return res;
    }

}
