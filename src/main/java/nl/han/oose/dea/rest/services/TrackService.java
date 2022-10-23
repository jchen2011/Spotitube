package nl.han.oose.dea.rest.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.rest.datasource.PlaylistDAO;
import nl.han.oose.dea.rest.services.dto.TrackResponseDTO;

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
