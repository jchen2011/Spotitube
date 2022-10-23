package nl.han.oose.dea.rest.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.rest.datasource.PlaylistDAO;
import nl.han.oose.dea.rest.datasource.UserDAO;
import nl.han.oose.dea.rest.services.dto.PlaylistDTO;
import nl.han.oose.dea.rest.services.dto.PlaylistResponseDTO;
import nl.han.oose.dea.rest.services.dto.TrackDTO;
import nl.han.oose.dea.rest.services.dto.TrackResponseDTO;


public class PlaylistService {
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
        int userId = userDAO.getUserIdByToken(token);
        return playlistDAO.getAllPlaylists(userId);
    }

    public PlaylistResponseDTO addPlaylist(String token, PlaylistDTO p) {
        int userId = userDAO.getUserIdByToken(token);
        playlistDAO.addPlaylist(userId, p.getName());
        return playlistDAO.getAllPlaylists(userId);
    }

    public PlaylistResponseDTO deletePlaylist(int playlistId, String token) {
        int userId = userDAO.getUserIdByToken(token);
        playlistDAO.deletePlaylist(playlistId);
        return playlistDAO.getAllPlaylists(userId);
    }

    public TrackResponseDTO getAllTracksFromPlaylist(String token, int playlistId) {
        if (checkIfTokenExists(token)) {
            TrackResponseDTO tracks = new TrackResponseDTO(playlistDAO.getAllTracksById(playlistId));
            return tracks;
        }
        return null;
    }

    public PlaylistResponseDTO updatePlaylist(int playlistId, String name, String token) {
        int userId = userDAO.getUserIdByToken(token);
        playlistDAO.updatePlaylist(playlistId, name);
        return playlistDAO.getAllPlaylists(userId);
    }

    public TrackResponseDTO deleteTrackFromPlaylist(int playlistId, int trackId, String token) {
        if (checkIfTokenExists(token)) {
            playlistDAO.deleteTrackFromPlaylist(playlistId, trackId);
            return new TrackResponseDTO(playlistDAO.getAllTracksById(playlistId));
        }
        return null;
    }

    public boolean checkIfTokenExists(String token) {
        return userDAO.getAllTokens().contains(token);
    }

    public TrackResponseDTO addTrackToPlaylist(int playlistId, TrackDTO track, String token) {
        if (checkIfTokenExists(token)) {
            playlistDAO.addTrackToPlaylist(playlistId, track);
            return new TrackResponseDTO(playlistDAO.getAllTracksById(playlistId));
        }
        return null;
    }
}
