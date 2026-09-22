package com.os.yerinial.config;

import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheManagerBuilderCustomizer cacheCustomizer(ObjectMapper objectMapper) {

        RedisSerializer<Object> jsonSerializer =
                new GenericJacksonJsonRedisSerializer(objectMapper);

        RedisCacheConfiguration base =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(new StringRedisSerializer())
                        )
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(jsonSerializer)
                        )
                        .disableCachingNullValues();

        return builder -> builder
                .cacheDefaults(base)
                .withCacheConfiguration(
                        "getEventDetailsById",
                        base.entryTtl(Duration.ofMinutes(15))
                )
                .withCacheConfiguration(
                        "customer-reservations",
                        base.entryTtl(Duration.ofMinutes(15))
                )
                .withCacheConfiguration(
                        "eventDetail",
                        base.entryTtl(Duration.ofMinutes(5))
                )
                .withCacheConfiguration(
                        "cities",
                        base.entryTtl(Duration.ofHours(24))
                );
    }
}