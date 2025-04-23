package com.org.pr_work.leetCode;

import java.util.HashSet;
import java.util.Set;

public class Solution3 {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int max = 0;

        for (int num : numSet) {
            // 检查是否是序列的起点
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                // 检查后续连续数字
                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                // 更新最长序列长度
                max = Math.max(max, currentStreak);
            }
        }

        return max;
    }


    public static void main(String[] args) {
        int[] nums = {100,101,102,103,100, 4, 200, 1, 3, 2};
        System.out.println(new Solution3().longestConsecutive(nums));
    }
}