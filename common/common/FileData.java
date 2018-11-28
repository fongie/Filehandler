package common;


public class FileData implements ReadableFile {
   private String name;
   private long size;
   private boolean writeable;
   private String ownerName;


   public FileData() {

   }

   public FileData(String name, long size, boolean writeable, String ownerName) {
      this.name = name;
      this.size = size;
      this.writeable = writeable;
      if (!(ownerName == null)) {
         this.ownerName = ownerName.toLowerCase();
      }
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public long getSize() {
      return size;
   }

   public void setSize(long size) {
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
