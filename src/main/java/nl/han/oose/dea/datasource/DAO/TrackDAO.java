package nl.han.oose.dea.datasource.DAO;

import nl.han.oose.dea.datasource.DAO.DAO;
import nl.han.oose.dea.dto.incoming.TrackDTO;
import nl.han.oose.dea.dto.outgoing.TrackResponseDTO;
import nl.han.oose.dea.services.exceptions.InternalServerError;
import nl.han.oose.dea.utils.Log;

import java.sql.*;
import java.util.List;

public class TrackDAO extends DAO<TrackDTO> {

    public void addTrackToPlaylist(int playlistId, TrackDTO track) {
        String sql = "INSERT into spotitube.trackinplaylist (kt_playlistId, kt_trackId) VALUES (?, ?)";

        PreparedStatement statement = connect(sql);
        setInt(statement, playlistId, 1);
        setInt(statement, track.getId(), 2);
        execute(statement);
    }


    public List<TrackDTO> getAllTracksById(int id) {
        String sql = "SELECT * FROM spotitube.trackinplaylist INNER JOIN spotitube.playlists ON spotitube.playlists.playlistId = ? INNER JOIN spotitube.tracks ON spotitube.trackinplaylist.kt_trackId = spotitube.tracks.trackId AND spotitube.trackinplaylist.kt_playlistId = ?";

        PreparedStatement statement = connect(sql);
        setInt(statement, id, 1);
        setInt(statement, id, 2);
        ResultSet set = executeQuery(statement);

        return convertMultiple(set, id);
    }

    public int getTracklistTotalLength() {
        String sql = "SELECT spotitube.tracks.duration FROM spotitube.trackinplaylist INNER JOIN spotitube.tracks ON spotitube.tracks.trackId = spotitube.trackinplaylist.kt_trackId";

        PreparedStatement statement = connect(sql);
        ResultSet set = executeQuery(statement);

        return calculateTotalDuration(set);
    }

    public int calculateTotalDuration(ResultSet set) {
        int totalDuration = 0;

        try  {
            while (set.next()) {
                int rowDuration = getInt(set, "duration");
                totalDuration += rowDuration;
            }
        } catch (Exception e) {
            Log.console("Failed to count duration.", e);
            throw new InternalServerError();
        }

        return totalDuration;
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        String sql = "DELETE FROM spotitube.trackinplaylist WHERE kt_trackId = ? AND kt_playlistId = ?";

        PreparedStatement statement = connect(sql);
        setInt(statement, trackId, 1);
        setInt(statement, playlistId, 2);
        execute(statement);
    }

    public TrackResponseDTO getAvailableTracks(int playlistId) {
        String sql = "SELECT DISTINCT * FROM spotitube.tracks WHERE spotitube.tracks.trackId NOT IN (SELECT kt_trackId FROM spotitube.trackinplaylist WHERE kt_playlistId = ?);";

        PreparedStatement statement = connect(sql);
        setInt(statement, playlistId, 1);
        ResultSet set = executeQuery(statement);

        List<TrackDTO> tracks = convertMultiple(set, playlistId);

        return new TrackResponseDTO(tracks);
    }
    @Override
    protected TrackDTO convert(ResultSet set, int id) {
        int trackId = getInt(set, "trackId");
        String title = getString(set, "title");
        String performer = getString(set, "performer");
        int duration = getInt(set, "duration");
        String album = getString(set, "album");
        int playcount = getInt(set, "playcount");
        String publicationDate = getString(set, "publicationDate");
        String description = getString(set, "description");
        boolean offlineAvailable = getBoolean(set, "offlineAvailable");

        return new TrackDTO(trackId, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable);
    }
}
