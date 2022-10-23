package nl.han.oose.dea.rest;

import jakarta.ws.rs.core.Response;
import nl.han.oose.dea.rest.resource.PlaylistResource;
import nl.han.oose.dea.rest.services.PlaylistService;
import nl.han.oose.dea.rest.services.dto.PlaylistResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistResourceTest {

    private PlaylistResource sut;
    private PlaylistService fixturePlaylistService;

    @BeforeEach
    void setup(){
        this.sut = new PlaylistResource();
        this.fixturePlaylistService = mock(PlaylistService.class);
        this.sut.setPlaylistService(fixturePlaylistService);
    }

    @Test
    void getAllPlaylistsTest(){
        //Arrange
        PlaylistResponseDTO playlistResponseDTO = new PlaylistResponseDTO();

        when(fixturePlaylistService.getPlaylists(anyString())).thenReturn(playlistResponseDTO);
        Response expected = Response.ok(playlistResponseDTO).build();

        //Act
        Response actual = sut.getAllPlaylists("");

        //Assert
        Assertions.assertEquals(expected.getStatus(), actual.getStatus());
        Assertions.assertEquals(expected.getEntity(), actual.getEntity());
    }
}
