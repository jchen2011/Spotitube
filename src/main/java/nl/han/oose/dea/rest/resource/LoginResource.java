package nl.han.oose.dea.rest.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.rest.services.LoginService;
import nl.han.oose.dea.rest.services.dto.UserDTO;

@Path("/login")
public class LoginResource {
    private LoginService loginService;

    public LoginResource() {

    }
    @Inject
    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO u) {
        System.out.println("Login? " + u.getUser() + u.getPassword());
        return Response.ok(loginService.doLogin(u)).build();
    }
}
