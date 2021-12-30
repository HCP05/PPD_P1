package service;

import domain.Account;
import domain.FestivalDTO;
import domain.Vanzare;
//TODO User=username, password, name + delete customer
import java.sql.Date;

public interface IServices {
    Account login(Account user,IObserver client) throws ServiceException, BadCredentialsException;
    Iterable<FestivalDTO> searchByDate(Date date) throws ServiceException;
    void notifyServerStoped() throws ServiceException;
    void logout(Account user, IObserver client) throws ServiceException;
    void vanzare(Vanzare vanzare) throws ServiceException;
    void verificare();
}
