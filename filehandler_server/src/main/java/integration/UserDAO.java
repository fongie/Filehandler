package integration;

import entities.User;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class UserDAO {

   private EntityManagerFactory emf;
   private ThreadLocal<EntityManager> threadLocalManager = new ThreadLocal<EntityManager>();

   public UserDAO(EntityManagerFactory emf) {
      this.emf = emf;
   }
   public void register(String username, String password) {
      //will throw java.sql.SQLIntegrityConstraintViolationException on duplicate username
      try {
         EntityManager entityManager = begin();
         User newUser = new User(username, password);
         entityManager.persist(newUser);
         commit();
      } catch (Exception e) {
         Throwable t = e.getCause();
         while ((t != null) && !(t instanceof ConstraintViolationException)) {
            t = t.getCause(); //cant catch this exception right away apparently, have to loop through layers of exceptions to see what comes last
         }
         if (t instanceof ConstraintViolationException) {
            System.out.println("DUPLICATE");
         } else {
            e.printStackTrace();
         }
      } finally {
         threadLocalManager.get().close();
      }
   }
   public boolean login(String username, String password) {
      EntityManager entityManager = begin();
      Long res = (Long) entityManager.createNamedQuery("loginAccount")
            .setParameter("userName", username)
            .setParameter("password", password)
            .getSingleResult(); //COUNT queries return Long
      if (res == 1) {
         return true;
      } else {
         return false;
      }
   }

   private EntityManager begin() {
      EntityManager entityManager = emf.createEntityManager();
      //because an entitymanager object should never be shared between threads,
      //threadlocal makes sure that several threads cant override eachother when they are writing
      //to the same manager
      //one transaction, one manager
      //we also dont have to worry about thread synchronization
      threadLocalManager.set(entityManager);
      EntityTransaction entityTransaction = entityManager.getTransaction();
      if (!entityTransaction.isActive()) {
         entityTransaction.begin();
      }
      return entityManager;
   }

   private void commit() {
      EntityManager manager = threadLocalManager.get();
      manager.getTransaction().commit();
   }
}
