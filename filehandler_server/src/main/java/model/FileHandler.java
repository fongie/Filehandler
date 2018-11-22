package model;

import integration.FileDAO;

public class FileHandler {
   private FileDAO fileDAO;

   public FileHandler(FileDAO fileDAO) {
      this.fileDAO = fileDAO;
   }

   public void download() { //might want ReadableFile interface (or WritableFile??) to avoid manipulating entities

   }
   public void upload() {

   }

   public void delete() {

   }
}
