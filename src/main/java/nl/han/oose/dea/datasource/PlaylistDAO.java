package nl.han.oose.dea.datasource;

import jakarta.inject.Inject;
import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.outgoing.PlaylistResponseDTO;
import nl.han.oose.dea.dto.incoming.TrackDTO;


import java.sql.*;
import java.util.List;


public class PlaylistDAO extends DAO<PlaylistDTO>{

    private TrackDAO trackDAO;
    public PlaylistDAO() {

    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    public PlaylistResponseDTO getAllPlaylists(int userId) {
        String sql = "SELECT * FROM spotitube.playlists";

        PreparedStatement statement = connect(sql);
        ResultSet set = executeQuery(statement);

        List<PlaylistDTO> playlists = convertMultiple(set, userId);

        return new PlaylistResponseDTO(playlists, trackDAO.getTracklistTotalLength());
    }

    public void deletePlaylist(int playlistId) {
        String sql = "DELETE FROM spotitube.playlists WHERE playlistId = ?";

        PreparedStatement statement = connect(sql);
        setInt(statement, playlistId, 1);
        execute(statement);
    }

    public void updatePlaylist(int playlistId, String name) {
        String sql = "UPDATE spotitube.playlists SET spotitube.playlists.name = ? WHERE spotitube.playlists.playlistId = ?";

        PreparedStatement statement = connect(sql);
        setString(statement, name, 1);
        setInt(statement, playlistId, 2);
        execute(statement);
    }

    public void addPlaylist(int userId, String name) {
        String sql = "INSERT into spotitube.playlists (name, owner) VALUES (?, ?)";

        PreparedStatement statement = connect(sql);
        setString(statement, name, 1);
        setInt(statement, userId, 2);
        execute(statement);
    }

    @Override
    protected PlaylistDTO convert(ResultSet set, int id) {
        int playlistId = getInt(set, "playlistId");
        String name = getString(set, "name");
        int owner = getInt(set, "owner");
        List<TrackDTO> tracks = trackDAO.getAllTracksById(playlistId);

        return new PlaylistDTO(playlistId, name, owner == id ? true : false, tracks);
    }
}
