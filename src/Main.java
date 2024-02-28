import java.util.Random;

/**
 * Создайте генератор текстов и сгенерируйте набор из 100 000 текстов, используя код
 * из описания задачи.                                                                 +
 * Заведите в статических полях три счётчика — по одному для длин 3, 4 и 5.             +
 * Заведите три потока — по одному на каждый критерий «красоты» слова.!!!!!!!****
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

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = SpecialClass.generateText("abc", 3 + random.nextInt(3));
        }

        Runnable wordSearch_3 = () -> {
            for (String text : texts) {
                if (SpecialClass.isPolindrome(text))
                    SpecialClass.incrementCounter(text.length());
            }
        };
        Thread thread_3 = new Thread(wordSearch_3);
        thread_3.start();

        Runnable wordSearch_4 = () -> {
            for (String text : texts) {
                if (SpecialClass.isSameChar(text))
                    SpecialClass.incrementCounter(text.length());
            }
        };
        Thread thread_4 = new Thread(wordSearch_4);
        thread_4.start();

        Runnable wordSearch_5 = () -> {
            for (String text : texts) {
                if (SpecialClass.isAscendingOrder(text) && SpecialClass.isSameChar(text))
                    SpecialClass.incrementCounter(text.length());
            }
        };
        Thread thread_5 = new Thread(wordSearch_5);
        thread_5.start();

        thread_3.join();
        thread_4.join();
        thread_5.join();

        System.out.print(" Красивых слов с длиной 3: " + SpecialClass.length_3 + " \n");
        System.out.print(" Красивых слов с длиной 4: " + SpecialClass.length_4 + " \n");
        System.out.print(" Красивых слов с длиной 5: " + SpecialClass.length_5 + " \n");

    }
}
