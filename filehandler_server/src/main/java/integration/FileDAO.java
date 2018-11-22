package integration;

import entities.File;
import entities.ReadableFile;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class FileDAO {
   private EntityManagerFactory emf;
   public FileDAO(EntityManagerFactory emf) {
      this.emf = emf;
   }

   public List<ReadableFile> listAllFiles() {
      return null;
   }

   public void create(File file) {

   }


   public void delete(File file) {

   }

   //TODO update?

}
