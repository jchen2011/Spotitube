package nl.han.oose.dea.resource.exceptionmappers;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.han.oose.dea.services.exceptions.InternalServerError;

@Provider
public class InternalServerErrorMapper implements ExceptionMapper<InternalServerError> {

    @Override
    public Response toResponse(InternalServerError error) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error.getMessage())
                .build();
    }
}
