package com.spring.transferservice.service;

import com.spring.usermanagementservice.model.UserFavorite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class UserManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.saveUserFavorite}")
    private String saveUserFavoriteUrl;

    public void saveUserFavorite(UserFavorite userFavorite){
        log.info("start saveUserFavorite for userMS");
        log.info("start saveUserFavorite for userMS req : {}", userFavorite);

        restTemplate.postForEntity(saveUserFavoriteUrl, userFavorite, Void.class);
    }
}
