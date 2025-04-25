class Solution {
    public long countInterestingSubarrays(List<Integer> nums, int modulo, int k) {
        int n = nums.size();
        if (k > n) return 0;

        // count[i] = frequency of prefix sum % modulo == i
        int[] count = new int[n + 1];
        count[0] = 1;

        long ans = 0;
        int sum = 0;

        for (int x : nums) {
            x %= modulo;
            if (x == k)
                ++sum;

            sum %= modulo;

            // Find required remainder
            int r = sum - k;
            if (r < 0) r += modulo;

            // Add valid previous prefix sums
            if (r < n)
                ans += count[r];

            // Update count for current sum
            count[sum]++;
        }

        return ans;
    }
}