package ru.clevertec.service;

import lombok.Data;
import lombok.Getter;
import ru.clevertec.model.Binder;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 Client - has a list of data in the form of a List<Integer> from 1 to n. Separate threads select a value from the list
 by a random index (remove() method) and send it to the server in the form of a request (a class with an int -field)
 containing this value in asynchronous mode. The number of requests is equal to the size of the original list.
 Control: after sending all requests, the size of the data list = 0.

 @see Server
 */

public class Client {

    @Getter
    private volatile int accumulator;
    private final List<Integer> list;
    private final int sizeList;

    public Client(int sizeList) {

        this.sizeList = sizeList;
        //Filling in the data list
        this.list =
                IntStream.rangeClosed(1, sizeList)
                        .boxed()
                        .collect(Collectors.toList());
    }

    /**
     The client receives a response from the server and sums up the value from the response from the server into the
     accumulator resource common to all threads. Final control: accumulator = (1+n) * (n/2). Those. for a range of 1-100,
     the answer should be 5050.
     @param server
     @see Server
     */
    public void requestToServer(Server server) {
        Lock lock = new ReentrantLock();
        ExecutorService executor = Executors.newFixedThreadPool(sizeList);

        List<Integer> listAccumulator =
                IntStream.rangeClosed(1, sizeList)
                        .mapToObj(binder -> {
                            try {
                                lock.lock();
                                return executor.submit(() -> {
                                    var index =
                                            ThreadLocalRandom.current().nextInt(0, list.size());
                                    var message = list.get(index);
                                    list.remove(index);
                                    return server.response(new Binder(message));
                                }).get();
                            } catch (InterruptedException | ExecutionException e) {
                                throw new RuntimeException(e);
                            } finally {
                                lock.unlock();
                            }
                        })
                        .mapToInt(Binder::getValue)
                        .mapToObj(this::computing)
                        .toList();
        executor.shutdown();
        accumulator = sumAccumulator(listAccumulator);
    }

    /**
     Function to calculate a value using the formula (n + 1) * (n / 2)
     * @param n meaning
     * @return returning a value from a formula
     */
    private int computing(int n) {
        return ((n + 1) * (n / 2));
    }

    /**
     Method for calculating the sum of received responses from the Server
     * @param listAccumulator
     * @return the sum of all elements of the list
     */
    private int sumAccumulator(List<Integer> listAccumulator) {
        return listAccumulator.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
