package com.chx.chat.netty.server.service;

@FunctionalInterface
public interface CommandLineRunner {
    void run(String... args) throws Exception;
}