package com.CK;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}

// Sort
class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0)
            return 0;
        Arrays.sort(nums);
        int local = 1, global = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1])
                continue;

            if (nums[i] == nums[i - 1] + 1) {
                local++;
            } else {
                local = 1;
            }
            global = Math.max(global, local);
        }
        return global;
    }
}

// HashSet
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num-1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum+1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }
}

// Union Find
class Solution {
    public int longestConsecutive(int[] nums) {
        UF uf = new UF(nums.length);
        Map<Integer,Integer> map = new HashMap<Integer,Integer>(); // <value,index>
        for(int i=0; i<nums.length; i++){
            if(map.containsKey(nums[i])){
                continue;
            }
            map.put(nums[i],i);
            if(map.containsKey(nums[i]+1)){
                uf.union(i,map.get(nums[i]+1));
            }
            if(map.containsKey(nums[i]-1)){
                uf.union(i,map.get(nums[i]-1));
            }
        }
        return uf.maxUnion();
    }
}

class UF{
    private int[] list;
    public UF(int n){
        list = new int[n];
        for(int i=0; i<n; i++){
            list[i] = i;
        }
    }

    private int root(int i){
        while(i!=list[i]){
            list[i] = list[list[i]];
            i = list[i];
        }
        return i;
    }

    public boolean connected(int i, int j){
        return root(i) == root(j);
    }

    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        list[i] = j;
    }

    // returns the maxium size of union
    public int maxUnion(){ // O(n)
        int[] count = new int[list.length];
        int max = 0;
        for(int i=0; i<list.length; i++){
            count[root(i)] ++;
            max = Math.max(max, count[root(i)]);
        }
        return max;
    }
}