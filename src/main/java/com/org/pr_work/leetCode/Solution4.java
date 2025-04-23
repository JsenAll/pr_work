package com.org.pr_work.leetCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 */
public class Solution4 {

    public void moveZeroes(int[] nums) {
        // 不等于0的坐标
        int at= 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[at++] = nums[i];
            }
        }
        for (int i = at; i < nums.length; i++) {
            nums[i] = 0;
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void main(String[] args) {
        int[] nums = {0,1,5,0,3,12};
        new Solution4().moveZeroes(nums);
    }
}
