//IMPORTANT!! Submit Code Region Begin(Do not remove this line)
/**
 * @param {number[]} nums
 * @param {number} target
 * @return {number[]}
 */
const twoSum = function (nums, target) {
    const map = new Map();

    for (let i = 0; i < nums.length; i++) {
        map.set(nums[i], i);
    }

    for (let i = 0; i < nums.length; i++) {
        let complement = target - nums[i];

        if (map.has(complement) && map.get(complement) !== i) {
            return [i, map.get(complement)]
        }
    }

    return null;
};
//IMPORTANT!! Submit Code Region End(Do not remove this line)


const sampleNums = [3, 2, 4], target = 6;
const output2 = twoSum(sampleNums, target);
console.log(output2)