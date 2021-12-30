package utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public abstract class AbstractServer {
    private final int port;
    private ServerSocket server=null;
    private final int INTERVAL_VERIFICARE = 5000;
    private final int TIMP_RULARE = 120000;

    public AbstractServer( int port){
              this.port=port;
    }
    public boolean running=true;
    public void start() throws ServerException {
        long start=System.currentTimeMillis();

        try{
            server=new ServerSocket(port);

            Thread requestProcessingThread=new Thread(() -> {
                while(running){
                    System.out.println("Waiting for clients ...");
                    Socket client= null;

                    try {
                        client = server.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Client connected ...");
                    processRequest(client);
                    System.out.println(System.currentTimeMillis()-start);
                }
            });

            Thread checkingThread = new Thread(() -> {
                while(running){
                    try {
                        Thread.sleep(INTERVAL_VERIFICARE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    verificare();
                }
            });

            requestProcessingThread.start();
            checkingThread.start();

            while (System.currentTimeMillis() - start < TIMP_RULARE)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notifyClient();
            requestProcessingThread.stop();
            System.out.println("Server stopping");
            running=false;
        } catch (IOException e) {
            throw new ServerException("Starting server error ",e);
        }finally {
            stop();
        }

    }

    protected abstract void notifyClient();

    protected abstract void verificare();

    protected abstract  void processRequest(Socket client);

    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}