import domain.*;
import domain.validators.ValidationException;
import repository.AccountRepo;
import repository.MasterRepo;
import service.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServicesImpl implements IServices {

    private LoginService loginService;
    private MainPageService mainPageService;
    private Map<String,IObserver> loggedClients;
    private MasterRepo masterRepo;

    public ServicesImpl(LoginService loginService, MainPageService mainPageService, MasterRepo masterRepo) {
        this.loginService=loginService;
        this.mainPageService=mainPageService;
        loggedClients=new ConcurrentHashMap<>();
        this.masterRepo = masterRepo;
    }


    private final int defaultThreadsNo=5;

    public synchronized Account login(Account user, IObserver client) throws ServiceException, BadCredentialsException {
        System.out.println("entered login servicesImpl");
        Account userR=loginService.getAccount(user.getUsername(),user.getPassword());
        if (userR!=null){
            if(loggedClients.get(user.getId())!=null)
                throw new ServiceException("User already logged in.");

            loggedClients.put(user.getId(), client);
            return userR;
        }else
            throw new ServiceException("Authentication failed.");
    }

    @Override
    public synchronized Iterable<FestivalDTO> searchByDate(Date date) throws ServiceException {
        if(date==null)
            return mainPageService.getFestivals();
        else
            return mainPageService.getFestivalsByDate(date);
    }

    @Override
    public synchronized void sellTicket(Integer festivalID, Long seats, String client) throws ServiceException {
        try {
            mainPageService.sellTicket(festivalID.longValue(),seats,client);
            loggedClients.forEach((x,y)->{
                try {
                    y.ticketsSold(new TicketDTO(festivalID,seats,client));
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            });
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public synchronized void logout(Account user, IObserver client) throws ServiceException {
        System.out.println("entered logout ServicesImpl");
        IObserver localClient=loggedClients.remove(user.getId());
        if (localClient==null)
            throw new ServiceException("User "+user.getId()+" is not logged in.");
    }

    @Override
    public synchronized void vanzare(Vanzare vanzare) throws ServiceException {
        // SELECT *from Vanzari inner join VanzariLocuri VL on Vanzari.id_vanzare = VL.id_vanzare where id_spectacol=? and nr_loc=?;
        List<Integer> locuriVandute=masterRepo.getLocuriVandute(vanzare.getID_Spectacol());
        for(Integer locDorit : vanzare.getLista_locuri_vandute()){
            if(locuriVandute.contains(locDorit)){
                throw new ServiceException("Loc existent!");
            }
        }
        masterRepo.addVanzare(vanzare);
    }
}
