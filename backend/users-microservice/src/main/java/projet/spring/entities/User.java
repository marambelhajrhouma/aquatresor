package projet.spring.entities;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {	
	@Id 
	@GeneratedValue (strategy=GenerationType.IDENTITY) 
	private Long user_id;

 @Column(unique=true)
	private String username;
	private String password;
	private Boolean enabled;
	private String email;
	
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id") , 
			   inverseJoinColumns = @JoinColumn(name="role_id")) 
	private List<Role> roles; 
}