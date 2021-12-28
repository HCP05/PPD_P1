package service;

import domain.Account;
import domain.Festival;
import domain.FestivalDTO;
import domain.Vanzare;
//TODO User=username, password, name + delete customer
import java.sql.Date;
import java.util.List;

public interface IServices {
    Account login(Account user,IObserver client) throws ServiceException, BadCredentialsException;
    Iterable<FestivalDTO> searchByDate(Date date) throws ServiceException;
    void sellTicket(Integer festivalID, Long seats, String client) throws ServiceException;
    void logout(Account user, IObserver client) throws ServiceException;
    void vanzare(Vanzare vanzare) throws ServiceException;
}
