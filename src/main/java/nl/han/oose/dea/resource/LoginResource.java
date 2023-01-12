package nl.han.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.services.LoginService;
import nl.han.oose.dea.dto.incoming.UserDTO;

@Path("/login")
public class LoginResource {
    private LoginService loginService;

    public LoginResource() {

    }

    @Inject
    public void setLoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO u) {
        System.out.println("Login? " + u.getUser() + u.getPassword());
        return Response.ok(loginService.authenticateAndGenerateToken(u)).build();
    }
}
