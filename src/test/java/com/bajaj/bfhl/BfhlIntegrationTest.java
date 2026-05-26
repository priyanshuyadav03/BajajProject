package com.bajaj.bfhl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BfhlIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Integration: POST /bfhl with mixed input")
    void testFullFlow_MixedInput() throws Exception {
        Map<String, Object> request = Map.of("data", List.of("a", "1", "334", "4", "R", "$"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.user_id").value("priyanshu_yadav_11042005"))
                .andExpect(jsonPath("$.email").value("ypriyanshu5714@gmail.com"))
                .andExpect(jsonPath("$.roll_number").value("0827CS231201"))
                .andExpect(jsonPath("$.odd_numbers.length()").value(1))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.even_numbers.length()").value(2))
                .andExpect(jsonPath("$.even_numbers[0]").value("334"))
                .andExpect(jsonPath("$.even_numbers[1]").value("4"))
                .andExpect(jsonPath("$.alphabets.length()").value(2))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("R"))
                .andExpect(jsonPath("$.special_characters.length()").value(1))
                .andExpect(jsonPath("$.special_characters[0]").value("$"))
                .andExpect(jsonPath("$.sum").value("339"));
    }

    @Test
    @DisplayName("Integration: POST /bfhl with concat_string logic")
    void testFullFlow_ConcatString() throws Exception {
        Map<String, Object> request = Map.of("data", List.of("A", "ABCD", "DOE"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concat_string").value("EoDdCbAa"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("ABCD"))
                .andExpect(jsonPath("$.alphabets[2]").value("DOE"));
    }

    @Test
    @DisplayName("Integration: POST /bfhl with only numbers")
    void testFullFlow_OnlyNumbers() throws Exception {
        Map<String, Object> request = Map.of("data", List.of("10", "20", "3"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.odd_numbers.length()").value(1))
                .andExpect(jsonPath("$.odd_numbers[0]").value("3"))
                .andExpect(jsonPath("$.even_numbers.length()").value(2))
                .andExpect(jsonPath("$.sum").value("33"))
                .andExpect(jsonPath("$.alphabets.length()").value(0))
                .andExpect(jsonPath("$.special_characters.length()").value(0))
                .andExpect(jsonPath("$.concat_string").value(""));
    }

    @Test
    @DisplayName("Integration: POST /bfhl with only special characters")
    void testFullFlow_OnlySpecialCharacters() throws Exception {
        Map<String, Object> request = Map.of("data", List.of("@", "#", "!", "&"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.special_characters.length()").value(4))
                .andExpect(jsonPath("$.odd_numbers.length()").value(0))
                .andExpect(jsonPath("$.even_numbers.length()").value(0))
                .andExpect(jsonPath("$.alphabets.length()").value(0))
                .andExpect(jsonPath("$.sum").value("0"));
    }

    @Test
    @DisplayName("Integration: POST /bfhl with empty data array")
    void testFullFlow_EmptyData() throws Exception {
        Map<String, Object> request = Map.of("data", Collections.emptyList());

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.sum").value("0"))
                .andExpect(jsonPath("$.concat_string").value(""));
    }

    @Test
    @DisplayName("Integration: POST /bfhl with invalid JSON returns 400")
    void testFullFlow_InvalidJson() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("not valid json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    @DisplayName("Integration: POST /bfhl with null data returns 400")
    void testFullFlow_NullData() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    @DisplayName("Integration: GET /bfhl returns operation_code")
    void testGetEndpoint() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code").value(1));
    }

    @Test
    @DisplayName("Integration: POST /bfhl with large dataset")
    void testFullFlow_LargeInput() throws Exception {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            data.add(String.valueOf(i));
            data.add(String.valueOf((char) ('a' + (i % 26))));
        }
        Map<String, Object> request = Map.of("data", data);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true));
    }
}
