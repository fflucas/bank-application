package fabio.zup.banco.controller.form;

import fabio.zup.banco.config.validation.ContactEmailConstraint;
import fabio.zup.banco.model.User;
import fabio.zup.banco.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;


public class UserForm {
	
	@NotNull @NotEmpty @Length(min = 4)
    private String name;
	
	@NotNull @NotEmpty @ContactEmailConstraint
    private String email;
	
	@NotNull @NotEmpty @CPF
    private String cpf;
    
	@NotNull @Past @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dateOfBirth;

    public UserForm(String name, String email, String cpf, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
    }
    
    public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public User convertFormToEntity() {
        return new User(name, email, cpf, dateOfBirth);
    }

	public User update(Long id, UserService userService) {
		User user = userService.getOne(id);
		user.setName(name);
		user.setEmail(email);
		user.setCpf(cpf);
		user.setDateOfBirth(dateOfBirth);
		return user;
	}
}
