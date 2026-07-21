package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains coding problems and associated unit tests that are solved using
 * the Sliding Window pattern.
 * This pattern is typically used for tracking subarrays or substrings, allowing
 * us to solve problems in linear time where a naive approach might take quadratic time.
 */
public class Sliding_Window_Problems extends TestBase {

    /**
     * Parameterized test to verify that the substringAnagrams method correctly counts
     * the number of anagram occurrences of string t within string s.
     *
     * @param s        the source string to search within
     * @param t        the target string (anagram pattern) to look for
     * @param expected the expected number of anagram occurrences found
     */
    @ParameterizedTest
    @CsvSource({
            "'caabab', 'aba', 2",   // Sample example
            "'abc', 'abcd', 0",     // Edge Case: t is longer than s
            "'abc', 'abc', 1",      // Edge Case: s and t are identical
            "'xyz', 'abc', 0",      // Edge Case: No anagrams found
            "'aaaaa', 'aa', 4",     // Edge Case: Multiple overlapping anagrams
            "'a', 'a', 1",          // Edge Case: Single character strings matching
            "'a', 'b', 0"           // Edge Case: Single character strings mismatching
    })
    void testSubstringAnagrams(String s, String t, int expected) {
        assertEquals(expected, substringAnagrams(s, t));
    }

    /**
     * Finds and counts all occurrences of any anagram of string t as a substring of string s
     * using the Sliding Window pattern by comparing character frequency arrays.
     *
     * Algorithmic Details:
     * - Time Complexity: O(N * K) where N is the length of s and K is the size of the character set (26).
     *   At each sliding window step, we compare the entire character count array of size 26.
     * - Space Complexity: O(1) auxiliary space (specifically O(26) = O(1) space for character frequency arrays).
     *
     * How it works:
     * - First, build a frequency array of characters in string t.
     * - Maintain a sliding window of size equal to the length of string t over string s.
     * - Shift the window one character at a time by moving the right pointer.
     * - Keep track of character frequencies in the current window of s.
     * - Once the window reaches size equal to length of t, check if character frequencies match.
     *   If they do, we've found an anagram, so increment the count.
     * - Before expanding the window further, remove the leftmost character from the window frequency count
     *   and shrink the window from the left by moving the left pointer.
     *
     * @param s the source string to search within
     * @param t the target string (anagram pattern) to look for
     * @return the total number of anagram occurrences of t in s
     */
    public int substringAnagrams(String s, String t) {
        int[] tFreqs = new int[26];
        int[] sFreqs = new int[26];
        int count = 0;
        
        // Populate frequency array for target string t
        for (int i = 0; i < t.length(); i++) tFreqs[t.charAt(i) - 'a']++;

        int left = 0, right = 0;
        // Expand the window with the right pointer
        while(right < s.length()) {
            sFreqs[s.charAt(right) - 'a']++;
            
            // When window size equals the length of t, check for an anagram match
            if(right - left + 1 == t.length()) {
                if(isSameArray(sFreqs, tFreqs)) count++;

                // Shrink the window from the left before shifting right
                sFreqs[s.charAt(left) - 'a']--;
                left++;
            }
            right++;
        }
        return count;
    }

    /**
     * Compares two character frequency arrays to check if they are identical.
     *
     * @param a the first frequency array
     * @param b the second frequency array
     * @return true if the arrays have the same character counts, false otherwise
     */
    boolean isSameArray(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }

    /**
     * Parameterized test to verify that the substringAnagramsOptimized method correctly counts
     * the number of anagram occurrences of string t within string s.
     *
     * @param s        the source string to search within
     * @param t        the target string (anagram pattern) to look for
     * @param expected the expected number of anagram occurrences found
     */
    @ParameterizedTest
    @CsvSource({
            "'caabab', 'aba', 2",   // Sample example
            "'abc', 'abcd', 0",     // Edge Case: t is longer than s
            "'abc', 'abc', 1",      // Edge Case: s and t are identical
            "'xyz', 'abc', 0",      // Edge Case: No anagrams found
            "'aaaaa', 'aa', 4",     // Edge Case: Multiple overlapping anagrams
            "'a', 'a', 1",          // Edge Case: Single character strings matching
            "'a', 'b', 0"           // Edge Case: Single character strings mismatching
    })
    void testSubstringAnagramsOptimized(String s, String t, int expected) {
        assertEquals(expected, substringAnagramsOptimized(s, t));
    }

    /**
     * Finds and counts all occurrences of any anagram of string t as a substring of string s
     * using the Sliding Window pattern optimized with a match counter.
     *
     * Algorithmic Details:
     * - Time Complexity: O(N + M) where N is the length of s and M is the length of t.
     *   Instead of comparing 26-element arrays at each window step, we maintain a `matches`
     *   counter in O(1) time.
     * - Space Complexity: O(1) auxiliary space (O(26) = O(1) space for character frequency arrays).
     *
     * How it works:
     * - If s is shorter than t, it's impossible to contain an anagram of t, so return 0.
     * - Populate the frequency array for string t.
     * - Count how many characters initially match their frequencies between sFreqs and tFreqs.
     *   Since all counts in sFreqs start at 0, any character not present in t (count 0 in tFreqs)
     *   initially matches.
     * - Slide a window of size equal to t.length() over s.
     * - For each character entering/leaving the window, update its frequency in sFreqs and adjust
     *   the `matches` count accordingly in O(1) time.
     * - When the window size matches t.length() and `matches` is 26, increment the anagram count.
     *
     * @param s the source string to search within
     * @param t the target string (anagram pattern) to look for
     * @return the total number of anagram occurrences of t in s
     */
    public int substringAnagramsOptimized(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }

        int[] tFreqs = new int[26];
        int[] sFreqs = new int[26];
        int count = 0;
        
        // Populate frequency array for target string t
        for (int i = 0; i < t.length(); i++) {
            tFreqs[t.charAt(i) - 'a']++;
        }

        // Count how many characters initially match (those that do not appear in t)
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (tFreqs[i] == 0) {
                matches++;
            }
        }

        int left = 0, right = 0;
        // Expand the window with the right pointer
        while (right < s.length()) {
            int r = s.charAt(right) - 'a';
            sFreqs[r]++;
            if (sFreqs[r] == tFreqs[r]) {
                matches++;
            } else if (sFreqs[r] == tFreqs[r] + 1) {
                matches--;
            }
            
            // When window size equals the length of t, check for an anagram match
            if (right - left + 1 == t.length()) {
                if (matches == 26) {
                    count++;
                }

                // Shrink the window from the left before shifting right
                int l = s.charAt(left) - 'a';
                sFreqs[l]--;
                if (sFreqs[l] == tFreqs[l]) {
                    matches++;
                } else if (sFreqs[l] == tFreqs[l] - 1) {
                    matches--;
                }
                left++;
            }
            right++;
        }
        return count;
    }

    /**
     * Parameterized test to verify that the longestSubstringWithUniqueChars method correctly
     * calculates the length of the longest substring without repeating characters.
     *
     * @param s        the input string to evaluate
     * @param expected the expected length of the longest substring with unique characters
     */
    @ParameterizedTest
    @CsvSource({
            "abcba, 3",       // Sample example
            "'', 0",          // Empty string
            "a, 1",           // Single character
            "bbbbb, 1",       // All repeating characters
            "pwwkew, 3",      // Duplicate characters with non-adjacent repeats ("wke")
            "abcdefg, 7",     // All unique characters
            "aab, 2",         // Duplicate at the beginning
            "abb, 2",         // Duplicate at the end
            "tmmzuxt, 5"      // Complex string with multiple overlapping duplicates ("mzuxt")
    })
    void testLongestSubstringWithUniqueChars(String s, int expected) {
        int actual = longestSubstringWithUniqueChars(s);
        assertEquals(expected, actual);
    }

    /**
     * Finds the length of the longest substring containing all unique (non-repeating) characters
     * using the Dynamic Sliding Window pattern with character last-seen index tracking.
     *
     * Algorithmic Details:
     * - Time Complexity: O(N) where N is the length of string s. Each character is visited at most once by the right pointer,
     *   and the left pointer jumps directly using the `lastSeen` array without incremental step-by-step scanning.
     * - Space Complexity: O(1) auxiliary space (specifically O(26) = O(1) space for character index tracking array).
     *
     * How it works:
     * - Maintain an array `lastSeen` initialized to -1 to track the most recent 0-based index of each character.
     * - Iterate through string s using a `right` pointer to expand the sliding window.
     * - If the current character `c` was seen inside the active window (`lastSeen[c] >= left`),
     *   shrink the window by advancing `left` to `lastSeen[c] + 1` to eliminate the duplicate character.
     * - Record the current character's index in `lastSeen[c]`.
     * - Update `maxLen` with the current window length (`right - left + 1`).
     *
     * @param s the input string consisting of lowercase English letters
     * @return the length of the longest substring with all unique characters
     */
    public int longestSubstringWithUniqueChars(String s) {
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        int left = 0, maxLen = 0;

        // Expand the sliding window using the right pointer
        for (int right = 0; right < s.length(); right++) {
            int c = s.charAt(right) - 'a';
            // If character was seen within current window, jump left pointer past its previous occurrence
            if (lastSeen[c] >= left) {
                left = lastSeen[c] + 1;
            }
            // Update last seen index of character and recalculate max window length
            lastSeen[c] = right;
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}
