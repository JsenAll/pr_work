package com.org.pr_work.leetCode;

import java.util.*;

/**
 * 49. 字母异位词分组
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 *
 * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
 *
 *
 *
 * 示例 1:
 *
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 示例 2:
 *
 * 输入: strs = [""]
 * 输出: [[""]]
 * 示例 3:
 *
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 *
 *
 * 提示：
 *
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 */
public class Solution2 {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> stringStringMap = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String s = new String(chars);
            List<String> ls = new ArrayList<>();
            if (stringStringMap.containsKey(s)) {
                ls = stringStringMap.get(s);
            }
            ls.add(str);
            stringStringMap.put(s, ls);
        }
        List<List<String>> lls = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : stringStringMap.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
            lls.add(entry.getValue());
        }

        return lls;

    }

    public static void main(String[] args) {
        String[] strs = {"a"};
        List<List<String>> lists = new Solution2().groupAnagrams(strs);
        System.out.println("输出结果:");
        for (List<String> list : lists) {
            System.out.println(list);
        }
    }
}































