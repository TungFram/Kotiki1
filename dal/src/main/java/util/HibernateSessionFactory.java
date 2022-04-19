package util;

import models.ModelCat;
import models.ModelCatColor;
import models.ModelCatType;
import models.ModelOwner;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static SessionFactory _instance;
    
    private HibernateSessionFactory() {}
    
    public static SessionFactory getSessionFactory() {
        if (_instance == null) {
            try {
                Configuration configuration = new Configuration().configure();
                
                configuration.addAnnotatedClass(ModelCat.class);
                configuration.addAnnotatedClass(ModelOwner.class);
                configuration.addAnnotatedClass(ModelCatType.class);
                configuration.addAnnotatedClass(ModelCatColor.class);
                
                StandardServiceRegistryBuilder builder = 
                        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                
                _instance = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Exception with session factory:\n" + e);
            }
        }
        return _instance;
    }   
}
