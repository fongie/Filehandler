package integration;

import entities.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class FileDAO extends DAO {
   public FileDAO(EntityManagerFactory emf) {
      super(emf);
   }

   public List<File> listAllFiles() {
      try {
         EntityManager entityManager = begin();
         List<File> fileList = entityManager.createNamedQuery("listAllFiles", File.class)
               .getResultList();
         commit();
         return fileList;
      } catch (NoResultException e) {
         return new ArrayList<File>();
      } finally {
         closeLocalManager();
      }
   }

   public void create(File file) {
      try {
         EntityManager entityManager = begin();
         entityManager.persist(file);
         commit();
      } catch (Exception e) {
         handleSQLException(e);
      } finally {
         closeLocalManager();
      }
   }


   public void delete(File file) {

   }

   //TODO update?
}