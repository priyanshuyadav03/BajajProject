package com.bajaj.bfhl.service.impl;

import com.bajaj.bfhl.dto.RequestDTO;
import com.bajaj.bfhl.dto.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
    }

    @Nested
    @DisplayName("Mixed Input Tests")
    class MixedInputTests {

        @Test
        @DisplayName("Should process mixed data with numbers, alphabets, and special characters")
        void testMixedInput() {
            RequestDTO request = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            assertEquals("priyanshu_yadav_11042005", response.getUserId());
            assertEquals("ypriyanshu5714@gmail.com", response.getEmail());
            assertEquals("0827CS231201", response.getRollNumber());

            assertEquals(List.of("1"), response.getOddNumbers());
            assertEquals(List.of("334", "4"), response.getEvenNumbers());
            assertEquals(List.of("A", "R"), response.getAlphabets());
            assertEquals(List.of("$"), response.getSpecialCharacters());
            assertEquals("339", response.getSum());
        }

        @Test
        @DisplayName("Should handle data with negative numbers")
        void testNegativeNumbers() {
            RequestDTO request = new RequestDTO(Arrays.asList("-3", "2", "a"));
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            assertEquals(List.of("-3"), response.getOddNumbers());
            assertEquals(List.of("2"), response.getEvenNumbers());
            assertEquals("-1", response.getSum());
        }
    }

    @Nested
    @DisplayName("Numbers Only Tests")
    class NumbersOnlyTests {

        @Test
        @DisplayName("Should process only odd numbers")
        void testOnlyOddNumbers() {
            RequestDTO request = new RequestDTO(Arrays.asList("1", "3", "5", "7"));
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            assertEquals(List.of("1", "3", "5", "7"), response.getOddNumbers());
            assertTrue(response.getEvenNumbers().isEmpty());
            assertTrue(response.getAlphabets().isEmpty());
            assertTrue(response.getSpecialCharacters().isEmpty());
            assertEquals("16", response.getSum());
            assertEquals("", response.getConcatString());
        }

        @Test
        @DisplayName("Should process only even numbers")
        void testOnlyEvenNumbers() {
            RequestDTO request = new RequestDTO(Arrays.asList("2", "4", "6", "100"));
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            assertTrue(response.getOddNumbers().isEmpty());
            assertEquals(List.of("2", "4", "6", "100"), response.getEvenNumbers());
            assertEquals("112", response.getSum());
        }

        @Test
        @DisplayName("Should handle zero as even")
        void testZeroIsEven() {
            RequestDTO request = new RequestDTO(List.of("0"));
            ResponseDTO response = service.processData(request);

            assertTrue(response.getOddNumbers().isEmpty());
            assertEquals(List.of("0"), response.getEvenNumbers());
            assertEquals("0", response.getSum());
        }
    }

    @Nested
    @DisplayName("Alphabets Only Tests")
    class AlphabetsOnlyTests {

        @Test
        @DisplayName("Should convert alphabets to uppercase")
        void testAlphabetsToUppercase() {
            RequestDTO request = new RequestDTO(Arrays.asList("a", "b", "c"));
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            assertEquals(List.of("A", "B", "C"), response.getAlphabets());
            assertTrue(response.getOddNumbers().isEmpty());
            assertTrue(response.getEvenNumbers().isEmpty());
            assertEquals("0", response.getSum());
        }

        @Test
        @DisplayName("Should handle multi-character alphabetic strings")
        void testMultiCharAlphabets() {
            RequestDTO request = new RequestDTO(Arrays.asList("A", "ABCD", "DOE"));
            ResponseDTO response = service.processData(request);

            assertEquals(List.of("A", "ABCD", "DOE"), response.getAlphabets());
            assertEquals("EoDdCbAa", response.getConcatString());
        }
    }

    @Nested
    @DisplayName("Special Characters Only Tests")
    class SpecialCharactersTests {

        @Test
        @DisplayName("Should detect special characters")
        void testSpecialCharacters() {
            RequestDTO request = new RequestDTO(Arrays.asList("$", "@", "#", "!"));
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            assertEquals(List.of("$", "@", "#", "!"), response.getSpecialCharacters());
            assertTrue(response.getOddNumbers().isEmpty());
            assertTrue(response.getEvenNumbers().isEmpty());
            assertTrue(response.getAlphabets().isEmpty());
            assertEquals("0", response.getSum());
            assertEquals("", response.getConcatString());
        }

        @Test
        @DisplayName("Should treat alphanumeric mix as special character")
        void testAlphanumericMix() {
            RequestDTO request = new RequestDTO(Arrays.asList("a1", "2b"));
            ResponseDTO response = service.processData(request);

            assertEquals(List.of("a1", "2b"), response.getSpecialCharacters());
            assertTrue(response.getAlphabets().isEmpty());
            assertTrue(response.getOddNumbers().isEmpty());
        }
    }

    @Nested
    @DisplayName("Empty and Null Input Tests")
    class EmptyAndNullInputTests {

        @Test
        @DisplayName("Should handle empty data list")
        void testEmptyData() {
            RequestDTO request = new RequestDTO(new ArrayList<>());
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            assertTrue(response.getOddNumbers().isEmpty());
            assertTrue(response.getEvenNumbers().isEmpty());
            assertTrue(response.getAlphabets().isEmpty());
            assertTrue(response.getSpecialCharacters().isEmpty());
            assertEquals("0", response.getSum());
            assertEquals("", response.getConcatString());
        }

        @Test
        @DisplayName("Should handle null data list gracefully")
        void testNullData() {
            RequestDTO request = new RequestDTO(null);
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            assertTrue(response.getOddNumbers().isEmpty());
            assertTrue(response.getEvenNumbers().isEmpty());
            assertTrue(response.getAlphabets().isEmpty());
            assertTrue(response.getSpecialCharacters().isEmpty());
            assertEquals("0", response.getSum());
            assertEquals("", response.getConcatString());
        }

        @Test
        @DisplayName("Should skip null and empty items within data list")
        void testNullAndEmptyItems() {
            List<String> data = new ArrayList<>();
            data.add(null);
            data.add("");
            data.add("5");
            data.add("a");
            RequestDTO request = new RequestDTO(data);
            ResponseDTO response = service.processData(request);

            assertEquals(List.of("5"), response.getOddNumbers());
            assertEquals(List.of("A"), response.getAlphabets());
        }
    }

    @Nested
    @DisplayName("Large Input Tests")
    class LargeInputTests {

        @Test
        @DisplayName("Should handle large number of items")
        void testLargeInput() {
            List<String> data = new ArrayList<>();
            for (int i = 1; i <= 1000; i++) {
                data.add(String.valueOf(i));
            }
            RequestDTO request = new RequestDTO(data);
            ResponseDTO response = service.processData(request);

            assertTrue(response.isSuccess());
            // Sum of 1..1000 = 500500
            assertEquals("500500", response.getSum());
            assertEquals(500, response.getOddNumbers().size());
            assertEquals(500, response.getEvenNumbers().size());
        }

        @Test
        @DisplayName("Should handle very large numbers")
        void testVeryLargeNumbers() {
            RequestDTO request = new RequestDTO(Arrays.asList("99999999999999999999", "1"));
            ResponseDTO response = service.processData(request);

            assertEquals("100000000000000000000", response.getSum());
            assertEquals(List.of("1"), response.getOddNumbers());
            assertEquals(List.of("99999999999999999999"), response.getEvenNumbers());
        }
    }

    @Nested
    @DisplayName("Concat String Tests")
    class ConcatStringTests {

        @Test
        @DisplayName("Should correctly build concat_string with alternating caps")
        void testConcatStringAlternatingCaps() {
            // Input: "A", "ABCD", "DOE" → combined: "AABCDDOE" → reversed: "EODDCBAA" → alt caps: "EoDdCbAa"
            String result = service.buildConcatString("AABCDDOE");
            assertEquals("EoDdCbAa", result);
        }

        @Test
        @DisplayName("Should return empty string for empty input")
        void testConcatStringEmpty() {
            assertEquals("", service.buildConcatString(""));
        }

        @Test
        @DisplayName("Should return empty string for null input")
        void testConcatStringNull() {
            assertEquals("", service.buildConcatString(null));
        }

        @Test
        @DisplayName("Should handle single character")
        void testConcatStringSingleChar() {
            String result = service.buildConcatString("a");
            assertEquals("A", result);
        }

        @Test
        @DisplayName("Should handle lowercase input correctly")
        void testConcatStringLowercase() {
            // combined: "ab" → reversed: "ba" → alt caps: "Ba"
            String result = service.buildConcatString("ab");
            assertEquals("Ba", result);
        }
    }

    @Nested
    @DisplayName("User Info Tests")
    class UserInfoTests {

        @Test
        @DisplayName("Should always return correct user_id, email, and roll_number")
        void testUserInfo() {
            RequestDTO request = new RequestDTO(List.of("1"));
            ResponseDTO response = service.processData(request);

            assertEquals("priyanshu_yadav_11042005", response.getUserId());
            assertEquals("ypriyanshu5714@gmail.com", response.getEmail());
            assertEquals("0827CS231201", response.getRollNumber());
        }
    }
}
