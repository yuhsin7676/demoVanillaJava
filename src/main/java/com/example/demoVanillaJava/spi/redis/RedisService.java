package com.example.demoVanillaJava.spi.redis;

import redis.clients.jedis.Jedis;

public class RedisService {

    public String get (String key) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            return jedis.get(key);
        }
    }

    public void set (String key, String value) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.set(key, value);
        }
    }

    public void inkr (String key) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.incr(key);
        }
    }



}
