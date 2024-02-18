import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Создайте генератор текстов и сгенерируйте набор из 100 000 текстов, используя код
 * из описания задачи.                                                                 +
 * Заведите в статических полях три счётчика — по одному для длин 3, 4 и 5.             +
 * Заведите три потока — по одному на каждый критерий «красоты» слова.
 * Каждый поток проверяет все тексты на «красоту» и увеличивает счётчик нужной длины,
 * если текст соответствует критериям.
 * После окончания работы всех потоков выведите результаты на экран.
 * <p>
 * 1 сгенерированное слово является палиндромом, т. е. читается одинаково как слева направо,
 * так и справа налево, например, abba;
 * 2 сгенерированное слово состоит из одной и той же буквы, например, aaa;
 * 3 буквы в слове идут по возрастанию: сначала все a (при наличии), затем все b (при наличии),
 * затем все c и т. д. Например, aaccc.
 * Вы хотите подсчитать, сколько «красивых» слов встречается среди сгенерированных длиной 3, 4, 5,
 * для чего заводите три счётчика в статических полях. Проверка каждого критерия
 * должна осуществляться в отдельном потоке.
 * После завершения всех трёх потоков выведите сообщение вида:
 * <p>
 * Красивых слов с длиной 3: 100 шт
 * Красивых слов с длиной 4: 104 шт
 * Красивых слов с длиной 5: 90 шт
 */

public class Main {
    static AtomicInteger length_3 = new AtomicInteger(0);
    static AtomicInteger length_4 = new AtomicInteger(0);
    static AtomicInteger length_5 = new AtomicInteger(0);

    static String[] texts = new String[100_000];
    static Runnable wordSearch_3 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < texts.length; i++) {
                if (isWordPalindrome(texts[i]) || letterGrowth(texts[i])) {
                    if (texts[i].length() == 3) {
                        length_3.addAndGet(1);
                        //    System.out.println(texts[i] + " " + length_3);
                    }
                }
            }
        }
    };
    static Runnable wordSearch_4 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < texts.length; i++) {
                if (isWordPalindrome(texts[i]) || letterGrowth(texts[i])) {
                    if (texts[i].length() == 4) {
                        length_4.addAndGet(1);
                        // System.out.println(texts[i] + " " + length_4);
                    }
                }
            }
        }
    };
    static Runnable wordSearch_5 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < texts.length; i++) {
                if (isWordPalindrome(texts[i]) || letterGrowth(texts[i])) {
                    if (texts[i].length() == 5) {
                        length_5.addAndGet(1);
                        // System.out.println(texts[i] + " " + length_5);
                    }
                }
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        arrayFilling();
        System.out.println();
        Thread thread_3 = new Thread(wordSearch_3);
        Thread thread_4 = new Thread(wordSearch_4);
        Thread thread_5 = new Thread(wordSearch_5);
        thread_3.start();
        thread_4.start();
        thread_5.start();
        thread_3.join();
        thread_4.join();
        thread_5.join();
        System.out.print(" Красивых слов с длиной 3: " + length_3 + " \n");
        System.out.print(" Красивых слов с длиной 4: " + length_4 + " \n");
        System.out.print(" Красивых слов с длиной 5: " + length_5 + " \n");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void arrayFilling() {
        Random random = new Random();
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
    }

    private static boolean isWordPalindrome(String word) {
        var chars = word.toCharArray();
        var left = 0; // индекс первого символа
        var right = chars.length - 1; // индекс последнего символа
        while (left < right) { // пока не дошли до середины слова
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean letterGrowth(String word) {
        if (word == null || word.length() < 2) {
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) < word.charAt(i - 1))
                return false;
        }
        return true;
    }
}
