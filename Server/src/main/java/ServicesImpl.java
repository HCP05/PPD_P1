import domain.*;
import repository.MasterRepo;
import service.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class ServicesImpl implements IServices {

    private LoginService loginService;
    private MainPageService mainPageService;
    private Map<String,IObserver> loggedClients;
    private MasterRepo masterRepo;
    private final double SOLD_EPSILON = 0.01;

    List<Double> soldSpectacole;
    List<Double> preturi;
    List<List<Integer>> locuriVandute;
    int nrSpectacole;

    public ServicesImpl(LoginService loginService, MainPageService mainPageService, MasterRepo masterRepo) {
        this.loginService=loginService;
        this.mainPageService=mainPageService;
        loggedClients=new ConcurrentHashMap<>();
        this.masterRepo = masterRepo;

        nrSpectacole = masterRepo.getNrSpectacole();
        locuriVandute = new ArrayList<>();
        soldSpectacole = new ArrayList<>();
        preturi = new ArrayList<>();

        for(int i = 0; i < nrSpectacole; i++) {
            preturi.add((double) masterRepo.getPretBilet(i + 1));
            locuriVandute.add(masterRepo.getLocuriVandute(i + 1));
            soldSpectacole.add(locuriVandute.get(i).size() * preturi.get(i));
        }

        System.out.println(nrSpectacole);
        System.out.println(locuriVandute);
        System.out.println(soldSpectacole);
    }

    private final int defaultThreadsNo=5;

    public synchronized Account login(Account user, IObserver client) throws ServiceException, BadCredentialsException {
//        System.out.println("entered login servicesImpl");
//        Account userR=loginService.getAccount(user.getUsername(),user.getPassword());
//        if (userR!=null){
//            if(loggedClients.get(user.getId())!=null)
//                throw new ServiceException("User already logged in.");

            loggedClients.put(String.valueOf(loggedClients.size()), client);
//            return userR;
//        }else
//            throw new ServiceException("Authentication failed.");
        return null;
    }

    @Override
    public synchronized Iterable<FestivalDTO> searchByDate(Date date) throws ServiceException {
        if(date==null)
            return mainPageService.getFestivals();
        else
            return mainPageService.getFestivalsByDate(date);
    }

    @Override
    public synchronized void notifyServerStoped() throws ServiceException {
        System.out.println("IS IN NOTIFYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        try {
            loggedClients.forEach((x,y)->{
                try {
                    y.serverStoped();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
        int spectacolID = vanzare.getID_Spectacol() - 1;

        List<Integer> locuriDejaVandute=masterRepo.getLocuriVandute(spectacolID + 1);
        for(Integer locDorit : vanzare.getLista_locuri_vandute()){
            if(locuriDejaVandute.contains(locDorit)){
                throw new ServiceException("Loc existent!");
            }
        }

        masterRepo.addVanzare(vanzare);

        for(int loc: vanzare.getLista_locuri_vandute()) {
            locuriVandute.get(spectacolID).add(loc);
            soldSpectacole.set(spectacolID, soldSpectacole.get(spectacolID) +  preturi.get(spectacolID));
        }
    }

    @Override
    public synchronized void verificare() {
        List<List<Integer>> locuriVanduteDB = new ArrayList<>();
        List<Double> soldSpectacoleDB = new ArrayList<>();
        boolean corect = true;

        for(int i = 0; i < nrSpectacole && corect; i++) {
            locuriVanduteDB.add(masterRepo.getLocuriVandute(i + 1));
            soldSpectacoleDB.add(locuriVanduteDB.get(i).size() * preturi.get(i));

            if(Math.abs(soldSpectacoleDB.get(i) - soldSpectacole.get(i)) > SOLD_EPSILON) {
                //System.out.println("### Solduri diferite:\n" + soldSpectacole.get(i) + "\n" + soldSpectacoleDB.get(i) + "\n###");
                corect = false;
            }

            locuriVanduteDB.set(i, locuriVanduteDB.get(i).stream().sorted().collect(Collectors.toList()));
            locuriVandute.set(i, locuriVandute.get(i).stream().sorted().collect(Collectors.toList()));

            for(int j = 0; j < locuriVanduteDB.get(i).size() && corect; j++) {
                if(locuriVanduteDB.get(i).get(j) != locuriVandute.get(i).get(j)) {
                    //System.out.println("### Locuri diferite:\n" + locuriVanduteDB.get(i) + "\n" + locuriVandute.get(i) + "\n###");
                    corect = false;
                }
            }
        }

        try {
            System.out.println("# Start verificare");

            FileWriter fileWriter = new FileWriter("verificari.txt", true);
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("# Verificare " + LocalDateTime.now() + "\n");
            stringBuilder.append("Sold per spectacol: " + soldSpectacole + "\n");
            stringBuilder.append("Locuri vandute: " + locuriVandute + "\n");
            stringBuilder.append("Corect: " + corect + "\n");

            fileWriter.append(stringBuilder.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
