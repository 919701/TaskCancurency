package ru.clevertec.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.model.Binder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void checkResponseShouldReturnListSize() {
        Server server = Server.getInstance();
        var binder = server.response(new Binder(5));
        assertEquals(1,binder.getValue());
    }

    @Test
    void getList() {
        Server server = Server.getInstance();
        var binder = server.response(new Binder(5));
        assertFalse(server.getList().isEmpty());
    }
}