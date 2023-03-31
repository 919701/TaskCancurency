package ru.clevertec;

import ru.clevertec.service.Client;
import ru.clevertec.service.Server;

public class Main {

    public static void main(String[] args) {
        Client client = new Client(10);
        Server server = Server.getInstance();

        System.out.println("Sum of values from server responses: " + client.requestToServer(server));
        System.out.println("List server: " + server.getList());
    }
}