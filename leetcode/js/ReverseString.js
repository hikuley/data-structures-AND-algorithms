//IMPORTANT!! Submit Code Region Begin(Do not remove this line)
/**
 * @param {character[]} s
 * @return {void} Do not return anything, modify s in-place instead.
 *
 *  This method uses TWO-POINTER APPROACH
 *
 */

// Two-pointer Approach
const reverseString = function (s) {
    let left = 0, right = s.length - 1;
    while (left < right) {

        let temp = s[left];
        s[left] = s[right];
        s[right] = temp

        left++;
        right--;
    }
    return s;
};
//IMPORTANT!! Submit Code Region End(Do not remove this line)

// Example usage:
const test = "hello";
const output = reverseString(test.split("")).join("");
console.log(output); // Output: ["o", "l", "l", "e", "h"]