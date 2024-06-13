package com.distribuida.servicios;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
@Named
@ApplicationScoped
public class BookServiceImplementacion implements IBookService {

    @Inject
    EntityManager em;

    @Override
    public Book buscarId(Integer id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> buscarLibros() {
        TypedQuery<Book> myQuery = em.createQuery("SELECT b FROM Book b ORDER BY b.id ASC", Book.class);
        return myQuery.getResultList();
    }

    @Override
    public void insertar(Book book) {
        var transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(book);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void modificar(Book book) {

        var transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(book);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void eliminar(Integer id) {
        var transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(buscarId(id));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
