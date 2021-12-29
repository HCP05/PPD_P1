package service;

import domain.*;

import java.sql.Date;

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
    public void notifyServerStoped() {

    }

    @Override
    public void logout(Account user, IObserver client) {
    }

    @Override
    public void vanzare(Vanzare vanzare) throws ServiceException {
    }
}
