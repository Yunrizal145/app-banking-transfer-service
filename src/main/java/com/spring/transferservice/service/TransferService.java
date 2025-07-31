package com.spring.transferservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.myaccountmanagementservice.dto.GetMutasiByAccountNumberRequest;
import com.spring.transactionhistorymanagementservice.constant.AccountType;
import com.spring.transactionhistorymanagementservice.constant.TransactionStatus;
import com.spring.transactionhistorymanagementservice.model.TransactionHistory;
import com.spring.transferservice.dto.TransferRequestDto;
import com.spring.transferservice.dto.TransferResponse;
import com.spring.usermanagementservice.model.UserFavorite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.spring.transferservice.util.ConstructUtils.constructTransactionHistory;

@Slf4j
@Service
public class TransferService {

    @Value("${midtrans.server-key}")
    private String serverKeyUrl;

    @Value("${midtrans.client-key}")
    private String clientKeyUrl;

    @Value("${midtrans.api-url}")
    private String midtransUrl;

    @Autowired
    private TransactionHistoryManagementService transactionHistoryManagementService;

    @Autowired
    private MyAccountManagementService myAccountManagementService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private ObjectMapper mapper;

    public TransferResponse transfer(TransferRequestDto dto) {
        log.info("start transferAntarBank");
        log.info("start transferAntarBank req : {}", dto);
        TransactionStatus transactionStatus = TransactionStatus.PENDING;
        try {
            var accountUser = myAccountManagementService.getAccountUserByAccountNumber(GetMutasiByAccountNumberRequest.builder().accountNumber(dto.getFromAccountNumber()).build());
            log.info("data account user : {}", accountUser);
            String orderId = UUID.randomUUID().toString();

            // Request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("payment_type", "bank_transfer");

            // Transaction details
            Map<String, Object> transactionDetails = new HashMap<>();
            transactionDetails.put("order_id", orderId);
            transactionDetails.put("gross_amount", dto.getTransactionAmount());
            requestBody.put("transaction_details", transactionDetails);

            // Bank transfer config
            Map<String, Object> bankTransfer = new HashMap<>();
            bankTransfer.put("bank", dto.getBankName().toLowerCase());
            bankTransfer.put("va_number", dto.getToAccountNumber());
            requestBody.put("bank_transfer", bankTransfer);

            // Optional - Customer Details
            Map<String, Object> customerDetails = new HashMap<>();
            customerDetails.put("first_name", dto.getCustomerName());
            customerDetails.put("email", dto.getCustomerEmail());
            customerDetails.put("phone", dto.getCustomerPhone());
            requestBody.put("customer_details", customerDetails);

            // Optional - Item Details
            Map<String, Object> itemDetail = new HashMap<>();
            itemDetail.put("id", "topup-01");
            itemDetail.put("price", dto.getTransactionAmount());
            itemDetail.put("quantity", 1);
            itemDetail.put("name", "Top Up Saldo");
            requestBody.put("item_details", List.of(itemDetail));

            // Header Authorization
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String encoded = Base64.getEncoder().encodeToString((serverKeyUrl + ":").getBytes());
            headers.set("Authorization", "Basic " + encoded);

            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

            // Endpoint Midtrans
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(midtransUrl, httpEntity, Map.class);

            // Ambil response dari Midtrans
            Map resBody = response.getBody();
            String vaNumber = ((Map) ((List) resBody.get("va_numbers")).get(0)).get("va_number").toString();
            String bank = ((Map) ((List) resBody.get("va_numbers")).get(0)).get("bank").toString();

            // Optional log
            System.out.println("VA Number: " + vaNumber + " Bank: " + bank);

            var transferResponse = TransferResponse.builder()
                    .transactionId(resBody.get("transaction_id").toString())
                    .userProfileId(accountUser.getUserProfileId())
                    .transactionDate(new Date())
                    .transactionAmount(new BigDecimal(dto.getTransactionAmount()))
                    .fromAccountNumber(dto.getFromAccountNumber())
                    .fromAccountType(AccountType.TABUNGAN)
                    .resultCode(resBody.get("status_code").toString())
                    .toAccountName(dto.getToAccountName())
                    .toAccountNumber(dto.getToAccountNumber())
                    .transactionCurrency("IDR")
                    .transactionFee(BigDecimal.ZERO)
                    .transactionDescription(dto.getNotes())
                    .transactionStatus(transactionStatus)
                    .additionalData(mapper.writeValueAsString(resBody))
                    .build();

            if (dto.getIsFavorite()){
                // Save To Favorite
                userManagementService.saveUserFavorite(UserFavorite.builder()
                                .userProfileId(accountUser.getUserProfileId())
                                .accountNumber(dto.getToAccountNumber())
                                .accountName(dto.getToAccountName())
                                .favoriteName(dto.getFavoriteName())
                                .bankName(dto.getBankName())
                        .build());
            }

            transactionHistoryManagementService.saveTransactoinHistory(constructTransactionHistory(transferResponse));
            return transferResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error Transfer", e);
        }
    }
}
