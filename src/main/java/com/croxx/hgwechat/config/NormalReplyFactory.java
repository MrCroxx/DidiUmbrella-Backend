package com.croxx.hgwechat.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "reply.normal")
public class NormalReplyFactory {
    private List<String> contents = new ArrayList<>();
    private static Random random = new Random(System.currentTimeMillis());

    public String createRandomReply() {
        return contents.get(random.nextInt(contents.size()));
    }

    /*    Getters & Setters     */

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }
}
