package rsa;

import java.math.BigInteger;
import java.util.ArrayList;

public class test {

    public static void main(String[] args) {
        Client c1 = new Client(73, 43, 13);
        Client c2 = new Client(13, 5 , 19);
        ArrayList<Long> enC1 = c1.encryptMsg("Amr 15613 56", c2.getPublicKey());
        for(int i = 0; i < enC1.size(); ++i)
            System.out.print(enC1.get(i) + " ");
        System.out.println("");
        System.out.println(c2.decryptMsg(enC1));
    }
}