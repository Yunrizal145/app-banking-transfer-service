package com.spring.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternalTransferRequest implements Serializable {
    private static final long serialVersionUID = 8257636491043998077L;

    private String fromAccountNumber;
    private String fromAccountName;
    private String toAccountNumber;
    private String toAccountName;
    private BigDecimal transactionAmount;
    private String notes;
    private Boolean isFavorite=Boolean.FALSE;
    private String favoriteName;
}
