package com.distribuida.configuracion;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class jpa {
    EntityManagerFactory emf;

    @PostConstruct
    public void init() {
        System.out.println("init");
        emf = Persistence.createEntityManagerFactory("pu-distribuida");
    }

    @Produces
    public EntityManager em() {
        System.out.println("JPA CONFIG::em");
        return emf.createEntityManager();
    }
}

