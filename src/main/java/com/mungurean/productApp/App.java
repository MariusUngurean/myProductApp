package com.mungurean.productApp;

import com.mungurean.productApp.module.DAOImpl;
import com.mungurean.productApp.service.Service;
import com.mungurean.productApp.view.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("projectDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        DAOImpl dao = new DAOImpl(entityManager);
        Service service = new Service(dao, entityManager);
        View view = new View(service);

        view.run();
    }
}
