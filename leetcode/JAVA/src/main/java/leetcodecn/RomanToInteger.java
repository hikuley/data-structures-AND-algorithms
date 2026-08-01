package leetcodecn;

import java.util.HashMap;
import java.util.Map;

class RomanToInteger {


    //IMPORTANT!! Submit Code Region Begin(Do not remove this line)
    class Solution {
        public int romanToInt(String string) {
            int result = 0;
            Map<Character, Integer> map = new HashMap<>();
            map.put('I', 1);
            map.put('V', 5);
            map.put('X', 10);
            map.put('L', 50);
            map.put('C', 100);
            map.put('D', 500);
            map.put('M', 1000);

            final char[] charArray = string.toCharArray();

            for (int i = 0; i < charArray.length; i++) {
                final char character = charArray[i];
                final boolean containsKey = map.containsKey(character);
                if (!containsKey) return 0;
                final Integer value = map.get(character);
                result += value;
            }


            return result;
        }
    }
    //IMPORTANT!! Submit Code Region End(Do not remove this line)

    public static void main(String[] args) {
        RomanToInteger romanToInteger = new RomanToInteger();
        final Solution solution = romanToInteger.new Solution();
        System.out.println(solution.romanToInt("III"));
        System.out.println(solution.romanToInt("LVIII"));
        System.out.println(solution.romanToInt("MCMXCIV"));
    }

}