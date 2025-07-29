package com.spring.transferservice.service;

import com.spring.transferservice.dto.TransferRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class MidtransTransferService {

    @Value("${midtrans.server-key}")
    private String serverKeyUrl;

    @Value("${midtrans.client-key}")
    private String clientKeyUrl;

    @Value("${midtrans.api-url}")
    private String midtransUrl;

    public String sendTransfer(TransferRequest request) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String auth = serverKeyUrl + ":";
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            headers.set("Authorization", "Basic " + encodedAuth);

            Map<String, Object> body = new HashMap<>();
            body.put("external_id", request.getExternalId());
            body.put("amount", request.getAmount());
            body.put("bank_code", request.getBankCode());
            body.put("account_holder_name", request.getAccountHolderName());
            body.put("account_number", request.getAccountNumber());
            body.put("description", request.getDescription());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(midtransUrl, HttpMethod.POST, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Gagal transfer: " + e.getMessage();
        }
    }
}
