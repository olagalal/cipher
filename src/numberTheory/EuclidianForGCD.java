package numberTheory;

public class EuclidianForGCD {
	static long x, y, d;
	void extendedEuclid(long a, long b){
		if(b == 0) { 
                    x = 1; 
                    y = 0; 
                    d = a; 
                    return; 
                }
		extendedEuclid(b, a % b);
		long x1 = y;
		long y1 = x - a / b * y;
		x = x1; y = y1;
	}
        public static long GCD(long a, long b){
            return b == 0 ? a : GCD(b, a % b);
        }
}
