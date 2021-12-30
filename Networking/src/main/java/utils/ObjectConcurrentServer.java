package utils;

import objectProtocol.ClientObjectWorker;
import service.IServices;
import service.ServiceException;

import java.net.Socket;

public class ObjectConcurrentServer extends AbsConcurrentServer {
    private IServices services;

    public ObjectConcurrentServer(int port, int threads, IServices server) {
        super(port, threads);
        this.services = server;
        System.out.println("ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientObjectWorker worker=new ClientObjectWorker(services, client);
        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    protected void notifyClient() {
        try {
            services.notifyServerStoped();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void verificare() {
        services.verificare();
    }
}
