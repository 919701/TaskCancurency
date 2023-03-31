package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import ru.clevertec.model.Binder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientTest {

    @Test
    void checkRequestToServerShouldRealValue() {
        Client client = new Client(10);
        Server server = spy(Server.getInstance());

        var accumulator = client.requestToServer(server);
        doReturn(new Binder(1)).when(server).response(new Binder(anyInt()));

        assertEquals(205, accumulator);
    }
}