package hu.alkfejl.controller;

import hu.alkfejl.dao.ContactDAO;
import hu.alkfejl.dao.ContactDAOImpl;
import hu.alkfejl.model.Contact;

import java.util.List;
import java.util.Optional;

public class ContactController {

    private ContactDAO dao = new ContactDAOImpl();
    private static ContactController single_instance = null;

    private ContactController() {

    }

    public static ContactController getInstance(){
        if(single_instance == null){
            single_instance = new ContactController();
        }
        return single_instance;
    }

    public boolean add(Contact contact) {
        return dao.add(contact);
    }

    public List<Contact> getAll() {
        return dao.listAll();
    }

    public boolean delete(Contact p){
        return dao.delete(p);
    }

    public boolean update(Contact p) {
        return dao.update(p);
    }
}
