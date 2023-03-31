package ru.clevertec.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.clevertec.model.Binder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 Server - receives requests from the client. The function processing the request has a delay in the form of a random int.
 The range is from 100 to 1000 ms. The server processes requests using a common resource for all threads: List<Integer>,
 which contains the values coming with the request. In response from the server, we pass the size of the sheet at the
 time the response was generated (a class with an int -field). Final data validation on the server side: the list (shared
 resource) must contain values from 1 to n without spaces, repetitions, its dimension must be n.
 */

@NoArgsConstructor(staticName = "getInstance")
public class Server {

    @Getter
    private List<Integer> list = new CopyOnWriteArrayList<>();

    /**
     Request handling method
     has a delay in the form of a random int. The range is from 100 to 1000 ms.
     The server processes requests using a common thread for all threads.
     resource: List<Integer>, which contains the values coming from
     request. In response from the server, we transmit the size of the sheet at the moment
     response generation (class with int -field)
     * @param response sheet size
     * @return object class Binder
     */
    public Binder response(Binder response) {
        Lock lock = new ReentrantLock();

        try {
            lock.lock();
            list.add(response.getValue());
            Thread.sleep(sleepRandom(100, 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return new Binder(list.size());
    }

    /**
     Method for setting a random value of the server response delay in the Min-Max interval
     * @param MIN_SLEEP_THREAD minimum value in milliseconds
     * @param MAX_SLEEP_THREAD maximum value in millisecond
     * @return random value in millisecond
     */
    public final int sleepRandom(int MIN_SLEEP_THREAD, int MAX_SLEEP_THREAD) {
        return ThreadLocalRandom.current().nextInt(MIN_SLEEP_THREAD, MAX_SLEEP_THREAD);
    }
}
