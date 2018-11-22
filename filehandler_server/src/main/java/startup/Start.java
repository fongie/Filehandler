package startup;

import model.File;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Start {
   public static void main(String[] args) {
      System.out.print("HELLO");

      EntityManagerFactory ef = Persistence.createEntityManagerFactory("FilehandlerPersistence");
      EntityManager em = ef.createEntityManager();

      EntityTransaction t = em.getTransaction();
      t.begin();

      User u = new User("Max", "max");
      em.persist(u);
      t.commit();

      EntityManager em2 = ef.createEntityManager();
      EntityTransaction t2 = em2.getTransaction();
      t2.begin();

      File f = new File("fil", 5, true,u);
      em2.persist(f);
      t2.commit();

   }
}
