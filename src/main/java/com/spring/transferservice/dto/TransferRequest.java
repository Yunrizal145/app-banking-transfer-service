package com.spring.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest implements Serializable {
    private static final long serialVersionUID = 237943326440456595L;

    private String externalId;
    private Long amount;
    private String bankCode;
    private String accountHolderName;
    private String accountNumber;
    private String description;
}
