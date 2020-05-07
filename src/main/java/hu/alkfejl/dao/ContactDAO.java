package hu.alkfejl.dao;

import hu.alkfejl.model.Contact;

import java.util.List;

public interface ContactDAO {

    List<Contact> listAll();

    boolean add(Contact c);

    public boolean delete(Contact c);

    boolean update(Contact c);

}
