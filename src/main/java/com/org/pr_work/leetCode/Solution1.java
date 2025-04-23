package com.org.pr_work.leetCode;

import java.util.*;

public class Solution1 {


    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = {2, 2,2, 11, 15};
        int[] ints = new Solution1().twoSum(nums, 6);
        System.out.println(Arrays.toString(ints)); // 输出: [0, 1]
    }
}
