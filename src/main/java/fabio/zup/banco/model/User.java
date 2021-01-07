package fabio.zup.banco.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class User{
    
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String cpf;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    public User(String name, String email, String cpf, LocalDate DateOfBirth) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.dateOfBirth = DateOfBirth;
    }

	public User() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
