import domain.Vanzare;
import objectProtocol.ServicesObjectProxy;
import service.BadCredentialsException;
import service.IObserver;
import service.IServices;
import service.ServiceException;

import java.io.IOException;
import java.util.*;

public class StartAutomaticClient{

    public static void main(String[] args) {
        Client c=new Client();
        c.run();
    }
}

class Client implements IObserver{
    private boolean running=true;
    private  int defaultPort =55555;
    private  String defaultServer="localhost";

    public Client() {
    }

    public void run(){
        Properties clientProps=new Properties();

        try {
            clientProps.load(StartObjectClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties "+e);
            return;
        }

        String serverIP=clientProps.getProperty("server.host",defaultServer);

        int serverPort= defaultPort;

        try{
            serverPort=Integer.parseInt(clientProps.getProperty("server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+ defaultPort);
        }

        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        IServices server=new ServicesObjectProxy(serverIP, serverPort);

        int idSpectacol, nrSpectacole = 3, nrLocuriTotal = 100, nrLocuri, loc;
        List<Integer> locuri;
        Random generator = new Random();

        try {
            server.login(null,this);
        } catch (ServiceException | BadCredentialsException e) {
            e.printStackTrace();
        }

        while(running) {
            locuri = new ArrayList<>();
            idSpectacol = generator.nextInt(nrSpectacole) + 1;
            nrLocuri = generator.nextInt(5) + 1;

            for(int i = 0; i < nrLocuri; i++) {
                loc = generator.nextInt(nrLocuriTotal) + 1;

                while(locuri.contains(loc)) {
                    loc = generator.nextInt(nrLocuriTotal) + 1;
                }

                locuri.add(loc);
            }

            try {
                server.vanzare(new Vanzare(idSpectacol, locuri, new Date().toString()));
            } catch (ServiceException e) {
                System.out.println("Vanzare esuata");
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void serverStoped() throws ServiceException {
        System.out.println("Server stoped");
        running=false;
    }
}
