package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.PlaylistDAO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.exceptions.TokenDoesNotExistException;
import nl.han.oose.dea.utils.UserAuth;

public class TrackService {
    private UserAuth userAuth;
    private PlaylistDAO playlistDAO;

    @Inject
    public void setTrackService(PlaylistDAO playlistDAO, UserAuth userAuth) {
        this.playlistDAO = playlistDAO;
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
            return playlistDAO.getAvailableTracks(playlistId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }
}
