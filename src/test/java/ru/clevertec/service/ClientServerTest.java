package ru.clevertec.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServerTest {

    @Test
    void checkClientServerTestShouldRealityValue() {
        Client client = new Client(10);
        Server server = Server.getInstance();

        var accumulator = client.requestToServer(server);
        assertEquals(205, accumulator);
    }
}
