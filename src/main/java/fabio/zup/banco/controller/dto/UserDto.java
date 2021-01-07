package fabio.zup.banco.controller.dto;

import fabio.zup.banco.model.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private LocalDate DateOfBirth;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.DateOfBirth = user.getDateOfBirth();
    }

    public static Page<UserDto> convertEntityToDto(Page<User> users) {
        return users.map(UserDto::new);
    }

    public Long getId() { return id; }

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getCpf() {
		return cpf;
	}

	public LocalDate getDateOfBirth() {
		return DateOfBirth;
	}    
}
