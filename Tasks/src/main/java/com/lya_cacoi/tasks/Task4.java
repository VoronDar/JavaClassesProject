package com.lya_cacoi.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {

    public static void main(String[] args) {
        System.out.println("-----1-----");
        System.out.println(Arrays.toString(essay(10, 7, "hello my name is Bessie and this is my essay")));
        System.out.println("-----2-----");
        System.out.println(Arrays.toString(split("()()()")));
        System.out.println(Arrays.toString(split("((()))")));
        System.out.println(Arrays.toString(split("((()))(())()()(()())")));
        System.out.println(Arrays.toString(split("((())())(()(()()))")));
        System.out.println("-----3-----");
        System.out.println(toCamelCase("hello_edabit"));
        System.out.println(toSnakeCase("helloEdabit"));
        System.out.println(toCamelCase("is_modal_open"));
        System.out.println(toSnakeCase("getColor"));
        System.out.println("-----4-----");
        System.out.println(overtime(9, 17, 30, 1.5));
        System.out.println(overtime(16, 18, 30, 1.8));
        System.out.println(overtime(13.25, 15, 30, 1.5));
        System.out.println("-----5-----");
        System.out.println(bmi("205 pounds", "73 inches"));
        System.out.println(bmi("55 kilos", "1.63 meters"));
        System.out.println(bmi("154 pounds", "2 meters"));
        System.out.println("----6------");
        System.out.println(bugger(39));
        System.out.println(bugger(999));
        System.out.println(bugger(4));
        System.out.println("----7------");
        System.out.println(toStartShorthand("abbccc"));
        System.out.println(toStartShorthand("77777geff"));
        System.out.println(toStartShorthand("abc"));
        System.out.println(toStartShorthand(""));
        System.out.println("----8------");
        System.out.println(doesRhyme("Sam I am!", "Green eggs and ham."));
        System.out.println(doesRhyme("Sam I am!", "Green eggs and HAM."));
        System.out.println(doesRhyme("You are off to the races", "a splendid day."));
        System.out.println(doesRhyme("and frequently do?", "you gotta move."));
        System.out.println("----9------");
        System.out.println(trouble(451999277, 41177722899L));
        System.out.println(trouble(1222345, 12345));
        System.out.println(trouble(666789, 12345667));
        System.out.println(trouble(33789, 12345337));
        System.out.println("----10------");
        System.out.println(countUniqueBooks("AZYWABBCATTTA", 'A'));
        System.out.println(countUniqueBooks("$AA$BBCATT$C$$B$", '$'));
        System.out.println(countUniqueBooks("ZZABCDEF", 'Z'));
    }

    /**
     * 1 задание
     * @return отформатированную строку в список строк так, чтобы в каждой строке было не больше символов чем charsInRow
     * параметр wordsCount в условии задачи есть но ни на что не влияет.
     */
    private static String[] essay(int wordsCount, int charsInRow, String initialString) {
        // делим строку на слова
        String[] strings = initialString.split(" ");
        // заводим массив строк
        ArrayList<String> rows = new ArrayList<>();
        // билдер для текущей строки
        StringJoiner currentRow = new StringJoiner(" ");
        for (String currentStr : strings) {
            // если в строку нельзя добавить слово - закрываем строку.
            if (currentStr.length() + currentRow.length() > charsInRow) {
                rows.add(currentRow.toString());
                currentRow = new StringJoiner(" ");
            }
            currentRow.add(currentStr);
        }
        if (currentRow.length() != 0) {
            rows.add(currentRow.toString());
        }
        return rows.toArray(new String[]{});
    }

    /**
     * 2 задание:
     * @return из строки со скобками выделяются кластеры скобок и возвращаются в массиве
     * */
    private static String[] split(String s) {
        // добавляем символы в стек по 1
        // если в стеке последний символ ( а новый ), то значит скобка закрылась
        // и символ ( можно убрать
        // если стек опустел, то одна большая скобка завершена
        Queue<Character> stack = new LinkedList<>();
        int startOfClaster = 0;
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if (stack.isEmpty() && i != 0) {
                // если в стеке больше нет символов - закрываем строку со скобками
                strings.add(s.substring(startOfClaster, i));
                startOfClaster = i;
            }
            if (stack.peek() != null && stack.peek() == '(' && currentChar == ')') {
                stack.poll();
            } else {
                stack.add(currentChar);
            }
        }
        strings.add(s.substring(startOfClaster));
        return strings.toArray(new String[]{});
    }

    /**
     * 3 задание:
     * @return модифицированная строка переведенная из snake_case в camel_case.
     * */
    private static String toCamelCase(String s) {
        // заменяем все комбинации _w на W через регулярку
        Pattern pattern = Pattern.compile("_\\w");
        return pattern.matcher(s).replaceAll(matchResult -> matchResult.group().substring(1).toUpperCase(Locale.ROOT));
    }

    /**
     * 3 задание:
     * @return модифицированная строка переведенная из camel_case в snake_case.
     * */
    private static String toSnakeCase(String s) {
        // находим в строке капитализированные буквы, по ним определяем, что это новое слово, и добавляем его в билдер
        StringJoiner builder = new StringJoiner("_");
        int lastPosition = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                builder.add(s.substring(lastPosition, i).toLowerCase(Locale.ROOT));
                lastPosition = i;
            }
        }
        builder.add(s.substring(lastPosition).toLowerCase(Locale.ROOT));
        return builder.toString();
    }

    /**
     * 4 задание
     * @return оплата работнику в соответствии с отработанными часами
     * */
    private static String overtime(double start, double end, double payment, double multiplier) {
        final int WORK_END = 17;
        double overtimeHours;
        double commonHours;
        // учтем кейс, что работник проработал часть следующего дня (после 00:00)
        // предполагаем что более 24 часов подряд человек не работает.
        // если вдруг время окончания работы < времении ее начала, значит новая работа пошла уже на следующий день
        if (end <= start) {
            overtimeHours = end + 24 - WORK_END;
            commonHours = WORK_END - start;
        }
        // если были овертаймы
        else if (end > WORK_END) {
            overtimeHours = end - WORK_END;
            commonHours = WORK_END - start;
        }
        // если не было овертаймов
        else {
            commonHours = end - start;
            overtimeHours = 0;
        }
        // расчитываем зарплату и форматируем
        double pay = commonHours * payment + overtimeHours * payment * multiplier;
        return String.format(Locale.ENGLISH, "$ %.2f", pay);
    }

    /**
     * 5 задание
     * @return ИМТ округленный до десятой
     * */
    private static String bmi(String weight, String height) {
        // регулярка, которая отлавливает числа без дроби и с дробью.
        Pattern doublePattern = Pattern.compile("\\d+\\.?\\d*");
        Matcher matcher = doublePattern.matcher(weight);

        // ищем в строке с весом число
        double weightNumber = 0;
        if (matcher.find()) {
            weightNumber = Double.parseDouble(matcher.group());
        }
        // ищем в строке с ростом число
        matcher.reset(height);
        double heightHumber = 0;
        if (matcher.find()) {
            heightHumber = Double.parseDouble(matcher.group());
        }

        // если вес в фунтах, конвертируем
        if (weight.contains("pound")) {
            weightNumber /= 2.205;
        }
        // если высота в дюймах, конвертируем
        if (height.contains("inches")) {
            heightHumber /= 39.37;
        }
        // определяем и классифицируем ИМТ
        double bmi = weightNumber / (heightHumber * heightHumber);
        String type;
        if (bmi >= 25) {
            type = "Overweight";
        } else if (bmi < 18.5) {
            type = "Underweight";
        } else {
            type = "Normal weight";
        }
        return String.format(Locale.ENGLISH, "%.1f, %s", bmi, type);
    }

    /**
     * 6 задание
     * @return мультипликативное постоянство (количество раз, которое нужно умножить цифры числа чтобы получить 1 цифру
     * */
    private static int bugger(int number) {
        // используем рекурсию. Если текущее число < 10, то можно перестать умножать
        if (number < 10) {
            return 0;
        }
        // получаем произведение цифр числа
        int multiplies = 1;
        for (char c : ("" + number).toCharArray()) {
            multiplies *= Integer.parseInt(String.valueOf(c));
        }
        // повторяем алгоритм для полученного произведения
        return 1 + bugger(multiplies);
    }

    /**
     * 7 задание
     * @return модифицированная строка, в которой повторения символов заменяются на фрагменты вида "a*n"
     * */
    private static String toStartShorthand(String s) {
        StringBuilder builder = new StringBuilder();
        // текущее количество повторений 1 символа
        int currentLength = 0;
        // последний встреченный символ
        Character lastChar = null;
        for (char c : s.toCharArray()) {
            // если текущий символ не равен предыдущему, то мы можем четко сказать, что мы знаем количество повторений для предыдущего символа
            if (lastChar != null && lastChar != c) {
                builder.append(lastChar);
                // если повторений было > 1 то пишем количество повторений
                if (currentLength > 1) {
                    builder.append("*").append(currentLength);
                }
                currentLength = 0;
            }

            currentLength += 1;
            lastChar = c;
        }
        // добавляем последние символы
        if (currentLength != 0) {
            builder.append(lastChar);
            if (currentLength > 1) {
                builder.append("*").append(currentLength);
            }
        }
        return builder.toString();
    }

    /**
     * 8 задание:
     * @return true если гласные в последнем слова предложений одинаковые и идут в одинаковом порядке
     * */
    private static Boolean doesRhyme(String s1, String s2) {
        // регулярка, которая находит в строке последнее слово и заменяет только его
        String s1LastWord = s1.replaceAll("^.*?(\\w+)\\W*$", "$1").toLowerCase(Locale.ROOT);
        String s2LastWord = s2.replaceAll("^.*?(\\w+)\\W*$", "$1").toLowerCase(Locale.ROOT);
        // удаляем все согласные из последних слов
        String s1CharsToRhyme = s1LastWord.replaceAll("[qwrtiopsdfghjklzxcvbnm]", "");
        String s2CharsToRhyme = s2LastWord.replaceAll("[qwrtiopsdfghjklzxcvbnm]", "");
        return s2CharsToRhyme.equals(s1CharsToRhyme);
    }

    /**
     * 9 задание:
     * @return true если есть такая цифра, которая ровно 3 раза повторяется в d1 и 2 раза повторяется в d2
     * */
    private static boolean trouble(long d1, long d2) {
        class RepeatCounter {
            // возвращает мапу в которой ключ - цифра, а значение - количество её повторений в числе
            Map<Integer, Integer> getRepeatsCount(long d) {
                Map<Integer, Integer> repeats = new HashMap<>();
                while (d > 0) {
                    // смотрим каждую цифру числа и увеличиваем значение на 1 в мапе для этой цифры
                    int currentInteger = (int) (d % 10);
                    repeats.merge(currentInteger, 1, Integer::sum);
                    d /= 10;
                }
                return repeats;
            }
        }
        RepeatCounter repeatCounter = new RepeatCounter();
        // получаем мапы с количеством повторений каждой цифры числа
        Map<Integer, Integer> repeatsInD1 = repeatCounter.getRepeatsCount(d1);
        Map<Integer, Integer> repeatsInD2 = repeatCounter.getRepeatsCount(d2);
        // находим среди мап такой ключ, который в 1 мапе имеет значение 3, а во второй 2
        for (Map.Entry<Integer, Integer> pair : repeatsInD1.entrySet()) {
            if (pair.getValue() == 3 && repeatsInD2.get(pair.getKey()) == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * 10 задание:
     * @return количество уникальных символов, встречающихся в открытых книгах
     * */
    private static int countUniqueBooks(String chars, char divider) {
        // сет чтобы избежать дублирования
        Set<Character> booksSet = new HashSet<>();
        boolean bookOpened = false;
        for (char c : chars.toCharArray()) {
            // если книга открыта - добавляем символ в сет. Иначе не добавляем.
            if (c == divider) {
                bookOpened = !bookOpened;
            } else if (bookOpened) {
                booksSet.add(c);
            }
        }
        return booksSet.size();
    }

}
