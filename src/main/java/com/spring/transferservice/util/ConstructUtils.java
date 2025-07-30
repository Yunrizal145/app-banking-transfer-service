package com.spring.transferservice.util;

import com.spring.transactionhistorymanagementservice.model.TransactionHistory;
import com.spring.transferservice.dto.TransferResponse;

public class ConstructUtils {

    public static TransactionHistory constructTransactionHistory(TransferResponse transfer){
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionId(transfer.getTransactionId());
        transactionHistory.setUserProfileId(transfer.getUserProfileId());
        transactionHistory.setTransactionDate(transfer.getTransactionDate());
        transactionHistory.setTransactionDescription(transfer.getTransactionDescription());
        transactionHistory.setTransactionAmount(transfer.getTransactionAmount());
        transactionHistory.setTransactionFee(transfer.getTransactionFee());
        transactionHistory.setTransactionStatus(transfer.getTransactionStatus());
        transactionHistory.setTransactionCurrency(transfer.getTransactionCurrency());
        transactionHistory.setAdditionalData(transfer.getAdditionalData());
        transactionHistory.setFromAccountType(transfer.getFromAccountType());
        transactionHistory.setFromAccountNumber(transfer.getFromAccountNumber());
        transactionHistory.setResultCode(transfer.getResultCode());
        transactionHistory.setToAccountName(transfer.getToAccountName());
        transactionHistory.setToAccountNumber(transfer.getToAccountNumber());
        return transactionHistory;
    }
}
