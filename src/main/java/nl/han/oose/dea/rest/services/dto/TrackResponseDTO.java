package nl.han.oose.dea.rest.services.dto;

import java.util.List;

public class TrackResponseDTO {
    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    private List<TrackDTO> tracks;

    public TrackResponseDTO(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
