package compilers.util;

public class HashString {
    //static private char[] stringHash = "aaaaaaaaaaaaaaa".toCharArray();
    static String stringHash = "";
    static Character current = 'a';

    static public String updateStringHash() {
        if (current != 'z') {
            current++;
        } else {
            stringHash += current;
            current = 'a';
        }
        return stringHash + current;
    }

}
