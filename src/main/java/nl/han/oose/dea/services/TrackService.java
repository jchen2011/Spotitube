package nl.han.oose.dea.services;

import jakarta.inject.Inject;
import nl.han.oose.dea.datasource.PlaylistDAO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.utils.UserAuth;

public class TrackService extends UserAuth {
    private PlaylistDAO playlistDAO;

    @Inject
    public TrackService(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    public TrackResponseDTO getAvailableTracks(int playlistId, String token) {
        return playlistDAO.getAvailableTracks(playlistId);
    }
}
