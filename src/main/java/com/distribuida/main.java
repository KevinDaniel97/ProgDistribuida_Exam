package com.distribuida;

import com.distribuida.rest.BookRest;
import io.helidon.webserver.WebServer;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;


public class main {

    private static ContainerLifecycle lifecycle = null;

    public static void main(String[] args) {
        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);

        BeanManager beanManager = lifecycle.getBeanManager();
        Bean<?> bean = beanManager.getBeans("bookRest").iterator().next();

        BookRest bookRest = (BookRest) beanManager.getReference(bean, BookRest.class, beanManager.createCreationalContext(bean));
        WebServer server = WebServer.builder()
                .port(8080)
                .routing(builder -> builder
                        .post("/books", bookRest::crear)
                        .delete("/books/{id}", bookRest::eliminar)
                        .get("/books/{id}", bookRest::buscarId)
                        .get("/books", bookRest::listarLibros)
                        .put("/books/{id}", bookRest::modificar)
                )
                .build();

        server.start();
        // Agrega un hook de apagado para detener el contenedor CDI
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (lifecycle != null) {
                lifecycle.stopApplication(null);
            }
        }));

    }

}
