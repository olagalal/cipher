package numberTheory;

import java.util.ArrayList;
import java.util.Random;

public class PrimeNumbers {

	public static ArrayList<Integer> primes;
	private static int[] isComposite;
        public PrimeNumbers(){
            if(primes == null)
                sieve(100);
        }
	private static void sieve(int N){
            isComposite = new int[N+1];					
            isComposite[0] = isComposite[1] = 1;
            primes = new ArrayList<Integer>();
            for (int i = 2; i <= N; ++i)
                    if (isComposite[i] == 0){
                        primes.add(i);
                        if(1l * i * i <= N)
                            for (int j = i * i; j <= N; j += i)
                                isComposite[j] = 1;
                    }   
	}

	public static boolean isPrime(long N){
            for(long i = 2; i <= N / i; ++i)
                if(N % i == 0)
                    return false;
            return N > 1;
	}
        
        public static long genRandomPrime(){
            if(primes == null)
                sieve(100);
            Random rand = new Random();
            return primes.get(rand.nextInt(primes.size()));
        }
	
}
