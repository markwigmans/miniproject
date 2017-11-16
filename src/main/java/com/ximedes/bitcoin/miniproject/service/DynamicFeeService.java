package com.ximedes.bitcoin.miniproject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * FeeService with a dynamic fee. If the fee server can't be reached, the static {@link FixedFeeService} is used.
 */
@Slf4j
@Component
@Primary
public class DynamicFeeService implements FeeService {

    private static final String API_URL = "http://api.blockcypher.com/v1/btc/main";

    /**
     * fee per byte
     */
    private Coin fee;
    private final FeeService fallback;
    private final int timeout;

    /**
     * constructor
     */
    @Autowired
    public DynamicFeeService(@Value("${fee.timeout.ms:5000}") int timeout,
                             FixedFeeService fallback) {
        this.timeout = timeout;
        this.fallback = fallback;
    }

    @PostConstruct
    void init() {
        try {
            RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
            ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode mediumFee = mapper.readTree(response.getBody()).path("medium_fee_per_kb");
            fee = Coin.valueOf(Long.parseLong(mediumFee.asText()));
            log.info("Dynamic fee: {}", fee);
        } catch (Exception e) {
            fee = fallback.feePerKb();
            log.warn("fallback static fee: {}", fee);
        }
    }

    public Coin feePerKb() {
        return fee;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
