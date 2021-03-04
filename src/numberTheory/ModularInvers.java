package numberTheory;

public class ModularInvers {
    static long MOD(long a, long b){
        return (a % b + b) % b;
    }
    public static long getModInv(long a, long m){
        EuclidianForGCD EEGCD = new EuclidianForGCD();
        EuclidianForGCD.d = 1;
        EEGCD.extendedEuclid(a, m);
        return MOD(EuclidianForGCD.x, m);
    }
}
