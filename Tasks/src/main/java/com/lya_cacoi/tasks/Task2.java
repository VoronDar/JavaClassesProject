package com.lya_cacoi.tasks;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Task2 {


    public static void main(String[] args) {
        System.out.println("------1------");
        System.out.println(repeat("mice", 5));
        System.out.println(repeat("hello", 3));
        System.out.println(repeat("stop", 1));
        System.out.println("------2------");
        System.out.println(differenceMaxMin(new int[]{10, 4, 1, 4, -10, -50, 32, 21}));
        System.out.println(differenceMaxMin(new int[]{44, 32, 86, 19}));
        System.out.println("------3------");
        System.out.println(isAvgWhole(new int[]{1, 3}));
        System.out.println(isAvgWhole(new int[]{1, 2, 3, 4}));
        System.out.println(isAvgWhole(new int[]{1, 5, 6}));
        System.out.println(isAvgWhole(new int[]{1, 1, 1}));
        System.out.println(isAvgWhole(new int[]{9, 2, 2, 5}));
        System.out.println("------4------");
        System.out.println(Arrays.toString(cumulativeSum(new int[]{1, 2, 3})));
        System.out.println(Arrays.toString(cumulativeSum(new int[]{1, -2, 3})));
        System.out.println(Arrays.toString(cumulativeSum(new int[]{3, 3, -2, 408, 3, 3})));
        System.out.println("------5------");
        System.out.println(getDecimalPlaces("43.20"));
        System.out.println(getDecimalPlaces("400"));
        System.out.println(getDecimalPlaces("3.1"));
        System.out.println("------6------");
        System.out.println(fibonacci(3));
        System.out.println(fibonacci(7));
        System.out.println(fibonacci(12));
        System.out.println("------7------");
        System.out.println(isValid("59001"));
        System.out.println(isValid("853a7"));
        System.out.println(isValid("732 32"));
        System.out.println(isValid("393939"));
        System.out.println("------8------");
        System.out.println(isStrangePair("ratio", "orator"));
        System.out.println(isStrangePair("sparkling", "groups"));
        System.out.println(isStrangePair("bush", "hubris"));
        System.out.println(isStrangePair("", ""));
        System.out.println("------9------");
        System.out.printf("isPrefix %b\n", isPrefix("automation", "auto-"));
        System.out.printf("isSuffix %b\n", isSuffix("arachnophobia", "-phobia"));
        System.out.printf("isPrefix %b\n", isPrefix("retrospect", "sub-"));
        System.out.printf("isSuffix %b\n", isSuffix("vocation", "-logy"));
        System.out.println("------10------");
        System.out.println(boxStep(0));
        System.out.println(boxStep(1));
        System.out.println(boxStep(2));
    }

    /**
     * 1 задание
     * @return вовращает модифицарованную строку originalString, в которой каждый символ повторен repeatCount раз
     */
    private static String repeat(String originalString, int repeatCount) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < originalString.length(); i++) {
            // повторяем символы через String.repeat(n);
            builder.append(String.valueOf(originalString.charAt(i)).repeat(repeatCount));
        }
        return builder.toString();
    }

    /**
     * 2 задание
     * @return разницу между наименьшим и наибольшим числами в массиве.
     * если в массиве только 1 элемент, то вернет 0
     * @throws IllegalStateException если массив пустой
     */
    private static int differenceMaxMin(int[] array) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        if (array.length == 0) {
            throw new IllegalStateException("array size is 0");
        }
        for (int c : array) {
            if (c > max) {
                max = c;
            }
            if (c < min) {
                min = c;
            }
        }
        return max - min;
    }

    /**
     * 3 задание
     * @return true если среднее арифметическое чисел в массиве - целое
     * Возвращает 0 для массива длины 0.
     */
    private static boolean isAvgWhole(int[] array) {
        int sum = 0;
        for (int c : array) {
            sum += c;
        }
        // среднее арифметическое это сумма всех элементов (целое) деленное на количество элементов (целое)
        // дробь может образоваться только при делении. Следовательно если остаток от деления 0, то и частное - целое
        return sum % array.length == 0;
    }

    /**
     * 4 задание
     * @return модифицированный array, в котором каждый элемент содержит самого себя и сумму всех предыдущих
     */
    private static int[] cumulativeSum(int[] array) {
        // найденная сумма всех предыдущих значений
        int old = 0;
        for (int i = 0; i < array.length; i++) {
            int newOld = array[i] + old;
            array[i] = array[i] + old;
            old = newOld;
        }
        return array;
    }

    /**
     * 5 задание
     * @return число десятичных знаков после запятой числа в заданной строке.
     * НЕ учитывает стоящие в конце нули.
     */
    private static int getDecimalPlaces(String number) {
        // находим индекс точки, и по ней определяем количество знаков после запятой
        int indexOfDot = number.lastIndexOf(".");
        if (indexOfDot == -1) {
            return 0;
        } else {
            return number.length() - indexOfDot - 1;
        }
    }

    /**
     * 6 задание
     * @return число фибонначи
     * upd: в таске ответы не верные. Там на 1 промахивается каждый раз.
     */
    private static int fibonacci(int d) {
        // используем рекурсивный алгоритм
        if (d < 2) {
            return d;
        } else {
            return fibonacci(d - 1) + fibonacci(d - 2);
        }
    }

    /**
     * 7 задание
     * @return true если в строке есть не более 5 цифр и никаких других символов
     */
    private static boolean isValid(String s) {
        // регулярка - проверяет строку на полное совпадение "только цифры от 1 до 5 раз"
        return Pattern.compile("\\d{5}").matcher(s).matches();
    }

    /**
     * 8 задание
     * @return true если первый символ 1 строки равен последнему символу второй строки и наоборот.
     */
    private static boolean isStrangePair(String s1, String s2) {
        // если какая-то из строк пустая, то возвращает true только если они обе пустые
        if (s1.isEmpty() || s2.isEmpty()) {
            return s1.isEmpty() && s2.isEmpty();
        }
        return s1.charAt(0) == s2.charAt(s2.length() - 1) && s1.charAt(s1.length() - 1) == s2.charAt(0);
    }

    /**
     * 9 задание
     * @return true если prefix формата "prefix-" является префиксом к word
     */
    private static boolean isPrefix(String word, String prefix) {
        if (prefix.isEmpty()) {
            return true;
        }
        return word.startsWith(prefix.substring(0, prefix.length() - 1));
    }

    /**
     * 9 задание
     * @return true если suffix формата "-suffix" является суффиксом к word
     */
    private static boolean isSuffix(String word, String suffix) {
        if (suffix.isEmpty()) {
            return true;
        }
        return word.endsWith(suffix.substring(1));
    }

    /**
     * 10 задание
     * @return размер фигуры, образованном на шаге d по правилам
     * на 0 шаге фигуры нет
     * на 1 шаге 3 блока добавляются к фигуре
     * на 2 шаге 1 блок отнимается от фигуры
     * дальнейшние шаги чередуют шаги 1 и 2.
     */
    private static int boxStep(int d) {
        // работает рекурсивно
        // если номер шага нечетный - то действие аналогично 1 шагу. Если номер шага четный - то аналогично 2.
        if (d == 0) {
            return 0;
        } else if (d % 2 == 0) {
            return boxStep(d - 1) - 1;
        } else {
            return boxStep(d - 1) + 3;
        }
    }
}
