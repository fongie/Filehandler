package integration;


import entities.File;
import entities.User;

public class FileHandler {
   private FileDAO fileDAO;

   public FileHandler(FileDAO fileDAO) {
      this.fileDAO = fileDAO;
   }

   public void download() { //might want ReadableFile interface (or WritableFile??) to avoid manipulating entities

   }
   public void upload(FileData fileData, User owner) {
      File file = new File(fileData.getName(), fileData.getSize(), fileData.isWriteable(), owner);
      fileDAO.create(file);

      //TODO persist in a file on server
   }

   public void delete() {

   }
}
