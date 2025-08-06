//IMPORTANT!! Submit Code Region Begin(Do not remove this line)
/**
 * @param {number} x
 * @return {number}
 */
const reverse = function (x) {
    let rev = 0;

    while (x !== 0) {

        // pop operation
        let pop = x % 10;
        x = (x - pop) / 10;

        if (rev > Math.pow(2, 31) / 10 || (rev === Math.pow(2, 31) / 10 && pop > 7)) return 0;
        if (rev < Math.pow(-2, 31) / 10 || (rev === Math.pow(-2, 31) / 10 && pop < -8)) return 0;

        // push operation
        rev = rev * 10 + pop;
    }

    return rev;
};
//IMPORTANT!! Submit Code Region End(Do not remove this line)


console.log(reverse(123))
