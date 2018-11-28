package common;


import java.io.Serializable;

public interface WriteableFile extends Serializable {
   String getName();
   int getSize();
   boolean isWriteable();
   String getOwnerName();

   void setName(String name);
   void setSize(int size);
   void setWriteable(boolean writeable);
}
