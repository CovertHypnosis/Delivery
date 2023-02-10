package com.example.delivery.listeners;

import com.example.delivery.dtos.ProductPurchaseDTO;
import com.example.delivery.services.MailSendService;
import com.example.delivery.services.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.pubsub.api.reactive.ChannelMessage;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class OrderListener {
    private final RedisPubSubReactiveCommands<String, String> reactiveSubscriber;
    private final PurchaseService purchaseService;
    private final MailSendService mailSendService;
    private final ObjectMapper mapper;
    private final static String CHANNEL_NAME = "purchase";

    public OrderListener(RedisPubSubReactiveCommands<String, String> reactiveSubscriber,
                         PurchaseService purchaseService,
                         MailSendService mailSendService, ObjectMapper mapper) {
        this.reactiveSubscriber = reactiveSubscriber;
        this.purchaseService = purchaseService;
        this.mailSendService = mailSendService;
        this.mapper = mapper;
        this.startListening();
    }

    private void startListening() {
        connectToChannel(CHANNEL_NAME)
                .flatMap(channelMessage -> {
                    log.info("Received from channel " + channelMessage.getChannel() + " Message: " + channelMessage.getMessage());
                    try {
                        return Mono.just(mapper.readValue(channelMessage.getMessage(), ProductPurchaseDTO.class));
                    } catch (JsonProcessingException ex) {
                        return Flux.error(new RuntimeException(ex));
                    }
                })
                .flatMap(purchaseDTO -> Mono.just(purchaseService.processPurchase(purchaseDTO)))
                .flatMap(order -> {
//                    mailSendService.sendMail(order.toString());
//                    mailSendService.sendSMS(order.toString());
                    return Mono.just(order);
                })
                .subscribe();
    }

    public final Flux<ChannelMessage<String, String>> connectToChannel(String channelName) {
        return reactiveSubscriber.subscribe(channelName)
                .thenMany(reactiveSubscriber.observeChannels())
                .filter(channelMessage -> channelMessage.getChannel().equals(channelName));
    }
}
