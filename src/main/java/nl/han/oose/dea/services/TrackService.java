package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.PlaylistDAO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;

public class TrackService {
    private PlaylistDAO playlistDAO;

    @Inject
    public TrackService(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    public TrackResponseDTO getAvailableTracks(int playlistId, String token) {
        return playlistDAO.getAvailableTracks(playlistId);
    }
}
