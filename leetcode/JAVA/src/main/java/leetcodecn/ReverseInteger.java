package leetcodecn;

class ReverseInteger {


    //IMPORTANT!! Submit Code Region Begin(Do not remove this line)
    class Solution {
        public int reverse(int x) {
            var rev = 0;

            while (x != 0) {
                // pop operation
                var pop = x % 10;
                x = (x - pop) / 10;

                if (rev > Math.pow(2, 31) / 10 || (rev == Math.pow(2, 31) / 10 && pop > 7)) return 0;
                if (rev < Math.pow(-2, 31) / 10 || (rev == Math.pow(-2, 31) / 10 && pop < -8)) return 0;

                rev = rev * 10 + pop;
            }

            // push operation
            return rev;
        }
    }
//IMPORTANT!! Submit Code Region End(Do not remove this line)

    public static void main(String[] args) {
        final ReverseInteger reverseInteger = new ReverseInteger();
        final Solution solution = reverseInteger.new Solution();

        System.out.println(solution.reverse(1534236469));

    }
}