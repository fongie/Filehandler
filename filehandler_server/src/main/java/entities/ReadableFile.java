package entities;

public interface ReadableFile {
   String getName();
   int getSize();
   boolean isWriteable();
   User getOwner();
}
