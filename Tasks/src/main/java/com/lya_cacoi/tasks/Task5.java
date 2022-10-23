package com.lya_cacoi.tasks;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.StringJoiner;

public class Task5 {

    public static void main(String[] args) {
        System.out.println("----1----");
        System.out.println(Arrays.toString(encrypt("Hello")));
        System.out.println(decrypt(new int[]{72, 33, -73, 84, -12, -3, 13, -13, -68}));
        System.out.println(Arrays.toString(encrypt("Sunshine")));
        System.out.println("----2----");
        System.out.println(canMove("Rook", "A8", "H8"));
        System.out.println(canMove("Bishop", "A7", "G1"));
        System.out.println(canMove("Queen", "C4", "D6"));
        System.out.println("----3----");
        System.out.println(canComplete("butl", "beautiful"));
        System.out.println(canComplete("butlz", "beautiful"));
        System.out.println(canComplete("tulb", "beautiful"));
        System.out.println(canComplete("bbutl", "beautiful"));
        System.out.println("----4----");
        System.out.println(sumDigProd(16, 28));
        System.out.println(sumDigProd(0));
        System.out.println(sumDigProd(1, 2, 3, 4, 5, 6));
        System.out.println("----5----");
        System.out.println(Arrays.toString(sameVowelGroup(new String[]{"toe", "ocelot", "maniac"})));
        System.out.println(Arrays.toString(sameVowelGroup(new String[]{"many", "carriage", "emit", "apricot", "animal"})));
        System.out.println(Arrays.toString(sameVowelGroup(new String[]{"hoops", "chuff", "bot", "bottom"})));
        System.out.println("----6----");
        System.out.println(validateCard(1234567890123456L));
        System.out.println(validateCard(1234567890123452L));
        System.out.println("----7----");
        System.out.println(numToEng(0));
        System.out.println(numToEng(18));
        System.out.println(numToEng(126));
        System.out.println(numToEng(909));
        System.out.println("----8----");
        System.out.println(getSha256Hash("password123"));
        System.out.println(getSha256Hash("Fluffy@home"));
        System.out.println(getSha256Hash("Hey dude!"));
        System.out.println("----9----");
        System.out.println(correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println(correctTitle("sansa stark, lady of winterfell."));
        System.out.println(correctTitle("TYRION LANNISTER, HAND OF THE QUEEN."));
        System.out.println("----10----");
        System.out.println(hexLattice(1));
        System.out.println(hexLattice(7));
        System.out.println(hexLattice(19));
        System.out.println(hexLattice(21));
    }

    /**
     * 1 задание:
     * @return шифр последовательности
     * 1 число - символьный код первой буквы
     * Следующие элементы-это различия между символами
     * */
    private static int[] encrypt(String s) {
        char lastAscii = 0;
        int[] cryphre = new int[s.length()];
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            cryphre[i] = chars[i] - lastAscii;
            lastAscii = chars[i];
        }
        return cryphre;
    }

    /**
     * 1 задание:
     * @return расшифровать последовательность, полученную с помощью encrypt
     * */
    private static String decrypt(int[] s) {
        char lastAscii = 0;
        char[] chars = new char[s.length];
        for (int i = 0; i < s.length; i++) {
            chars[i] = (char) (lastAscii + s[i]);
            lastAscii = chars[i];
        }
        return new String(chars);
    }

    /**
     * 2 задание:
     * @return true если для данной фигуры есть возможность переместиться с позиции from на позицию to
     * не учитываем возможность для пешки пойти сразу на 2 со стартовой позиции, потому что у нас нет инфы о том, чья она
     * так же не рассматриваем для пешки возможность пойти по диагонали для атаки
     */
    private static boolean canMove(String figure, String from, String to) {
        // константы фигур
        final String HORSE = "Horse";
        final String BISHOP = "Bishop";
        final String QUEEN = "Queen";
        final String ROOK = "Rook";
        final String PAWN = "Pawn";
        final String KING = "King";

        // определяем координаты перемещений
        char fromX = from.charAt(0);
        int fromY = Integer.parseInt(String.valueOf(from.charAt(1)));
        char toX = to.charAt(0);
        int toY = Integer.parseInt(String.valueOf(to.charAt(1)));
        // если прошли прямо
        if ((fromY == toY && fromX != toX) || (fromY != toY && fromX == toX)) {
            if (figure.equals(QUEEN) || figure.equals(ROOK)) {
                return true;
            }
            // если прошли прямо строго на 1 клетку куда угодно
            if ((fromY == toY && Math.abs(fromX - toX) == 1) || (fromX == toX && Math.abs(fromY - toY) == 1)) {
                if (figure.equals(KING)) {
                    return true;
                }
            }
            // если прошли вперед на 1 клетку
            if (fromX == toX && toY - fromY == 1) {
                return figure.equals(PAWN);
            }
        }
        // если прошли диагонально
        else if (Math.abs(fromX - toX) == Math.abs(fromY - toY)) {
            if (figure.equals(QUEEN) || figure.equals(BISHOP)) {
                return true;
            }
            // если прошли строго на 1 шаг
            if (Math.abs(fromX - toX) == 1) {
                return figure.equals(KING);
            }
        }
        // если это конь
        else if ((Math.abs(fromX - toX) == 1 && Math.abs(fromY - toY) == 2)
                || (Math.abs(fromY - toY) == 1 && Math.abs(fromX - toX) == 2)) {
            return figure.equals(HORSE);
        }
        return false;
    }

    /**
     * 3 задание:
     * @return true если посредством добавления в 1 строку символов можно получить 2 строку
     * */
    private static boolean canComplete(String s1, String s2) {
        /*
        * проходим по 2 строке. Проверяем каждый символ с тем, на который указывает s1Iterator
        * если совпадает, значит 1 символ найден и s1Iterator двигается вперед.
        * */
        int s1Iterator = 0;
        for (int s2Iterator = 0; s2Iterator < s2.length(); s2Iterator++) {
            if (s1.charAt(s1Iterator) == s2.charAt(s2Iterator)) {
                s1Iterator++;
                if (s1Iterator == s1.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 4 задание:
     * @return цифру образованную при произведении всех цифр суммы чисел (и всех цифр получившегося произведения и тд если необходимо)
     * */
    private static int sumDigProd(int... a1) {
        class Multiplier {
            // получить произведение цифр числа
            int getMultipleOfNumbers(long d) {
                int mult = 1;
                while (d > 0) {
                    mult *= (int) (d % 10);
                    d /= 10;
                }
                return mult;
            }
        }

        // складываем числа
        int newA = 0;
        for (int a : a1) {
            newA += a;
        }
        // умножаем все цифры суммы до тех пор, пока это произведение > 9
        Multiplier multiplier = new Multiplier();
        while (newA >= 10) {
            newA = multiplier.getMultipleOfNumbers(newA);
        }
        return newA;
    }

    /**
     * 5 задание:
     * @return массив слов, в котором есть все слова, включая первое, в котором есть те же гласные, что и в первом слове
     * */
    private static String[] sameVowelGroup(String[] words) {
        // получаем множество гласных из первого слова, удалив из слова все согласные
        Set<Character> vowels = new HashSet<>();
        String first = words[0].replaceAll("[qwrtiopsdfghjklzxcvbnm]", "");
        for (char c : first.toCharArray()) {
            vowels.add(c);
        }

        // задаем массив результата
        ArrayList<String> resultWords = new ArrayList<>();
        resultWords.add(words[0]);

        // аналогично первому слову получаем множества гласных других слов и добавляем по возможности в массив результата
        for (int i = 1; i < words.length; i++) {
            Set<Character> currentVowels = new HashSet<>();
            String it = words[i].replaceAll("[qwrtiopsdfghjklzxcvbnm]", "");
            for (char c : it.toCharArray()) {
                currentVowels.add(c);
            }
            if (currentVowels.equals(vowels)) {
                resultWords.add(words[i]);
            }
        }
        return resultWords.toArray(new String[]{});
    }


    /**
     * 6 задание:
     * @return true если номер удовлетворяет условиям:
     * * в нем от 14 до 19 цифр
     * * проходит тест луна:
     * – Удалите последнюю цифру (это"контрольная цифра").
     * – Переверните число.
     * – Удвойте значение каждой цифры в нечетных позициях. Если удвоенное значение имеет
     * более 1 цифры, сложите цифры вместе (например, 8 x 2 = 16 ➞ 1 + 6 = 7).
     * – Добавьте все цифры.
     * – Вычтите последнюю цифру суммы (из шага 4) из 10. Результат должен быть равен
     * контрольной цифре из Шага 1.
     * */
    private static boolean validateCard(Long number) {
        // проверяем что число в нужном диапазоне
        if (number< Math.pow(10, 14) || number >= Math.pow(10, 20)) {
            return false;
        }
        // шаг 1 - находим контрольную сумму
        int controlNum = (int)(number%10);
        String num = number.toString();
        num = num.substring(0, num.length()-1);
        // шаг 2-3-4, пробегаемся по числу в обратном порядке, и сразу же складываем найденные цифры (или удвоенные цифры на нечетных позициях)
        int sum = 0;
        for (int i = num.length()-1; i >= 0; i--) {
            int doubledInteger = Integer.parseInt(num.substring(i, i+1));
            if ((num.length()- i)%2 == 1) {
                doubledInteger*=2;
                if (doubledInteger > 9) {
                    doubledInteger = doubledInteger % 10 + (doubledInteger / 10) % 10;
                }
            }
            sum+= doubledInteger;
        }
        // шаг 5 - сравниваем число с контрольной цифрой
        return (10 - (sum%10)) == controlNum;
    }

    /**
     * 7 задание:
     * @return перевод цифры в ее строковой эквивалент на английском
     * */
    private static String numToEng(int number) {
        class NumTransformer {
            // перевод цифры в слово
            String oneNumberToString(int n) {
                switch (n) {
                    case 1:
                        return "one";
                    case 2:
                        return "two";
                    case 3:
                        return "three";
                    case 4:
                        return "four";
                    case 5:
                        return "five";
                    case 6:
                        return "six";
                    case 7:
                        return "seven";
                    case 8:
                        return "eight";
                    case 9:
                        return "nine";
                    default:
                        return null;
                }
            }
            // перевод десятков в строки (кроме десятков = 0,1)
            String decToString(int decCount) {
                switch (decCount) {
                    case 2:
                        return "twenty";
                    case 3:
                        return "thirty";
                    case 4:
                        return "forty";
                    case 5:
                        return "fifty";
                    case 6:
                        return "sixty";
                    case 7:
                        return "seventy";
                    case 8:
                        return "eighty";
                    case 9:
                        return "ninety";
                    default:
                        return null;
                }
            }
            // перевод чисел от 10 до 19 в строки
            String dec1ToString(int n) {
                switch (n) {
                    case 0:
                        return "ten";
                    case 1:
                        return "eleven";
                    case 2:
                        return "twelve";
                    case 3:
                        return "thirteen";
                    case 4:
                        return "fourteen";
                    case 5:
                        return "fifteen";
                    case 6:
                        return "sixteen";
                    case 7:
                        return "seventeen";
                    case 8:
                        return "eighteen";
                    case 9:
                        return "nineteen";
                    default:
                        return null;
                }
            }
        }
        // находим число сотен десятков и едениц
        int hundred = (number / 100)%10;
        int dec = (number / 10) %10;
        int n = number % 10;

        NumTransformer numTransformer = new NumTransformer();
        // получаем строку для  сотен
        String hundredString = numTransformer.oneNumberToString(hundred);
        // получаем отдельно строки для десятков и едениц или общую строку для диапазона от 10 до 19
        String nString = null;
        String decString;
        if (dec != 1) {
            nString = numTransformer.oneNumberToString(n);
            if (n == 0) {
                nString = "zero";
            }
            decString = numTransformer.decToString(dec);
        } else {
            decString = numTransformer.dec1ToString(n);
        }

        // складываем все значения
        StringJoiner joiner = new StringJoiner(" ");
        if (hundredString != null) {
            joiner.add(hundredString);
            joiner.add("hundred");
        }
        if (decString != null) {
            joiner.add(decString);
        }
        if (nString != null) {
            joiner.add(nString);
        }
        return joiner.toString();
    }

    /**
     * 8 задание:
     * @return отформатированный в виде шестнадцатеричной цифры хеш SHA-256 для строки
     * */
    private static String getSha256Hash(String s) {
        try {
            // шифруем в байты строку через MessageDigest
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
            // переводим байты в хеш
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 9 задание:
     * @return строка, в которой все слова кроме of, in, and, the - капитализированы
     * */
    private static String correctTitle(String s) {
        // делим строку на слова
        String[] words = s.toLowerCase(Locale.ROOT).split(" ");
        StringJoiner joiner = new StringJoiner(" ");
        for (String word: words) {
            // капитализуем все слова, не являющиеся служебными
            if (!word.equals("of") && !word.equals("in") && !word.equals("and") && !word.equals("the")) {
                word = word.substring(0,1).toUpperCase(Locale.ROOT) + word.substring(1);
            }
            joiner.add(word);
        }
        return joiner.toString();
    }

    /**
     * 10 задание:
     * @return вывести на экран гексагональную решетку для центрированного шестиугольного числа
     * */
    private static String hexLattice(int n) {
        // объявим циклы - это количество кругов решетки. Например для 7 - 2 цикла. для 37 - 4 цикла
        int hexCycle = 1;
        int currentElements = 1;
        // пытаемся подобрать цикл для данного числа эллементов.
        // Если цикл не удается подобрать - значит число не центрированное шестиугольное
        while (currentElements < n) {
            // число элементов для данного цикла = 1 + 6* сумма(k)по k от 2 до n, где n - цикл.
            currentElements+= 6*hexCycle;
            hexCycle+=1;
        }
        if (currentElements != n) {
            return "invalid";
        }
        // размер - количество строк = количество точек
        final int hexSize = hexCycle* 2 - 1;
        final int hexSizeWithSpaces = hexSize* 2 - 1;
        // массив строк
        String[] hex = new String[hexSize];
        // цикл с середины фигуры до ее начала. Можно рисовать только половину так как фигура зеркальная.
        for (int i = hexCycle - 1; i >= 0; i--) {
            // находим сколько будет символов для этой строки вместе с пробелами
            int rowSizeWithSpaces = (hexCycle + i)*2 - 1;
            // находим какой отступ нужно будет сделать слева и справа для этой строки
            int spaceInLeft = (hexSizeWithSpaces - rowSizeWithSpaces)/2;
            // рисуем строку - повторяем точки определенное количество раз, и ставим между ними пробелы. добавляем слева и срава отступы.
            hex[i] = " ".repeat(spaceInLeft) + "o".repeat(hexCycle + i).replaceAll(".(?=.)", "$0 ") + " ".repeat(spaceInLeft);
            // если строка не на середине, то рисуем зеркальную строку
            if (i <= hexCycle - 1) {
                hex[hexSize - i - 1] = hex[i];
            }
        }
        // преобразуем массив в строку
        StringJoiner joiner = new StringJoiner("\n");
        for (String h: hex) {
            joiner.add(h);
        }
        return joiner.toString();
    }


}
