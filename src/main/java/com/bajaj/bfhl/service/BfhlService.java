package com.bajaj.bfhl.service;

import com.bajaj.bfhl.dto.RequestDTO;
import com.bajaj.bfhl.dto.ResponseDTO;

public interface BfhlService {

    /**
     * Processes the input data array and returns categorized results.
     *
     * @param requestDTO the request containing the data array
     * @return ResponseDTO with categorized numbers, alphabets, special characters, sum, and concat_string
     */
    ResponseDTO processData(RequestDTO requestDTO);
}
