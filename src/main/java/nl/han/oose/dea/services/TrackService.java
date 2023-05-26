package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.DAO.TrackDAO;
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


}
