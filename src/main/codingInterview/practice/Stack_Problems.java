package main.codingInterview.practice;

import main.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stack_Problems extends TestBase {

    /*
     * Problem: Valid Parenthesis Expression
     * * Given a string 's' containing the characters '(', ')', '{', '}', '[', and ']',
     * determine if the input string is valid.
     * * An input string is valid if:
     * 1. Open brackets must be closed by the same type of brackets.
     * 2. Open brackets must be closed in the correct order (LIFO - Last In, First Out).
     * 3. Every close bracket has a corresponding open bracket of the same type.
     * * Examples:
     * Input: s = "([]{})"  -> Output: true
     * Input: s = "([]{)}"  -> Output: false
     * * Constraints:
     * - 0 <= s.length <= 10^4 (or equivalent length based on context)
     * - s consists of parentheses only: '()[]{}'
     */

    @ParameterizedTest
    @CsvSource({
            // Examples from problem description
            "'([]{})', true",
            "'([]{)}', false",

            // Edge cases: Simple valid/invalid
            "'()', true",
            "'()[]{}', true",
            "'(]', false",
            "'([)]', false",

            // Edge cases: Nested and complex expressions
            "'{[()]}', true",
            "'{[(])}', false",
            "'((()))', true",

            // Edge cases: Unbalanced lengths (extra opening/closing)
            "'(', false",
            "')', false",
            "'(()', false",
            "'())', false",
            "']}[', false",

            // Edge case: Empty string
            "'', true"
    })
    void testValidParenthesisExpression(String input, boolean expected) {
        assertEquals(expected, validParenthesisExpression(input));
    }

    public boolean validParenthesisExpression(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put('(',')');
        map.put('{','}');
        map.put('[',']');

        Stack<Character> stack = new Stack<>();

        for(char c: s.toCharArray()) {
            if(map.containsKey(c)) stack.push(c);
            else {
                if(!stack.isEmpty()) {
                    if(map.get(stack.pop()) != c) return false;
                } else return false;
            }
        }

        return stack.isEmpty();
    }
}
