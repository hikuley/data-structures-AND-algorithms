package leetcodecn;

import java.util.HashMap;
import java.util.Map;

class TwoSum {


    //IMPORTANT!! Submit Code Region Begin(Do not remove this line)
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap(); // value, key

            for (int i = 0; i < nums.length; i++) map.put(nums[i], i);

            for (int i = 0; i < nums.length; i++) {
                int complement = target - nums[i];

                if (map.containsKey(complement) && map.get(complement) != i) {
                    return new int[]{i, map.get(complement)};
                }

            }
            return new int[]{};
        }
    }
//IMPORTANT!! Submit Code Region End(Do not remove this line)

    public static void main(String[] args) {
        // Test the TwoSum solution
        Solution solution = new TwoSum().new Solution();
        int[] nums = {2, 5, 5, 11};
        int target = 10;
        int[] result = solution.twoSum(nums, target);
        if (result.length == 2) {
            System.out.println("Indices: " + result[0] + ", " + result[1]);
        } else {
            System.out.println("No two sum solution found.");
        }

    }
}