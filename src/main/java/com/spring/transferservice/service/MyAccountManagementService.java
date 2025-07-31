package com.spring.transferservice.service;

import com.spring.myaccountmanagementservice.dto.AccountUserDto;
import com.spring.myaccountmanagementservice.dto.GetMutasiByAccountNumberRequest;
import com.spring.myaccountmanagementservice.dto.SaveAccountUserResponse;
import com.spring.myaccountmanagementservice.dto.SaveUserAccountRequest;
import com.spring.myaccountmanagementservice.model.AccountUser;
import com.spring.transactionhistorymanagementservice.dto.GetTransactionByTransactionIdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MyAccountManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.getAccountUser}")
    private String getAccountUserUrl;

    @Value("${url.saveAccountUser}")
    private String saveAccountUserUrl;

    @Value("${url.updateBalance}")
    private String updateBalanceUrl;

    @Value("${url.checkUserBalance}")
    private String checkuserBalanceUrl;

    @Value("${url.creditBalanceAndRecorMutation}")
    private String creditBalanceAndRecordMutationUrl;

    @Value("${url.decutBalanceAndRecordMutation}")
    private String deductBalanceAndRecordMutationUrl;

    public AccountUser getAccountUserByAccountNumber(GetMutasiByAccountNumberRequest request) {
        log.info("Start getAccountUser ... ");
        log.info("getAccountUser req {}", request);

        ResponseEntity<AccountUser> getAccountUser = restTemplate.postForEntity(getAccountUserUrl, request, AccountUser.class);
        log.info("data response accountUser : {}", getAccountUser.getBody());
        return getAccountUser.getBody();
    }

    public SaveAccountUserResponse saveAccountUser(SaveUserAccountRequest request) {
        log.info("Start getValueFromMyAccountMS ... ");
        log.info("saveAccountUser ... ");

        ResponseEntity<SaveAccountUserResponse> getAccountUser = restTemplate.postForEntity(saveAccountUserUrl, request, SaveAccountUserResponse.class);
        return getAccountUser.getBody();
    }

    public void updateBalance(AccountUserDto request) {
        log.info("Start updateBalance ... ");
        log.info("updateBalance req : {}", request);
        restTemplate.postForEntity(updateBalanceUrl, request, Void.class);
    }

    public void checkUserBalance(AccountUserDto request) {
        log.info("Start checkUserBalance ... ");
        log.info("checkUserBalance req : {}", request);
        restTemplate.postForEntity(checkuserBalanceUrl, request, Void.class);
    }

    public void creditBalanceAndRecordMutation(AccountUserDto request) {
        log.info("Start creditBalanceAndRecordMutation ... ");
        log.info("creditBalanceAndRecordMutation req : {}", request);
        restTemplate.postForEntity(creditBalanceAndRecordMutationUrl, request, Void.class);
    }

    public void deductBalanceAndRecordMutation(AccountUserDto request) {
        log.info("Start deductBalanceAndRecordMutation ... ");
        log.info("deductBalanceAndRecordMutation req : {}", request);
        restTemplate.postForEntity(deductBalanceAndRecordMutationUrl, request, Void.class);
    }
}
