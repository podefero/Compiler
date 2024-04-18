package compilers.util;

import java.util.Arrays;

public class HashString {
    static private char[] stringHash = "aaaaaaaaaaaaaaa".toCharArray();

    static public String updateStringHash() {
        String hash = "";
        for (int i = 0; i < stringHash.length; i++) {
            if (stringHash[i] != 'z') {
                stringHash[i]++;
                hash += stringHash[i];
                return hash;
            }
        }
        return hash;
    }

}
