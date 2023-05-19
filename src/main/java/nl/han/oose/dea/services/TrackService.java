package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.PlaylistDAO;
import nl.han.oose.dea.datasource.TrackDAO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.exceptions.TokenDoesNotExistException;
import nl.han.oose.dea.utils.UserAuth;

public class TrackService {
    private UserAuth userAuth;
    private TrackDAO trackDAO;

    @Inject
    public void setTrackService(TrackDAO trackDAO, UserAuth userAuth) {
        this.trackDAO = trackDAO;
        this.userAuth = userAuth;
    }

    /**
     * Get all the available tracks in the database.
     *
     * @param playlistId the id of the playlist
     * @param token the user token
     * @return the tracks as a {@link TrackResponseDTO}
     * @throws TokenDoesNotExistException the token does not exist
     */
    public TrackResponseDTO getAvailableTracks(int playlistId, String token) {
        if (userAuth.checkIfTokenExists(token)) {
            return trackDAO.getAvailableTracks(playlistId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    /**
     * Gets all the tracks from the selected playlist in the database.
     *
     * @param token the user token
     * @param playlistId the id of the playlist
     * @return the tracks as a {@link TrackResponseDTO}
     * @throws TokenDoesNotExistException the token does not exist
     */
    public TrackResponseDTO getAllTracksFromPlaylist(String token, int playlistId) {
        if (userAuth.checkIfTokenExists(token)) {
            return new TrackResponseDTO(trackDAO.getAllTracksById(playlistId));
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    /**
     * Deletes the track from the playlist in the database.
     *
     * @param playlistId the id of the playlist
     * @param trackId the id of the track
     * @param token the user token
     * @return the tracks as a {@link TrackResponseDTO}
     * @throws TokenDoesNotExistException the token does not exist
     */
    public TrackResponseDTO deleteTrackFromPlaylist(int playlistId, int trackId, String token) {
        if (userAuth.checkIfTokenExists(token)) {
            trackDAO.deleteTrackFromPlaylist(playlistId, trackId);
            return new TrackResponseDTO(trackDAO.getAllTracksById(playlistId));
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    /**
     * Adds a track to the selected playlist in the database.
     *
     * @param playlistId the id of the playlist
     * @param track the new track information
     * @param token the user token
     * @return the tracks as a {@link TrackResponseDTO}
     * @throws TokenDoesNotExistException the token does not exist
     */
    public TrackResponseDTO addTrackToPlaylist(int playlistId, TrackDTO track, String token) {
        if (userAuth.checkIfTokenExists(token)) {
            trackDAO.addTrackToPlaylist(playlistId, track);
            return new TrackResponseDTO(trackDAO.getAllTracksById(playlistId));
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }
}
