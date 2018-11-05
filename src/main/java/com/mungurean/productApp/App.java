package com.mungurean.productApp;

import com.mungurean.productApp.service.DaoService;
import com.mungurean.productApp.view.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("projectDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        DaoService daoService = new DaoService();
        View view = new View(daoService);

        view.run();
    }
}
