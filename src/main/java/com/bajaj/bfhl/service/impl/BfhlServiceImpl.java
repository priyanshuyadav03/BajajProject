package com.bajaj.bfhl.service.impl;

import com.bajaj.bfhl.dto.RequestDTO;
import com.bajaj.bfhl.dto.ResponseDTO;
import com.bajaj.bfhl.service.BfhlService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    private static final String USER_ID = "priyanshu_yadav_11042005";
    private static final String EMAIL = "ypriyanshu5714@gmail.com";
    private static final String ROLL_NUMBER = "0827CS231201";

    @Override
    public ResponseDTO processData(RequestDTO requestDTO) {
        List<String> data = requestDTO.getData();

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        BigInteger sum = BigInteger.ZERO;

        if (data == null || data.isEmpty()) {
            return buildResponse(oddNumbers, evenNumbers, alphabets, specialCharacters, sum.toString(), "");
        }

        StringBuilder alphabetChars = new StringBuilder();

        for (String item : data) {
            if (item == null || item.isEmpty()) {
                continue;
            }

            if (isNumeric(item)) {
                // It's a number
                BigInteger number = new BigInteger(item);
                sum = sum.add(number);

                if (number.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabetic(item)) {
                // It's an alphabetic string
                alphabets.add(item.toUpperCase());
                alphabetChars.append(item);
            } else if (isAlphanumericMix(item)) {
                // Mixed alphanumeric — not purely numeric or alphabetic — treat as special character
                specialCharacters.add(item);
            } else {
                // Special character(s)
                specialCharacters.add(item);
            }
        }

        String concatString = buildConcatString(alphabetChars.toString());

        return buildResponse(oddNumbers, evenNumbers, alphabets, specialCharacters, sum.toString(), concatString);
    }

    /**
     * Checks if a string represents a valid integer (positive or negative).
     */
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        int start = 0;
        if (str.charAt(0) == '-' || str.charAt(0) == '+') {
            if (str.length() == 1) {
                return false;
            }
            start = 1;
        }
        for (int i = start; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a string contains only alphabetic characters.
     */
    private boolean isAlphabetic(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a string is an alphanumeric mix (contains both letters and digits).
     */
    private boolean isAlphanumericMix(String str) {
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) hasLetter = true;
            if (Character.isDigit(str.charAt(i))) hasDigit = true;
        }
        return hasLetter && hasDigit;
    }

    /**
     * Builds the concat_string per spec:
     * 1. Extract all alphabetic characters from all alphabetic entries (already joined)
     * 2. Reverse the string
     * 3. Apply alternating capitalization starting with uppercase
     */
    String buildConcatString(String combined) {
        if (combined == null || combined.isEmpty()) {
            return "";
        }

        // Reverse the combined string
        String reversed = new StringBuilder(combined).reverse().toString();

        // Apply alternating capitalization: index 0 = uppercase, index 1 = lowercase, ...
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }

        return result.toString();
    }

    private ResponseDTO buildResponse(List<String> oddNumbers, List<String> evenNumbers,
                                      List<String> alphabets, List<String> specialCharacters,
                                      String sum, String concatString) {
        return ResponseDTO.builder()
                .isSuccess(true)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .oddNumbers(Collections.unmodifiableList(oddNumbers))
                .evenNumbers(Collections.unmodifiableList(evenNumbers))
                .alphabets(Collections.unmodifiableList(alphabets))
                .specialCharacters(Collections.unmodifiableList(specialCharacters))
                .sum(sum)
                .concatString(concatString)
                .build();
    }
}
