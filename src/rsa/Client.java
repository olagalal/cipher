package rsa;

import java.util.ArrayList;
import numberTheory.EuclidianForGCD;
import numberTheory.ModularInvers;
import numberTheory.PrimeFactorization;
import numberTheory.PrimeNumbers;

public class Client {
    private long p, q, n, d, e, phiN;
    private PublicKey pk;
    public Client(long p, long q){
        setP(p);
        setQ(q);
        setN(p * q);
        setPhiN(PrimeFactorization.phi(p) * PrimeFactorization.phi(q));
        setE(getRandomeE());
        setD(ModularInvers.getModInv(e, phiN));
        setPk(new PublicKey(n, e));
    }
    public Client(long p, long q, long e){
        setP(p);
        setQ(q);
        setN(p * q);
        setPhiN(PrimeFactorization.phi(p) * PrimeFactorization.phi(q));
        setE(e);
        setD(ModularInvers.getModInv(e, phiN));
        setPk(new PublicKey(n, e));
    }
    private void check(){
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n = " + n);
        System.out.println("phi N = " + phiN);
        System.out.println("e = " + e);
        System.out.println("d = " + d);
    }
    public long power(long x, long p, long m){
        long res = 1;
        while(p != 0){
            if((p & 1) == 1)
                res = ((res % m) * (x % m)) % m;
            x = ((x % m) * (x % m )) % m;
            p >>= 1l;
        }
        return res;
    }
   
    public long getRandomeE(){
        long e = PrimeNumbers.genRandomPrime();;
        while(EuclidianForGCD.GCD(e, phiN) != 1 && e > 1)
            e = PrimeNumbers.genRandomPrime();
        return e;
    }
    public ArrayList<Long> encryptMsg(String msg, PublicKey pkC2){
        check();
        ArrayList<Long> encrypted = new ArrayList<>();
        for(int i = 0; i < msg.length(); ++i)
            encrypted.add(power(msg.charAt(i), pkC2.e, pkC2.n));
        return encrypted;
    }
    public String decryptMsg(ArrayList<Long> encrypted){
        check();
        String s = "";
        for(int i = 0; i < encrypted.size(); ++i){
            long curr = power(encrypted.get(i), d, n);
            s += (char)curr;
        }
        return s;
    }
    public PublicKey getPublicKey(){
        return pk;
    }
    private void setP(long p) {
        this.p = p;
    }

    public void setQ(long q) {
        this.q = q;
    }
    
    private void setN(long n) {
        this.n = n;
    }

    private void setD(long d) {
        this.d = d;
    }


    private void setE(long e) {
        this.e = e;
    }

    private void setPhiN(long phiN) {
        this.phiN = phiN;
    }

    private void setPk(PublicKey pk) {
        this.pk = pk;
    }
    
}
