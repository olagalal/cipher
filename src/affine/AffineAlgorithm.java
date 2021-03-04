/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package affine;

import java.math.BigInteger;

/**
 *
 * @author hodam
 */
public class AffineAlgorithm {

    private char language[] = new char[83];
    private char special[] = new char[30];
    private String input;
    private int k;
    private int m;
    private int n = 26;

    boolean up = false;
    boolean specials = false;
    boolean space = false;

    public AffineAlgorithm() {
        for (int i = 0; i < 26; i++) {
            language[i] = (char) (i + 97);

        }

        for (int i = 0; i < 30; i++) {
            if (i < 15) {
                special[i] = (char) (i + 33);
            } else if (i < 22) {
                special[i] = (char) (i - 15 + 58);
            } else if (i < 28) {
                special[i] = (char) (i - 22 + 91);
            } else {
                special[i] = (char) (i - 28 + 123);
            }
        }
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    void customAlphabet(String custom) {
        n = custom.length();
        for (int i = 0; i < n; i++) {
            language[i] = custom.charAt(i);
        }
    }

    void insertUpperCase() {

        for (int i = 26; i < 52; i++) {
            language[i] = (char) (i - 26 + 65);
        }
        up = true;

    }

    void insertSpecialCharacters() {
        int i;
        if (up == true) {
            i = 52;
        } else {
            i = 26;
        }
        for (int j = 0; j < special.length; j++, i++) {
            language[i] = special[j];
        }
        specials = true;
    }

    void insertSpaces() {
        int x = 83;
        if (!up) {
            x -= 26;
        }
        if (!specials) {
            x -= 30;
        }
        language[x - 1] = (char) 32;
        space = true;
    }

    String encryption() {
        for (int i = 0; i < language.length; i++) {
            System.out.println(language[i]);
        }
        String output = "";
        char[] p = new char[input.length()];

        BigInteger x = new BigInteger("" + m);
        BigInteger y = new BigInteger("" + n);
        if (!x.gcd(y).equals(BigInteger.ONE)) {
            return "Wrong Value of M";
        }

        for (int i = 0; i < input.length(); i++) {
            int a = -1;

            p[i] = input.charAt(i);

            if (!up && Character.isUpperCase(p[i])) {
                p[i] = Character.toLowerCase(p[i]);
                System.out.println(p[i]);
            }

            if (!specials && !Character.isLetter(p[i]) && !Character.isWhitespace(p[i])) {
                output += p[i];
            }

            for (int j = 0; j < n; j++) {
                if (p[i] == language[j]) {
                    a = j * m + k;
                    j = language.length;
                }
            }
            if (a < 0) {
                continue;
            }
            BigInteger text = new BigInteger("" + a);
            BigInteger cipher = text.mod(y);
            int cipher1 = Integer.parseInt("" + cipher);
            output += language[cipher1];
        }
        System.out.println(up);
        System.out.println(specials);
        System.out.println(space);
        return output;
    }

    String decryption() {
        String plain = "";
        char[] p = new char[input.length()];

        BigInteger x = new BigInteger("" + m);
        BigInteger y = new BigInteger("" + n);
        if (!x.gcd(y).equals(BigInteger.ONE)) {
            return "Wrong Value of M";
        }
        BigInteger z = x.modInverse(y);

        String s = "" + z;
        int modInv = Integer.parseInt(s);

        for (int i = 0; i < input.length(); i++) {
            p[i] = input.charAt(i);
            int a = 0;

            for (int j = 0; j < n; j++) {
                if (p[i] == language[j]) {
                    a = j - k;
                    j = language.length;
                }
            }

            BigInteger text = new BigInteger("" + (a * modInv));
            BigInteger pln = text.mod(y);
            int pln1 = Integer.parseInt("" + pln);
            plain += language[pln1];
        }

        return plain;

    }

}
