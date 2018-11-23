package integration;


import java.io.Serializable;

public class FileData implements Serializable {
   private String name;
   private int size;
   private boolean writeable;
   private String ownerName;


   public FileData() {

   }

   public FileData(String name, int size, boolean writeable, String ownerName) {
      this.name = name;
      this.size = size;
      this.writeable = writeable;
      this.ownerName = ownerName;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getSize() {
      return size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public boolean isWriteable() {
      return writeable;
   }

   public String getOwnerName() {
      return ownerName;
   }

   public void setWriteable(boolean writeable) {
      this.writeable = writeable;
   }
}
