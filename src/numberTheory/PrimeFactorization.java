package numberTheory;

import java.util.ArrayList;

public class PrimeFactorization {
        PrimeNumbers pn = new PrimeNumbers();
	static ArrayList<Integer> primes;
        
	public static long phi(long N){
            if(primes == null){
                new PrimeNumbers();
                primes = PrimeNumbers.primes;
            }
            long ans = N, p = primes.get(0);
            int idx = 0;
            while(p * p <= N){
                if(N % p == 0)
                    ans -= ans / p;
                while(N % p == 0)
                    N /= p;
                p = primes.get(++idx);
            }
            if(N != 1) 
                ans -= ans / N;
            return ans;
	}
        
}
