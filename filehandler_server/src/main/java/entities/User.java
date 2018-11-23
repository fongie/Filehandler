package entities;



import javax.persistence.*;

@NamedQueries({
      @NamedQuery(
            name = "loginAccount",
            query = "SELECT COUNT(*) FROM User usr WHERE usr.name LIKE :userName AND usr.password LIKE :password"
      ),
      @NamedQuery(
            name = "findUser",
            query = "SELECT usr FROM User usr WHERE usr.name LIKE :userName"
      )
})

@Entity
@Table(name = "user")
public class User {
   @Id
   @Column(name="name", nullable = false)
   private String name;

   @Column(name="password", nullable = false)
   private String password;

   public User() {

   }

   public User(String name, String password) {
      this.name = name;
      this.password = password;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
