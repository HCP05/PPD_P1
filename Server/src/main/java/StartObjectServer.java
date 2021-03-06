

import repository.*;
import service.*;
import utils.AbstractServer;
import utils.ObjectConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;


public class StartObjectServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
       Properties serverProps=new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        EmployeeRepo userRepo=new EmployeeRepo(serverProps);
        ArtistRepo artistRepo=new ArtistRepo(serverProps);
        FestivalRepo festivalRepo=new FestivalRepo(serverProps);
        TicketRepo ticketRepo=new TicketRepo(serverProps);
        MasterRepo masterRepo = new MasterRepo(serverProps);

        EmployeeService userService=new EmployeeService(userRepo);
        ArtistService artistService=new ArtistService(artistRepo);
        FestivalService festivalService=new FestivalService(festivalRepo,artistRepo);
        TicketService ticketService=new TicketService(ticketRepo,festivalRepo);
        MainPageService mainPageService=new MainPageService(artistService,festivalService,ticketService,userService);

        LoginService loginService=new LoginService(new AccountRepo(serverProps));
        IServices services=new ServicesImpl(loginService, mainPageService, masterRepo);
        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+serverPort);
        int threadCount = Integer.parseInt(args[0]);
        System.out.println("Using " + threadCount + " threads");

        AbstractServer server = new ObjectConcurrentServer(serverPort, threadCount, services);
        try {
                server.start();
        } catch (ServerException e) {
                System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
