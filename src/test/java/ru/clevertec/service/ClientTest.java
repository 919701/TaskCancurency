package ru.clevertec.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.model.Binder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientTest {

    @Test
    void checkRequestToServerShouldResultIsBeActual() {
        Client client = new Client(10);
        Server server = Server.getInstance();

        assertEquals(205, client.requestToServer(server));
    }

    @Test
    void mockito() {
        Client client = new Client(10);
        Server server = mock(Server.class);


        doReturn(new Binder(0)).when(server).response(any());
        assertEquals(0,client.requestToServer(server));
        verify(server).response(any());
    }
}