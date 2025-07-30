package com.spring.transferservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.myaccountmanagementservice.dto.AccountUserDto;
import com.spring.myaccountmanagementservice.dto.GetMutasiByAccountNumberRequest;
import com.spring.myaccountmanagementservice.model.AccountUser;
import com.spring.transactionhistorymanagementservice.constant.AccountType;
import com.spring.transactionhistorymanagementservice.constant.TransactionStatus;
import com.spring.transferservice.dto.InternalTransferRequest;
import com.spring.transferservice.dto.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static com.spring.transferservice.util.ConstructUtils.constructTransactionHistory;

@Slf4j
@Service
public class InternalTransferService {

    @Autowired
    private TransactionHistoryManagementService transactionHistoryManagementService;

    @Autowired
    private MyAccountManagementService myAccountManagementService;

    @Autowired
    ObjectMapper mapper;

    @Transactional
    public TransferResponse transferInternal(InternalTransferRequest dto) {
        log.info("start transferInternal");
        log.info("start transferInternal req : {}", dto);
        BigDecimal fromAccountBalance = BigDecimal.ZERO;
        BigDecimal toAccountBalance = BigDecimal.ZERO;
        try {
            AccountUser fromAccount = myAccountManagementService.getAccountUserByAccountNumber(GetMutasiByAccountNumberRequest.builder()
                    .accountNumber(dto.getFromAccountNumber()).build());

            AccountUser toAccount = myAccountManagementService.getAccountUserByAccountNumber(GetMutasiByAccountNumberRequest.builder()
                    .accountNumber(dto.getToAccountNumber()).build());

            if (fromAccount.getBalance().compareTo(dto.getAmount()) < 0) {
                throw new RuntimeException("Saldo tidak mencukupi");
            }

            fromAccountBalance = fromAccount.getBalance().subtract(dto.getAmount());
            // update saldo pengirim
            myAccountManagementService.updateBalance(AccountUserDto.builder()
                    .accountNumber(fromAccount.getAccountNumber())
                    .balance(fromAccountBalance)
                    .build());

            toAccountBalance = toAccount.getBalance().add(dto.getAmount());
            // update saldo penerima
            myAccountManagementService.updateBalance(AccountUserDto.builder()
                    .accountNumber(fromAccount.getAccountNumber())
                    .balance(toAccountBalance)
                    .build());

            var transferResponse = TransferResponse.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .transactionDate(new Date())
                    .transactionAmount(dto.getAmount())
                    .fromAccountNumber(dto.getFromAccountNumber())
                    .fromAccountType(AccountType.TABUNGAN)
                    .resultCode("200")
                    .toAccountName(dto.getToAccountName())
                    .toAccountNumber(dto.getToAccountNumber())
                    .transactionCurrency("IDR")
                    .transactionFee(BigDecimal.ZERO)
                    .transactionDescription(dto.getMessage())
                    .transactionStatus(TransactionStatus.SUCCESS)
                    .additionalData(mapper.writeValueAsString(dto))
                    .build();

            transactionHistoryManagementService.saveTransactoinHistory(constructTransactionHistory(transferResponse));

            return TransferResponse.builder()

                    .build();
        } catch (Exception e) {
            throw new RuntimeException("error when transfer internal : {}", e);
        }

    }
}
