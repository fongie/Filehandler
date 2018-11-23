package integration;

import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public class UserDAO extends DAO {
   public UserDAO(EntityManagerFactory emf) {
      super(emf);
   }
   public boolean register(String username, String password) {
      //will throw java.sql.SQLIntegrityConstraintViolationException on duplicate username
      try {
         EntityManager entityManager = begin();
         User newUser = new User(username, password);
         entityManager.persist(newUser);
         commit();
         return true;
      } catch (Exception e) {
         handleSQLException(e);
         return false;
      } finally {
         closeLocalManager();
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

   public User findUser(String username) {
      try {
         EntityManager entityManager = begin();
         User user = entityManager.createNamedQuery("findUser", User.class)
               .setParameter("userName", username)
               .getSingleResult();
         return user;
      } catch (NoResultException e) {
         System.err.println("No such user");
         return null;
      } finally {
         commit();
      }
   }
}
