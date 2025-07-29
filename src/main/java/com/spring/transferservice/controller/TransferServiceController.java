package com.spring.transferservice.controller;

import com.spring.transferservice.dto.TransferRequest;
import com.spring.transferservice.dto.TransferRequestDto;
import com.spring.transferservice.service.MidtransTransferService;
import com.spring.transferservice.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferServiceController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer-midtrans")
    public ResponseEntity<?> transfer(@RequestBody TransferRequestDto request) {
        return ResponseEntity.ok(transferService.transfer(request));
    }
}
