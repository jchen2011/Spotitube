package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.DAO.PlaylistDAO;
import nl.han.oose.dea.datasource.DAO.TrackDAO;
import nl.han.oose.dea.datasource.DAO.UserDAO;
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

    private TrackDAO trackDAO;

    public PlaylistService() {

    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {this.trackDAO = trackDAO;}
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
