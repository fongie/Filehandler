package integration;

import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Contains methods used by all DAOs
 */
class DAO {
   protected EntityManagerFactory emf;
   protected ThreadLocal<EntityManager> threadLocalManager = new ThreadLocal<EntityManager>();

   public DAO(EntityManagerFactory emf) {
      this.emf = emf;
   }

   protected void handleSQLException(Exception e) {
      Throwable t = e.getCause();
      while ((t != null) && !(t instanceof ConstraintViolationException)) {
         t = t.getCause(); //cant catch this exception right away apparently, have to loop through layers of exceptions to see what comes last
      }
      if (t instanceof ConstraintViolationException) {
         System.out.println("DUPLICATE");
      } else {
         e.printStackTrace();
      }
   }

   protected EntityManager begin() {
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

   protected void commit() {
      EntityManager manager = threadLocalManager.get();
      manager.getTransaction().commit();
   }

   protected void closeLocalManager() {
      threadLocalManager.get().close();
   }
}
