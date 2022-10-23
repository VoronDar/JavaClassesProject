package com.lya_cacoi.tasks;


public class Task1 {

    public static void main(String[] args) {
        System.out.println("==first==");
        System.out.println(remainder(1, 3));
        System.out.println(remainder(3, 4));
        System.out.println(remainder(-9, 45));
        System.out.println(remainder(5, 5));
        System.out.println("==second==");
        System.out.println(triArea(3, 2));
        System.out.println(triArea(7, 4));
        System.out.println(triArea(10, 10));
        System.out.println("==third==");
        System.out.println(animals(2, 3, 5));
        System.out.println(animals(1, 2, 3));
        System.out.println(animals(5, 2, 8));
        System.out.println("==fourth==");
        System.out.println(profitableGamble(0.2, 50, 9));
        System.out.println(profitableGamble(0.9, 1, 2));
        System.out.println(profitableGamble(0.9, 3, 2));
        System.out.println("==fifth==");
        System.out.println(operation(24, 15, 9));
        System.out.println(operation(24, 26, 2));
        System.out.println(operation(15, 11, 11));
        System.out.println("==sixth==");
        System.out.println(ctoa('A'));
        System.out.println(ctoa('n'));
        System.out.println(ctoa('['));
        System.out.println(ctoa('\\'));
        System.out.println("==seventh==");
        System.out.println(addUpTo(3));
        System.out.println(addUpTo(10));
        System.out.println(addUpTo(7));
        System.out.println("==eighth==");
        System.out.println(nextEdge(8, 10));
        System.out.println(nextEdge(5, 7));
        System.out.println(nextEdge(9, 2));
        System.out.println("==ninth==");
        System.out.println(sumOfCubes(new int[]{1, 5, 9}));
        System.out.println(sumOfCubes(new int[]{3, 4, 5}));
        System.out.println(sumOfCubes(new int[]{2}));
        System.out.println(sumOfCubes(new int[]{}));
        System.out.println("==tenth==");
        System.out.println(abcmatch(42, 5, 10));
        System.out.println(abcmatch(5, 2, 1));
        System.out.println(abcmatch(1, 2, 3));
    }


    /**
     * 1 задание
     * @return remainder of dividing d to d2
     * Использует оператор % для вычисления
     */
    private static int remainder(int d, int d2) {
        return d % d2;
    }

    /**
     * 2 задание
     * @return the area of triangle of height=h, a=base
     */
    private static int triArea(int a, int h) {
        return (int) Math.ceil(((double) a * h) / 2);
    }

    /**
     * 3 задание
     * @return amount of length of given animals
     * chicken get 2 legs, cow get 4, pig get 4.
     */
    private static int animals(int chickens, int cows, int pigs) {
        return chickens * 2 + cows * 4 + pigs * 4;
    }

    /**
     * 4 задание
     * @return true if prob*prize > pay
     */
    private static boolean profitableGamble(double prob, int prize, int pay) {
        return prob * prize > pay;
    }

    /**
     * 5 задание
     * @return how you should operate on numbers a and b to get h
     */
    private static String operation(int n, int a, int b) {
        if (a + b == n) {
            return "added";
        } else if (a - b == n) {
            return "substracted";
        } else if (a * b == n) {
            return "multiplied";
        } else if (a / b == n) {
            return "divided";
        } else {
            return "none";
        }
    }

    /**
     * 6 задание
     * @return ASCII of c
     */
    private static int ctoa(char c) {
        return c;
    }

    /**
     * 7 задание
     * @return sum of all numbers from 1 to d
     */
    public static int addUpTo(int d) {
        if (d == 1) {
            return d;
        } else {
            return d + addUpTo(d - 1);
        }
    }

    /**
     * 8 задание
     * @return max possible length of a side of a triangle, if a and b are other sides.
     * В таске ответ неверный, там на 1 меньше чем можно
     * Неравенство такое: a + b >= c, а решение в таске для строгого неравенства
     */
    private static int nextEdge(int a, int b) {
        return a + b - 1;
    }

    /**
     * 9 задание
     * return sum of cubes all numbers from the given array
     */
    private static int sumOfCubes(int[] cubes) {
        int sum = 0;
        for (int c : cubes) {
            sum += Math.pow(c, 3);
        }
        return sum;
    }

    /**
     * 10 задание:
     * add a to itself b times.
     * @return true if the result is divisible by a number c
     */
    private static boolean abcmatch(int a, int b, int c) {
        for (int i = 0; i < b; i++) {
            a += a;
        }
        return (a % c) == 0;
    }

}
