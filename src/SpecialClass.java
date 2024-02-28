import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SpecialClass {
    public static AtomicInteger length_3 = new AtomicInteger();
    public static AtomicInteger length_4 = new AtomicInteger();
    public static AtomicInteger length_5 = new AtomicInteger();

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPolindrome(String text) {
        return text.contentEquals(new StringBuilder(text).reverse());
    }

    public static boolean isSameChar(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscendingOrder(String word) {
        if (word == null || word.length() < 2) {
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) < word.charAt(i - 1))
                return false;
        }
        return true;
//        for (int i = 0; i < text.length() ; i++) {
//            if (text.charAt(i) < text.charAt(i-1)){
//                return false;
//            }
//        }
//        return true;
    }

    public static void incrementCounter(int textLength) {
        if (textLength == 3) {
            length_3.getAndIncrement();
        } else if (textLength == 4) {
            length_4.getAndIncrement();
        } else {
            length_5.getAndIncrement();
        }
    }
}
