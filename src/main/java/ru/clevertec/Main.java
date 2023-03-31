package ru.clevertec;

import ru.clevertec.service.Client;
import ru.clevertec.service.Server;

public class Main {

    public static void main(String[] args) {
        Client client = new Client(100);
        Server server = Server.getInstance();

        client.requestToServer(server);
        System.out.printf("Sum of values from server responses: %d",client.getAccumulator());
    }
}