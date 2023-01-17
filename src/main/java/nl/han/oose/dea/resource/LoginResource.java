package nl.han.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.dto.outgoing.LoginResponseDTO;
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

    /**
     * Authenticates and generates a token with the given username and password
     *
     * @param user the user that is trying to login
     * @return a status 200 "OK" code with a {@link LoginResponseDTO} as entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO user) {
        System.out.println("Login? " + user.getUser() + user.getPassword());
        return Response.ok(loginService.authenticateAndGenerateToken(user)).build();
    }
}
