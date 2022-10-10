package com.lya_cacoi.lab1;

/**
 * Utils for checking palindrome strings
 */
public class Palindrome {
    public static void main(String[] args) {
        double d1 = 0;
        for (int i = 1; i <= 8; i++) {
            d1 += 0.1;
        }

        double d2 = 0.1 * 8;

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d1 == d2);
        System.out.println(Double.compare(d1, d2));

        for (String s : args) {
            System.out.printf(
                    "reversed string for '%s' is  '%s'. The string is palindrome: %b%n",
                    s,
                    reverseString(s),
                    isPalindrome(s)
            );
        }
    }

    /**
     * get reversed string
     *
     * @return reversed String or null if the original string is null
     */
    public static String reverseString(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * check if the string is a palindrome. Takes O(n) time complexity
     *
     * @return false if the string is null or if it is not a palindrome. returns
     */
    public static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }
        String reversedString = reverseString(s);
        return s.equals(reversedString);
    }
}
