public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    /**
     * decide if the given word is palindrome.
     */
    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        return isPHelper(d.removeFirst(), d.removeLast(), d);
    }

    private boolean isPHelper(Character front, Character end, Deque<Character> deque) {
        if (front == null || end == null) {
            return true;
        }
        if (front != end) {
            return false;
        }
        return isPHelper(deque.removeFirst(), deque.removeLast(), deque);
    }

    /**
     * overloaded isPalindrome, decide if the given word is palindrome
     * according to the given CharacterComparator.
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        return isPHelper(d.removeFirst(), d.removeLast(), d, cc);
    }

    private boolean isPHelper(Character front, Character end,
                              Deque<Character> deque, CharacterComparator cc) {
        if (front == null || end == null) {
            return true;
        }
        if (!cc.equalChars(front, end)) {
            return false;
        }
        return isPHelper(deque.removeFirst(), deque.removeLast(), deque, cc);
    }
}
