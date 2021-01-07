package fabio.zup.banco.controller;

import fabio.zup.banco.config.security.TokenUtil;
import fabio.zup.banco.controller.dto.TokenDto;
import fabio.zup.banco.controller.form.UserApiForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenUtil tokenUtil) {
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody @Valid UserApiForm form){
        UsernamePasswordAuthenticationToken data = form.convertFormToTokenAuth();
        try{
            Authentication authentication = authenticationManager.authenticate(data);
            String token = tokenUtil.allocateToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }
}
