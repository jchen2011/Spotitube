package nl.han.oose.dea.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token) {
        return Response.ok(this.playlistService.getPlaylists(token)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        return Response.ok(playlistService.deletePlaylist(id, token)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO p) {
        playlistService.addPlaylist(token, p);
        return Response.ok(playlistService.getPlaylists(token)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlaylist(@PathParam("id") int id, PlaylistDTO p, @QueryParam("token") String token) {
        return Response.ok(playlistService.updatePlaylist(id, p.getName(), token)).build();
    }

    //TODO publicationDate should be a String representation of a Date, formatted as MM-dd-yyyy
    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksFromPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        return Response.ok(playlistService.getAllTracksFromPlaylist(token, id)).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int id, TrackDTO track, @QueryParam("token") String token) {
        return Response.ok(playlistService.addTrackToPlaylist(id, track, token)).build();
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        playlistService.deleteTrackFromPlaylist(playlistId, trackId, token);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
