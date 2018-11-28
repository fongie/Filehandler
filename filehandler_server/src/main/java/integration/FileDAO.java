package integration;

import common.NoSuchFileException;
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

   public File findByName(String name) throws NoSuchFileException {
      try {
         EntityManager entityManager = begin();
         File file = entityManager.createNamedQuery("findByName", File.class)
               .setParameter("fileName", name)
               .getSingleResult();
         return file;
      } catch (NoResultException e) {
         System.err.println("No such file");
         throw new NoSuchFileException();
      } finally {
         commit();
      }

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

   public void delete(String fileName) {
      EntityManager entityManager = begin();
      entityManager.createNamedQuery("deleteByName")
            .setParameter("fileName", fileName)
            .executeUpdate();
      commit();
   }

   //TODO update?
}