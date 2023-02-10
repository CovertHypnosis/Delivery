package com.example.delivery.configurations;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedisConfiguration {
    private final RedisClient redisClient;

    public RedisConfiguration(Environment environment) {
        String host = environment.getProperty("redis.host");
        this.redisClient = RedisClient
                .create(RedisURI.Builder
                        .redis(host, 6379)
                        .build());
    }

    @Bean
    public RedisPubSubReactiveCommands<String, String> redisReactivePublisher() {
        return redisClient.connectPubSub().reactive();
    }
}
