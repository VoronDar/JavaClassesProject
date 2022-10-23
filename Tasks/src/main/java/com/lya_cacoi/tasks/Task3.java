package com.lya_cacoi.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3 {

    public static void main(String[] args) {
        System.out.println("----------1----------");
        System.out.println(solutions(1, 0, -1));
        System.out.println(solutions(1, 0, 0));
        System.out.println(solutions(1, 0, 1));
        System.out.println("----------2----------");
        System.out.println(findZip("all zip files are zipped"));
        System.out.println(findZip("all zip files are compressed"));
        System.out.println("----------3----------");
        System.out.println(checkPerfect(6));
        System.out.println(checkPerfect(28));
        System.out.println(checkPerfect(496));
        System.out.println(checkPerfect(12));
        System.out.println(checkPerfect(97));
        System.out.println("----------4----------");
        System.out.println(flipEndChars("Cat, dog and mouse."));
        System.out.println(flipEndChars("ada"));
        System.out.println(flipEndChars("Ada"));
        System.out.println(flipEndChars("z"));
        System.out.println("----------5----------");
        System.out.println(isValidHexCode("#CD5C5C"));
        System.out.println(isValidHexCode("#EAECEE"));
        System.out.println(isValidHexCode("#eaecee"));
        System.out.println(isValidHexCode("#CD5C58C"));
        System.out.println(isValidHexCode("#CD5C&C"));
        System.out.println(isValidHexCode("#CD5C5C"));
        System.out.println("----------6----------");
        System.out.println(same(new int[]{1, 3, 4, 4, 4}, new int[]{2, 5, 7}));
        System.out.println(same(new int[]{9, 8, 7, 6}, new int[]{4, 4, 3, 1}));
        System.out.println(same(new int[]{2}, new int[]{3, 3, 3, 3, 3}));
        System.out.println("----------7----------");
        System.out.println(isKaprekar(3));
        System.out.println(isKaprekar(5));
        System.out.println(isKaprekar(297));
        System.out.println("----------8----------");
        System.out.println(longestZero("01100001011000"));
        System.out.println(longestZero("100100100"));
        System.out.println(longestZero("11111"));
        System.out.println("----------9----------");
        System.out.println(nextPrime(12));
        System.out.println(nextPrime(24));
        System.out.println(nextPrime(11));
        System.out.println("----------10----------");
        System.out.println(rightTriangle(3, 4, 5));
        System.out.println(rightTriangle(145, 105, 100));
        System.out.println(rightTriangle(70, 130, 110));
    }


    /**
     * 1 задание.
     * @return количество решений квадратного уравнения ax^2 + bx + c = 0
     */
    private static int solutions(int a, int b, int c) {
        // используем формулу дискрименанта и его свойства
        int d = b ^ 2 - 4 * a * c;
        if (d == 0) {
            return 1;
        } else if (d > 1) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * 2 задание
     * @return позицию второго вхождения 'zip' в строке или -1 если такого нет.
     * */
    private static int findZip(String s) {
        // регулярка находит все вхождения последовательности 'zip'
        Pattern pattern = Pattern.compile("zip");
        Matcher matcher = pattern.matcher(s);
        // количество найденных последовательностей 'zip'
        int foundOccurrence = 0;
        while (matcher.find() && foundOccurrence < 2) {
            // если это второе вхождение, то возвращается его позиция
            if (foundOccurrence == 1) {
                return matcher.start();
            }
            foundOccurrence += 1;
        }
        return -1;
    }

    /**
     * 3 задание
     * @return true если сумма множителей этого числа (включая единицу, исключая само число) = самому числу
     * */
    private static boolean checkPerfect(int number) {
        int sum = 1;
        // у каждого множителя есть пара.
        // Первый множитель находится до корня числа. Второй - после.
        // Поэтому можно перебрать все множители до корня
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                sum+=i;
                // если множитель не является корнем - то добавляем в сумму его пару
                if (i*i != number) {
                    sum+= number/i;
                }
            }
        }
        return sum == number;
    }

    /**
     * 4 задание
     * @return модицифированную строку s по правилам:
     * – Если длина строки меньше двух, верните "несовместимо".".
     * – Если первый и последний символы совпадают, верните "два-это пара.".
     * - Иначе заменить первый и последний символ местами.
     * */
    private static String flipEndChars(String s) {
        if (s.length() < 2) {
            return "incompatible";
        } else if (s.charAt(0) == s.charAt(s.length() - 1)) {
            return "Two's a pair";
        } else {
            return s.charAt(s.length() - 1) +
                    s.substring(1, s.length() - 2) +
                    s.charAt(0);
        }
    }

    /**
     * 5 задание
     * @return true если в данном строке находится корректный шестнадцатеричный код
     * */
    private static boolean isValidHexCode(String s) {
        // регулярка которая проверяет строку на полное соответствие: в начале #, в конце ровно 6 символов (цифры или ABCDEF).
        // Сравниваем без учета регистра
        return Pattern.compile("#[\\dABCDEF]{6}", Pattern.CASE_INSENSITIVE).matcher(s).matches();
    }

    /**
     * 6 задание
     * @return true если количество уникальных элементов в массивах равно.
     * */
    private static boolean same(int[] a1, int[] a2) {
        // локальный класс для возвращаения уникальных значений массива
        class SetBuilder {
            Set<Integer> build(int[] a) {
                Set<Integer> s = new HashSet<>();
                for (int c : a) {
                    s.add(c);
                }
                return s;
            }

        }
        // проверяем, что у множеств элементов этих массивов одинаковые размеры
        return new SetBuilder().build(a1).size() == new SetBuilder().build(a2).size();
    }

    /**
     * 7 задание
     * @return true если число является числом капрекара
     * Число капрекара - это положительное целое число, которое после возведения в
     * квадрат и разбиения на две лексикографические части равно сумме двух
     * полученных новых чисел
     */
    private static boolean isKaprekar(int a) {
        // возмодим число в квадрат
        String aStr = "" + a*a;
        // разделитель нового числа надвое
        int indexOfSecond = aStr.length() / 2;
        // правое число
        int right = Integer.parseInt(aStr.substring(indexOfSecond));
        // если число состоит из 1 символа, то в левой стороне числа не будет.
        // в этом случае запишем 0
        int left;
        try {
            left = Integer.parseInt(aStr.substring(0, indexOfSecond));
        } catch (NumberFormatException e) {
            left = 0;
        }
        // если сумма левой и правой частей квадрата числа равна самому числу - то это число капрекара
        return right + left == a;
    }

    /**
     * 8 задание
     * @return максимальную последовательность из нулей в строке
     * */
    private static String longestZero(String s) {
        // максимальная найденная последовательность нулей
        int maxLength = 0;
        // текущая последовательность нулей
        int currentLength = 0;
        for (char c: s.toCharArray()) {
            // если сейчас не 0, то сбрасыываем найденное количество нулей
            if (c != '0') {
                currentLength = 0;
            } else {
                currentLength+=1;
            }
            // если текущее количество нулей больше максимального - перезаписываем
            if (currentLength > maxLength) {
                maxLength = currentLength;
            }
        }
        // возвращаем 0 повторенное найденное количество раз
        return "0".repeat(maxLength);
    }

    /**
     * 9 задание
     * @return d если оно простое или следующее простое число.
     * */
    private static int nextPrime(int d) {
        class PrimeChecker {
            boolean isPrime(int number) {
                // проверяем что на промежутке от 2 до корня числа нет его множителей.
                // если это так - то оно простое
                for (int i = 2; i <= Math.sqrt(number); i++) {
                    if (number % i == 0) {
                        return false;
                    }
                }
                return true;
            }
        }
        PrimeChecker checker = new PrimeChecker();
        // ищем среди заданного числа и следующих за ним простое.
        while (true) {
            if (checker.isPrime(d)) {
                return d;
            }
            d++;
        }
    }

    /**
     * 10 задание
     * @return true если по заданым сторонам можно образовать прямоугольный треугольник
     * */
    private static boolean rightTriangle(int a, int b, int c) {
        // закидываем в массив все стороны
        ArrayList<Integer> array = new ArrayList<>();
        array.add(a);
        array.add(b);
        array.add(c);
        Collections.sort(array);
        // сортируем стороны. Самая большая = гипотенуза. Проверяем что выполняется теорема пифагора
        return Math.pow(array.get(0), 2) + Math.pow(array.get(1), 2) == Math.pow(array.get(2), 2);
    }

}
