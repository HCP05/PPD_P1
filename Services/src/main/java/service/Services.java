package service;

import domain.*;

import java.sql.Date;
import java.util.List;

public class Services implements IServices {
    MainPageService mainPageService;

    public Services(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @Override
    public Account login(Account account, IObserver client) {
        return null;
    }

    @Override
    public Iterable<FestivalDTO> searchByDate(Date date) {
        return null;
    }

    @Override
    public void sellTicket(Integer festivalID, Long seats, String client) {

    }

    @Override
    public void logout(Account user, IObserver client) {
    }

    @Override
    public void vanzare(Vanzare vanzare) throws ServiceException {
    }
}
