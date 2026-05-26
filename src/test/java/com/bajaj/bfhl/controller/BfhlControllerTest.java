package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.RequestDTO;
import com.bajaj.bfhl.dto.ResponseDTO;
import com.bajaj.bfhl.service.BfhlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BfhlController.class)
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BfhlService bfhlService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /bfhl — should return 200 with processed data")
    void testPostBfhl_Success() throws Exception {
        ResponseDTO mockResponse = ResponseDTO.builder()
                .isSuccess(true)
                .userId("priyanshu_yadav_11042005")
                .email("ypriyanshu5714@gmail.com")
                .rollNumber("0827CS231201")
                .oddNumbers(List.of("1"))
                .evenNumbers(List.of("334", "4"))
                .alphabets(List.of("A", "R"))
                .specialCharacters(List.of("$"))
                .sum("339")
                .concatString("Ra")
                .build();

        when(bfhlService.processData(any(RequestDTO.class))).thenReturn(mockResponse);

        RequestDTO request = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.user_id").value("priyanshu_yadav_11042005"))
                .andExpect(jsonPath("$.email").value("ypriyanshu5714@gmail.com"))
                .andExpect(jsonPath("$.roll_number").value("0827CS231201"))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.even_numbers[0]").value("334"))
                .andExpect(jsonPath("$.even_numbers[1]").value("4"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.alphabets[1]").value("R"))
                .andExpect(jsonPath("$.special_characters[0]").value("$"))
                .andExpect(jsonPath("$.sum").value("339"))
                .andExpect(jsonPath("$.concat_string").value("Ra"));
    }

    @Test
    @DisplayName("POST /bfhl — should return 400 for null data field")
    void testPostBfhl_NullData() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    @DisplayName("POST /bfhl — should return 400 for missing data field")
    void testPostBfhl_MissingData() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    @DisplayName("POST /bfhl — should return 400 for malformed JSON")
    void testPostBfhl_MalformedJson() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false))
                .andExpect(jsonPath("$.message").value("Malformed JSON request"));
    }

    @Test
    @DisplayName("POST /bfhl — should return 200 for empty data list")
    void testPostBfhl_EmptyData() throws Exception {
        ResponseDTO mockResponse = ResponseDTO.builder()
                .isSuccess(true)
                .userId("priyanshu_yadav_11042005")
                .email("ypriyanshu5714@gmail.com")
                .rollNumber("0827CS231201")
                .oddNumbers(Collections.emptyList())
                .evenNumbers(Collections.emptyList())
                .alphabets(Collections.emptyList())
                .specialCharacters(Collections.emptyList())
                .sum("0")
                .concatString("")
                .build();

        when(bfhlService.processData(any(RequestDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\": []}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.sum").value("0"));
    }

    @Test
    @DisplayName("GET /bfhl — should return operation_code = 1")
    void testGetBfhl() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code").value(1));
    }
}
