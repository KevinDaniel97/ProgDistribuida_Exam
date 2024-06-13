package com.distribuida.servicios;

import com.distribuida.db.Book;

import java.util.List;

public interface IBookService {

    public Book buscarId(Integer id);

    public List<Book> buscarLibros();

    public void insertar(Book book);

    public void modificar(Book book);

    public void eliminar(Integer id);


}
