package util;

import models.ModelCat;
import models.ModelCatColor;
import models.ModelCatType;
import models.ModelOwner;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateSessionFactory {
    private static SessionFactory _instance = null;
    
    private HibernateSessionFactory() {}
    
    public static SessionFactory getSessionFactory() {
        if (_instance == null) {
            try {
                var configuration = new Configuration().configure();
                
                configuration.addAnnotatedClass(ModelCat.class);
                configuration.addAnnotatedClass(ModelOwner.class);
                configuration.addAnnotatedClass(ModelCatType.class);
                configuration.addAnnotatedClass(ModelCatColor.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                
                _instance = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                System.out.println("Initial session factory failed:\n" + e);
                throw e;
            }
        }
        return _instance;
    }   
}
