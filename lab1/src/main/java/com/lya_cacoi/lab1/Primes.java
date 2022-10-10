package com.lya_cacoi.lab1;

/**
 * Utils for calculating the prime numbers
 */
public class Primes {
    public static void main(String[] args) {
        for (int i = 2; i <= 100; i++) {
            if (isPrime(i))
                System.out.print(i + " ");
        }
    }

    /**
     * check if the given number is a prime number. Takes O(n) time complexity
     *
     * @return true if the given number is prime
     */
    public static boolean isPrime(int n) {
        if (n < 0) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if ((n % i) == 0) {
                return false;
            }
        }
        return true;
    }
}
