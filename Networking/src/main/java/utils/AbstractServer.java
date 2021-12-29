package utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public abstract class AbstractServer {
    private final int port;
    private ServerSocket server=null;
    public AbstractServer( int port){
              this.port=port;
    }
    public boolean running=true;
    public void start() throws ServerException {
        long start=System.currentTimeMillis();
        System.out.println(start);
        try{
            server=new ServerSocket(port);
//            new Thread(() -> { // Lambda Expression
//                while (System.currentTimeMillis()-start<20000)
//                {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                notifyClient();
//                System.out.println("AICI");
//                running=false;
//
//            }).start();
            Thread tw=new Thread(() -> {
            while(running){
                System.out.println(running);
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
            tw.start();
            while (System.currentTimeMillis()-start<20000)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notifyClient();
            tw.stop();
            System.out.println("AICI");
            running=false;
        } catch (IOException e) {
            throw new ServerException("Starting server error ",e);
        }finally {
            stop();
        }

    }
    protected abstract void notifyClient();
    protected abstract  void processRequest(Socket client);
    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}