package leetcodecn;

class TwoSumIiInputArrayIsSorted {


    //IMPORTANT!! Submit Code Region Begin(Do not remove this line)
    class Solution {
        public int[] twoSum(int[] numbers, int target) {
            var left = 0;
            var right = numbers.length - 1;
            while (left < right) {
                var sum = numbers[left] + numbers[right];
                if (sum == target) {
                    return new int[]{left+1, right+1};
                } else if (sum > target) { // sum is too big, move right pointer to the left
                    right--; // right is too big, move it to the left
                } else {
                    left++; // left is too small, move it to the right
                }
            }
            return new int[]{};
        }
    }
    //IMPORTANT!! Submit Code Region End(Do not remove this line)

    public static void main(String[] args) {
        // add your test code

    }
}