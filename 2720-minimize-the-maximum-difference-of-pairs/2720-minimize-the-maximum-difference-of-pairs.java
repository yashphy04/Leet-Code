class Solution {
    public int minimizeMax(int[] nums, int p) {
        Arrays.sort(nums);
        int n = nums.length, l = 0, r = nums[n - 1] - nums[0];
        int result = Integer.MAX_VALUE;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (isValid(nums, p, mid)) {
                result = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return result;
    }

    static boolean isValid(int[] nums, int p, int mid) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] - nums[i] <= mid) {
                count++;
                i++; // skip the next element
            }
        }
        return count >= p;
    }
}