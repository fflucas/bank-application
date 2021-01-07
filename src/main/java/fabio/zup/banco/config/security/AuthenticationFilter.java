package fabio.zup.banco.config.security;

import fabio.zup.banco.model.UserApi;
import fabio.zup.banco.service.UserApiService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final UserApiService userApiService;

    public AuthenticationFilter(TokenUtil tokenUtil, UserApiService userApiService) {
        this.tokenUtil = tokenUtil;
        this.userApiService = userApiService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenUtil.retrieveToken(request);
        if (token != null) {
            boolean valid = tokenUtil.isValidToken(token);
            if (valid)
                authenticatedUser(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticatedUser(String token) {
        Long userId = tokenUtil.getUserId(token);
        //noinspection OptionalGetWithoutIsPresent
        UserApi userApi = userApiService.findById(userId).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userApi, null, userApi.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
