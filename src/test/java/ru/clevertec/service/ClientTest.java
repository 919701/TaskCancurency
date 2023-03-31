package ru.clevertec.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {

    Client client;
    Server server;

    @BeforeEach
    void setUp() {
        client = new Client(10);
        server = Server.getInstance();

    }

    @Test
    void requestToServer() {

    }

    @Test
    void checkSumAccumulatorShouldCountCorrectly() {
        client.requestToServer(server);
        var accumulator = client.getAccumulator();
        Assertions.assertEquals(205, accumulator);
    }
}