package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.RequestDTO;
import com.bajaj.bfhl.dto.ResponseDTO;
import com.bajaj.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bfhl")
@RequiredArgsConstructor
public class BfhlController {

    private final BfhlService bfhlService;

    /**
     * POST /bfhl — Processes the data array and returns categorized results.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> processData(@Valid @RequestBody RequestDTO requestDTO) {
        ResponseDTO response = bfhlService.processData(requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /bfhl — Returns a simple operation code (useful for health checks).
     */
    @GetMapping
    public ResponseEntity<Map<String, Integer>> getOperationCode() {
        return ResponseEntity.ok(Map.of("operation_code", 1));
    }

    /**
     * GET /health — Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "healthy"));
    }
}
