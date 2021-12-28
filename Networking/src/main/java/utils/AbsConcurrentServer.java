package utils;

import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class AbsConcurrentServer extends AbstractServer {
    private ExecutorService executorService;

    public AbsConcurrentServer(int port) {
        super(port);
         System.out.println("Concurrent AbstractServer");
         executorService = Executors.newFixedThreadPool(10);
    }

    protected void processRequest(Socket client) {
        Thread tw=createWorker(client);
        executorService.execute(tw);
    }

    protected abstract Thread createWorker(Socket client) ;


}