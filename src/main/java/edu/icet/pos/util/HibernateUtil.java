package edu.icet.pos.util;

import edu.icet.pos.entity.*;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static Transaction singletonTransaction;
    private static Session singletonSession;

    private HibernateUtil(){}

    private static SessionFactory buildSessionFactory(){
        try{
            StandardServiceRegistry build = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            Metadata metadata = new MetadataSources(build)
                    .addAnnotatedClass(UserRoleEntity.class)
                    .addAnnotatedClass(UserEntity.class)
                    .addAnnotatedClass(CategoryEntity.class)
                    .addAnnotatedClass(SubCategoryEntity.class)
                    .addAnnotatedClass(SupplierEntity.class)
                    .addAnnotatedClass(ProductEntity.class)
                    .addAnnotatedClass(InventoryEntity.class)
                    .addAnnotatedClass(JobRoleEntity.class)
                    .addAnnotatedClass(EmployeeEntity.class)
                    .addAnnotatedClass(OrderEntity.class)
                    .addAnnotatedClass(OrderDetailEntity.class)
                    .getMetadataBuilder()
                    .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                    .build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return null;
    }

    public static Session getSession(){
        assert sessionFactory != null;
        return sessionFactory.openSession();
    }

    public static Session getSingletonSession(){
        if(singletonSession == null || !singletonSession.isOpen()){
            singletonSession = getSession();
        }
        return singletonSession;
    }

    public static void singletonSessionClose(){
        singletonSession.close();
    }

    public static void singletonBegin(){
        singletonTransaction = singletonSession.beginTransaction();
    }

    public static void singletonCommit(){
        singletonTransaction.commit();
    }

    public static void singletonRollback(){
        singletonTransaction.rollback();
    }
}
