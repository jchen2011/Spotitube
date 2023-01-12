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


public class PlaylistService extends UserAuth {
    private PlaylistDAO playlistDAO;
    private UserDAO userDAO;

    public PlaylistService() {

    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public PlaylistResponseDTO getPlaylists(String token) {
        if (checkIfTokenExists(token)) {
            int userId = userDAO.getUserId(token);
            return playlistDAO.getAllPlaylists(userId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    public PlaylistResponseDTO addPlaylist(String token, PlaylistDTO p) {
        if (checkIfTokenExists(token)) {
            int userId = userDAO.getUserId(token);
            playlistDAO.addPlaylist(userId, p.getName());
            return playlistDAO.getAllPlaylists(userId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    public PlaylistResponseDTO deletePlaylist(int playlistId, String token) {
        if (checkIfTokenExists(token)) {
            int userId = userDAO.getUserId(token);
            playlistDAO.deletePlaylist(playlistId);
            return playlistDAO.getAllPlaylists(userId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    public TrackResponseDTO getAllTracksFromPlaylist(String token, int playlistId) {
        if (checkIfTokenExists(token)) {
            TrackResponseDTO tracks = new TrackResponseDTO(playlistDAO.getAllTracksById(playlistId));
            return tracks;
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    public PlaylistResponseDTO updatePlaylist(int playlistId, String name, String token) {
        if (checkIfTokenExists(token)) {
            int userId = userDAO.getUserId(token);
            playlistDAO.updatePlaylist(playlistId, name);
            return playlistDAO.getAllPlaylists(userId);
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }

    public TrackResponseDTO deleteTrackFromPlaylist(int playlistId, int trackId, String token) {
        if (checkIfTokenExists(token)) {
            playlistDAO.deleteTrackFromPlaylist(playlistId, trackId);
            return new TrackResponseDTO(playlistDAO.getAllTracksById(playlistId));
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }



    public TrackResponseDTO addTrackToPlaylist(int playlistId, TrackDTO track, String token) {
        if (checkIfTokenExists(token)) {
            playlistDAO.addTrackToPlaylist(playlistId, track);
            return new TrackResponseDTO(playlistDAO.getAllTracksById(playlistId));
        } else {
            throw new TokenDoesNotExistException("The token does not exist in the database");
        }
    }
}
