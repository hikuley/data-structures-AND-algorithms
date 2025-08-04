package leetcodecn;

class ReverseString {

    //IMPORTANT!! Submit Code Region Begin(Do not remove this line)
    class Solution {
        public void reverseString(char[] s) {
            int left = 0;
            int right = s.length - 1;
            while (left < right) {
                char temp = s[left];
                s[left] = s[right];
                s[right] = temp;
                left++;
                right--;
            }
        }
    }
//IMPORTANT!! Submit Code Region End(Do not remove this line)

    public static void main(String[] args) {
        // test reverseString
        ReverseString rs = new ReverseString();
        Solution solution = rs.new Solution();
        char[] s = {'h', 'e', 'l', 'l', 'o'};
        solution.reverseString(s);
        System.out.println(s); // should print "olleh"
    }
}