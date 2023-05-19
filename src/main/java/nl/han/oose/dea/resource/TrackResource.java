package nl.han.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.TrackService;

@Path("/playlists/{playlistId}/tracks")
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
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAvailableTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId) {
        return Response.ok(trackService.getAvailableTracks(playlistId, token)).build();
    }

    /**
     * Get all track from the selected playlist
     *
     * @param token the user token
     * @param id the id of the playlist
     * @return a status 200 "OK" with {@link TrackResponseDTO} as entity
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int id) {
        return Response.ok(trackService.getAllTracksFromPlaylist(token, id)).build();
    }

    /**
     * Adds the given track to the selected playlist
     *
     * @param id the id of the playlist
     * @param track the selected track
     * @param token the user token
     * @return a status 200 "OK" with {@link TrackResponseDTO} as entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("playlistId") int id, TrackDTO track, @QueryParam("token") String token) {
        return Response.ok(trackService.addTrackToPlaylist(id, track, token)).build();
    }

    /**
     * Deletes the selected track from the selected playlist
     *
     * @param playlistId the id of the playlist
     * @param trackId the id of the track
     * @param token the user id
     * @return a status 204 "No Content" with {@link TrackResponseDTO} as entity
     */
    @DELETE
    @Path("/{trackId}")
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        trackService.deleteTrackFromPlaylist(playlistId, trackId, token);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
