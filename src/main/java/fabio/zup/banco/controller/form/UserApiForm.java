package fabio.zup.banco.controller.form;

import fabio.zup.banco.config.validation.ContactEmailConstraint;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserApiForm {

    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordAuthenticationToken convertFormToTokenAuth(){
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
