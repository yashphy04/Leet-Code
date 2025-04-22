public class Solution 
{
    static final int MOD = 1_000_000_007;
    int[] fact, invFact;

    public int idealArrays(int n, int maxValue) 
    {
        int max = n + 100;

        // Step 1: Precompute factorials and inverse factorials
        computeFactorials(max);

        // Step 2: Sieve to get smallest prime factor for fast factorization
        int[] spf = sieve(maxValue);

        long result = 0;

        // Step 3: Iterate over each number as potential array end
        for (int num = 1; num <= maxValue; num++) 
        {
            // Step 4: Prime factorize the number
            Map<Integer, Integer> primeCounts = primeFactorize(num, spf);

            long ways = 1;

            // Step 5: For each exponent, apply stars and bars formula
            for (int exp : primeCounts.values()) 
            {
                ways = ways * comb(n + exp - 1, exp) % MOD;
            }

            // Step 6: Add valid ways ending with this number to result
            result = (result + ways) % MOD;
        }

        // Step 7: Return total
        return (int) result;
    }

    // Step 1: Compute factorials and inverse factorials
    private void computeFactorials(int max) 
    {
        fact = new int[max];
        invFact = new int[max];
        fact[0] = 1;

        // 1a: Compute factorials modulo MOD
        for (int i = 1; i < max; i++) 
        {
            fact[i] = (int)((long)fact[i - 1] * i % MOD);
        }

        // 1b: Compute inverse of factorial[max - 1]
        invFact[max - 1] = modInverse(fact[max - 1]);

        // 1c: Compute inverse factorials using reverse loop
        for (int i = max - 2; i >= 0; i--) 
        {
            invFact[i] = (int)((long)invFact[i + 1] * (i + 1) % MOD);
        }
    }

    // Step 2: Sieve to calculate smallest prime factor for every number
    private int[] sieve(int limit) 
    {
        int[] spf = new int[limit + 1];

        // 2a: Use modified sieve to assign smallest prime factor
        for (int i = 2; i <= limit; i++) 
        {
            if (spf[i] == 0) 
            {
                for (int j = i; j <= limit; j += i) 
                {
                    if (spf[j] == 0)
                    {
                        spf[j] = i;
                    }
                }
            }
        }

        return spf;
    }

    // Step 4: Prime factorization using SPF
    private Map<Integer, Integer> primeFactorize(int num, int[] spf) 
    {
        Map<Integer, Integer> map = new HashMap<>();

        // 4a: Repeatedly divide by SPF until num becomes 1
        while (num > 1) 
        {
            int p = spf[num];
            map.put(p, map.getOrDefault(p, 0) + 1);
            num /= p;
        }

        return map;
    }

    // Step 5: Combination C(a, b) with precomputed factorials
    private long comb(int a, int b) 
    {
        if (b > a)
        {
            return 0;
        } 
        
        return (long)fact[a] * invFact[b] % MOD * invFact[a - b] % MOD;
    }

    // Utility: Modular inverse using Fermat's little theorem
    private int modInverse(int x) 
    {
        return pow(x, MOD - 2);
    }

    // Utility: Binary exponentiation
    private int pow(int a, int b) 
    {
        long res = 1, base = a;
        while (b > 0) 
        {
            if ((b & 1) == 1)
            {
                res = res * base % MOD;
            }

            base = base * base % MOD;
            b >>= 1;
        }
        
        return (int) res;
    }
}