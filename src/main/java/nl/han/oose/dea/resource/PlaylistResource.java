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



}
