package nl.han.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.TrackService;

@Path("/tracks")
public class TrackResource {
    private TrackService trackService;

    public TrackResource() {

    }

    @Inject
    public void setTrackResource(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * Get all the available tracks for a user to add for a given playlist
     *
     * @param token the user token
     * @param playlistId the id of the playlist
     * @return a status 200 "OK" with {@link TrackResponseDTO} as entity
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId) {
        return Response.ok(trackService.getAvailableTracks(playlistId, token)).build();
    }
}
