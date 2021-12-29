package utils;

import java.net.Socket;
import java.util.concurrent.*;


public abstract class AbsConcurrentServer extends AbstractServer {
    private ExecutorService executorService;

    public AbsConcurrentServer(int port, int threads) {
        super(port);
         System.out.println("Concurrent AbstractServer");
         executorService = Executors.newFixedThreadPool(threads);
    }

    protected void processRequest(Socket client) {
        Thread tw=createWorker(client);
        //executorService.execute(tw);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                tw.start();
                return "Done";
            }
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    protected abstract Thread createWorker(Socket client) ;
}
