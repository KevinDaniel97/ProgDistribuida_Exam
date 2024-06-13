package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.servicios.IBookService;
import com.google.gson.Gson;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class BookRest {
    @Inject
    static IBookService service;

    static Gson gson = new Gson();

    /*
        {"id":5,"isbn":"isbn5","title":"libro5","price":29.0}
    */
    public  void crear(ServerRequest rq, ServerResponse res) {
        String bstr = rq.content().as(String.class);
        Book book= gson.fromJson(bstr, Book.class);
        service.insertar(book);
        res.send(gson.toJson(book));
    }
    /*
        http://localhost:8080/books/5
    */
    public void eliminar(ServerRequest rq, ServerResponse res) {
        String _id = rq.path().pathParameters().get("id");
        service.eliminar(Integer.valueOf(_id));
        res.send("Eliminado");
    }

    public void buscarId(ServerRequest rq, ServerResponse res) {
        String _id = rq.path().pathParameters().get("id");
        res.send(gson.toJson(service.buscarId(Integer.valueOf(_id))));
    }

    public void listarLibros(ServerRequest rq, ServerResponse res) {
        res.send(gson.toJson(service.buscarLibros()));
    }

    public void modificar(ServerRequest rq, ServerResponse res) {
        String _id = rq.path().pathParameters().get("id");
        String bstr = rq.content().as(String.class);
        Book book = gson.fromJson(bstr, Book.class);
        book.setId(Integer.valueOf(_id));
        service.modificar(book);
        res.send(gson.toJson(book) );
    }
}
