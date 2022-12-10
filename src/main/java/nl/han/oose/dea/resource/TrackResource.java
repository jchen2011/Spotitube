package nl.han.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.services.TrackService;

@Path("/tracks")
public class TrackResource {
    private TrackService trackService;

    public TrackResource() {

    }

    @Inject
    public TrackResource(TrackService trackService) {
        this.trackService = trackService;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAvailableTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int forPlaylist) {
        return Response.ok(trackService.getAvailableTracks(forPlaylist, token)).build();
    }
}
