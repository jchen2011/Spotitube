package nl.han.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.dto.outgoing.PlaylistResponseDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.PlaylistService;
import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.incoming.TrackDTO;

@Path("/playlists")
public class PlaylistResource {
    private PlaylistService playlistService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    /**
     * Get all the playlists from the user
     *
     * @param token the user token
     * @return a status 200 "OK" with {@link PlaylistResponseDTO} as entity
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        return Response.ok(this.playlistService.getPlaylists(token)).build();
    }

    /**
     * Deletes the selected playlist from the user playlists
     *
     * @param id the id of the playlist
     * @param token the user token
     * @return a status 200 "OK" with {@link PlaylistResponseDTO} as entity
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        return Response.ok(playlistService.deletePlaylist(id, token)).build();
    }

    /**
     * Adds a playlist with a given name to the user playlists
     *
     * @param token the user token
     * @param playlist the new playlist that is going to be added
     * @return a status 200 "OK" with {@link PlaylistResponseDTO} as entity
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlist) {
        playlistService.addPlaylist(token, playlist);
        return Response.ok(playlistService.getPlaylists(token)).build();
    }

    /**
     * Updates the selected playlist with a given name
     *
     * @param id the id of the playlist that is going to be changed
     * @param playlist the playlist with the new name
     * @param token the user id
     * @return a status 200 "OK" with {@link PlaylistResponseDTO} as entity
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlaylist(@PathParam("id") int id, PlaylistDTO playlist, @QueryParam("token") String token) {
        return Response.ok(playlistService.updatePlaylist(id, playlist.getName(), token)).build();
    }

    /**
     * Get all track from the selected playlist
     *
     * @param token the user token
     * @param id the id of the playlist
     * @return a status 200 "OK" with {@link TrackResponseDTO} as entity
     */
    @GET
    @Path("/{playlistId}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int id) {
        return Response.ok(playlistService.getAllTracksFromPlaylist(token, id)).build();
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
    @Path("/{playlistId}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("playlistId") int id, TrackDTO track, @QueryParam("token") String token) {
        return Response.ok(playlistService.addTrackToPlaylist(id, track, token)).build();
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
    @Path("/{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        playlistService.deleteTrackFromPlaylist(playlistId, trackId, token);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
