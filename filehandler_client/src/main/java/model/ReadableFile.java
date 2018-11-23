package model;

import java.io.Serializable;

public interface ReadableFile {
   String getName();
   int getSize();
   boolean isWriteable();
   String getOwnerName();
}
