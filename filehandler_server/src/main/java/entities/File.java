package entities;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File implements ReadableFile {
   @Id
   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "size", nullable = false)
   private int size;

   @Column(name="writeable", nullable = false)
   private boolean writeable;

   @ManyToOne
   @JoinColumn(name="owner")
   private User owner;


   public File() {

   }

   public File(String name, int size, boolean writeable, User owner) {
      this.name = name;
      this.size = size;
      this.writeable = writeable;
      this.owner = owner;
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

   public void setWriteable(boolean writeable) {
      this.writeable = writeable;
   }

   public User getOwner() {
      return owner;
   }

   public void setOwner(User owner) {
      this.owner = owner;
   }
}
