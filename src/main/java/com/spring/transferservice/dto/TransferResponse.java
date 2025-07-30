package com.spring.transferservice.dto;

import com.spring.transactionhistorymanagementservice.constant.AccountType;
import com.spring.transactionhistorymanagementservice.constant.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse implements Serializable {
    private static final long serialVersionUID = 549157858164226741L;

    private String transactionId;
    private Long userProfileId;
    private String transactionDescription;
    private Date transactionDate;
    private TransactionStatus transactionStatus;
    private String transactionCurrency;
    private BigDecimal transactionAmount;
    private BigDecimal transactionFee;
    private String resultCode;
    private String fromAccountNumber;
    private AccountType fromAccountType;
    private String toAccountNumber;
    private String toAccountName;
    private String additionalData;
}
