package nl.han.oose.dea.rest.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.rest.services.PlaylistService;
import nl.han.oose.dea.rest.services.dto.PlaylistDTO;
import nl.han.oose.dea.rest.services.dto.TrackDTO;

@Path("/playlists")
public class PlaylistResource {
    private PlaylistService playlistService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllPlaylists(@QueryParam("token") String token) {
        return Response.ok(this.playlistService.getPlaylists(token)).build();
    }



    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") int id, String token) {
        playlistService.deletePlaylist(id, token);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO p) {
        playlistService.addPlaylist(token, p);
        return Response.ok(playlistService.getPlaylists(token)).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Path("/{id}")
    public Response updatePlaylist(@PathParam("id") int id, PlaylistDTO p, @QueryParam("token") String token) {
        return Response.ok(playlistService.updatePlaylist(id, p.getName(), token)).build();
    }

    //TODO publicationDate should be a String representation of a Date, formatted as MM-dd-yyyy
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/tracks")
    @GET
    public Response getAllTracksFromPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        return Response.ok(playlistService.getAllTracksFromPlaylist(token, id)).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/tracks")
    @POST
    public Response addTrackToPlaylist(@PathParam("id") int id, TrackDTO track, String token) {
        return Response.ok(playlistService.addTrackToPlaylist(id, track, token)).build();
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        playlistService.deleteTrackFromPlaylist(playlistId, trackId, token);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
