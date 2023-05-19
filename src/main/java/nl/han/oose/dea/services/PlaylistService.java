package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.PlaylistDAO;
import nl.han.oose.dea.datasource.UserDAO;
import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.outgoing.PlaylistResponseDTO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.exceptions.TokenDoesNotExistException;
import nl.han.oose.dea.utils.UserAuth;


public class PlaylistService {
    private UserAuth userAuth;
    private PlaylistDAO playlistDAO;
    private UserDAO userDAO;

    public PlaylistService() {

    }

    @Inject
    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Gets all the playlist from the database
     *
     * @param token the user token
     * @return the playlists and length as a {@link PlaylistResponseDTO}
     * @throws TokenDoesNotExistException the token does not exist
     */
    public PlaylistResponseDTO getPlaylists(String token) {
        if (userAuth.checkIfTokenExists(token)) {
            int userId = userDAO.getUserId(token);
            return playlistDAO.getAllPlaylists(userId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    /**
     * Adds the playlist with a new name to the database
     *
     * @param token the user token
     * @param playlist the playlist with a new name
     * @return the playlists and length as a {@link PlaylistResponseDTO}
     * @throws TokenDoesNotExistException the token does not exist
     */
    public PlaylistResponseDTO addPlaylist(String token, PlaylistDTO playlist) {
        if (userAuth.checkIfTokenExists(token)) {
            int userId = userDAO.getUserId(token);
            playlistDAO.addPlaylist(userId, playlist.getName());
            return playlistDAO.getAllPlaylists(userId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    /**
     * Deletes the selected playlist from the database
     *
     * @param playlistId the id of the playlist that is going to be deleted
     * @param token the user token
     * @return the playlists and length as a {@link PlaylistResponseDTO}
     * @throws TokenDoesNotExistException the token does not exist
     */
    public PlaylistResponseDTO deletePlaylist(int playlistId, String token) {
        if (userAuth.checkIfTokenExists(token)) {
            int userId = userDAO.getUserId(token);
            playlistDAO.deletePlaylist(playlistId);
            return playlistDAO.getAllPlaylists(userId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    /**
     * Updates the selected playlist name in the database
     *
     * @param playlistId the id of the playlist that is going to be changed
     * @param name the new name for the playlist
     * @param token the user token
     * @return the playlists and length as a {@link PlaylistResponseDTO}
     * @throws TokenDoesNotExistException the token does not exist
     */
    public PlaylistResponseDTO updatePlaylist(int playlistId, String name, String token) {
        if (userAuth.checkIfTokenExists(token)) {
            int userId = userDAO.getUserId(token);
            playlistDAO.updatePlaylist(playlistId, name);
            return playlistDAO.getAllPlaylists(userId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }
}
