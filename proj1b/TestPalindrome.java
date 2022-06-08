import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("cac"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("abbbba"));
        assertTrue(palindrome.isPalindrome("abbbaabbba"));
        assertFalse(palindrome.isPalindrome("car"));
        assertFalse(palindrome.isPalindrome("ab"));
        assertFalse(palindrome.isPalindrome("test"));
        assertFalse(palindrome.isPalindrome("idontcare"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        assertTrue(palindrome.isPalindrome("cad", offByOne));
        assertTrue(palindrome.isPalindrome("cab", offByOne));
        assertTrue(palindrome.isPalindrome("abdcb", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertFalse(palindrome.isPalindrome("aba", offByOne));
        assertFalse(palindrome.isPalindrome("abbbbba", offByOne));
        assertFalse(palindrome.isPalindrome("adfgda", offByOne));
    }
}
