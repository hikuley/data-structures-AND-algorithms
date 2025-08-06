//IMPORTANT!! Submit Code Region Begin(Do not remove this line)
/**
 * @param {string} string
 * @return {boolean}
 *
 *  Note: Works with Last IN First OUT => LIFO =>
 *      push(..) => adds an item to the top of Stack
 *      pop()    => removes an item from the top of stack
 *
 */
const isValid = function (string) {
    const stack = [];

    for (let i = 0; i < string.length; i++) {

        let char = string[i];

        if (char === "(") {
            stack.push(")");
        } else if (char === "{") {
            stack.push("}");
        } else if (char === "[") {
            stack.push("]");
        } else if (stack.pop() !== char) {
            return false;
        }

    }

    return stack.length === 0;
};
//IMPORTANT!! Submit Code Region End(Do not remove this line)

console.log(isValid("({})"));



