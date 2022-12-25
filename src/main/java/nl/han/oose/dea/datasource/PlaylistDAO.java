package nl.han.oose.dea.datasource;

import jakarta.inject.Inject;
import nl.han.oose.dea.dto.incoming.PlaylistDTO;
import nl.han.oose.dea.dto.outgoing.PlaylistResponseDTO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistDAO {
    private Logger logger = Logger.getLogger(getClass().getName());

    private DatabaseProperties databaseProperties;

    public PlaylistDAO() {

    }

    @Inject
    public PlaylistDAO(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public PlaylistResponseDTO getAllPlaylists(int userId) {
        List<PlaylistDTO> playlists = new ArrayList<>();
        int playlistId = 0;
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM spotitube.playlists");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                playlistId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int owner = resultSet.getInt(3);
                List<TrackDTO> tracks = getAllTracksById(playlistId);
                PlaylistDTO playlist = new PlaylistDTO(playlistId, name, owner == userId ? true : false, tracks);
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return new PlaylistResponseDTO(playlists, getTracklistTotalLength());
    }

    public boolean deletePlaylist(int playlistId) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("DELETE FROM spotitube.playlists WHERE id = ?");
            statement.setInt(1, playlistId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return false;
    }

    public boolean updatePlaylist(int playlistId, String name) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("UPDATE spotitube.playlists SET spotitube.playlists.name = ? WHERE spotitube.playlists.id = ?");
            statement.setString(1, name);
            statement.setInt(2, playlistId);
            boolean resultSet = statement.execute();
            return resultSet;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return false;
    }

    public boolean addTrackToPlaylist(int playlistId, TrackDTO track) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());

            PreparedStatement statement = connection.prepareStatement("INSERT into spotitube.trackinplaylist (playlistId, trackId) VALUES (?, ?)");
            statement.setInt(1, playlistId);
            statement.setInt(2, track.getId());

            boolean resultSet = statement.execute();

            return resultSet;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return false;
    }

    //TODO alle error messages veranderen

    public List<TrackDTO> getAllTracksById(int id) {
        List<TrackDTO> tracks = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM spotitube.trackinplaylist INNER JOIN spotitube.playlists ON spotitube.playlists.id = ? INNER JOIN spotitube.tracks ON spotitube.trackinplaylist.trackId = spotitube.tracks.id AND spotitube.trackinplaylist.playlistId = ?");
            statement.setInt(1, id);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int trackId = resultSet.getInt(6);
                String title = resultSet.getString(7);
                String performer = resultSet.getString(8);
                int duration = resultSet.getInt(9);
                String album = resultSet.getString(10);
                int playcount = resultSet.getInt(11);
                String publicationDate = resultSet.getString(12);
                String description = resultSet.getString(13);
                boolean offlineAvailable = resultSet.getBoolean(14);
                TrackDTO track = new TrackDTO(trackId, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable);
                tracks.add(track);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return tracks;
    }

    public int getTracklistTotalLength() {
        int length = 0;
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT spotitube.tracks.duration FROM spotitube.trackinplaylist INNER JOIN spotitube.tracks ON spotitube.tracks.id = spotitube.trackinplaylist.trackId");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int resultNumber = resultSet.getInt(1);
                length += resultNumber;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return length;
    }

    public boolean deleteTrackFromPlaylist(int playlistId, int trackId) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("DELETE FROM spotitube.trackinplaylist WHERE trackId = ? AND playlistId = ?");
            statement.setInt(1, trackId);
            statement.setInt(2, playlistId);
            boolean resultSet = statement.execute();
            return resultSet;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return false;
    }

    public TrackResponseDTO getAvailableTracks(int playlistId) {
        List<TrackDTO> tracks = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT * FROM spotitube.tracks WHERE spotitube.tracks.id NOT IN (SELECT trackId FROM spotitube.trackinplaylist WHERE playlistId = ?);");
            statement.setInt(1, playlistId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int trackId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String performer = resultSet.getString(3);
                int duration = resultSet.getInt(4);
                String album = resultSet.getString(5);
                int playcount = resultSet.getInt(6);
                String publicationDate = resultSet.getString(7);
                String description = resultSet.getString(8);
                boolean offlineAvailable = resultSet.getBoolean(9);
                TrackDTO track = new TrackDTO(trackId, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable);
                tracks.add(track);
            }
            return new TrackResponseDTO(tracks);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return null;
    }

    public boolean addPlaylist(int userId, String name) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("INSERT into spotitube.playlists (name, owner) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setInt(2, userId);
            boolean resultSet = statement.execute();
            return resultSet;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Can't insert playlist " + name, e);
        }
        return false;
    }

}
