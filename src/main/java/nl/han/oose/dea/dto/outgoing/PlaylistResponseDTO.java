package nl.han.oose.dea.dto.outgoing;

import nl.han.oose.dea.dto.incoming.PlaylistDTO;

import java.util.List;

public class PlaylistResponseDTO {
    private List<PlaylistDTO> playlists;
    private int length;

    public PlaylistResponseDTO(){

    }
    public PlaylistResponseDTO(List<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public List<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
