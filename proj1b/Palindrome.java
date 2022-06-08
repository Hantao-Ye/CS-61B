public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> ans = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            ans.addLast(word.charAt(i));
        }
        return ans;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        if (d.size() == 1) {
            return true;
        }

        while (d.size() >= 2) {
            Character head = d.removeFirst();
            Character tail = d.removeLast();
            if (head != tail) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        if (d.size() == 1) {
            return true;
        }

        while (d.size() >= 2) {
            Character head = d.removeFirst();
            Character tail = d.removeLast();
            if (!cc.equalChars(head, tail)) {
                return false;
            }
        }
        return true;
    }
}
