package integration;

import entities.File;
import model.ReadableFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class FileDAO extends DAO {
   public FileDAO(EntityManagerFactory emf) {
      super(emf);
   }

   public List<ReadableFile> listAllFiles() {
      return null;
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