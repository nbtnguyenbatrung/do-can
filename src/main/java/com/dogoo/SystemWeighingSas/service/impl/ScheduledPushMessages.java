package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.service.WeighingStationService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledPushMessages {

    private final AccountService accountService;
    private SimpMessagingTemplate template;
    private final WeighingStationService weighingStationService;

    public ScheduledPushMessages(AccountService accountService,
                                 SimpMessagingTemplate template,
                                 WeighingStationService weighingStationService) {
        this.accountService = accountService;
        this.template = template;
        this.weighingStationService = weighingStationService;
    }

    @Scheduled(fixedRate = 30000)
    public void sendRole(@Payload String username) {
        template.convertAndSendToUser(username, "/public/dogoo/role",
                accountService.getAccountByAccountId(Long.parseLong(username)));
    }

    @Scheduled(fixedRate = 30000)
    public void sendWeighingStation(@Payload String username) {
        template.convertAndSendToUser(username, "/public/dogoo/weighingStation",
                weighingStationService.getWeighingStationByCustomerKey(username));
    }


}
