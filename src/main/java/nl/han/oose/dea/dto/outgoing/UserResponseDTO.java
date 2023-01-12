package nl.han.oose.dea.dto.outgoing;

public class UserResponseDTO {
    private String username;
    private String token;
    private String password;

    public UserResponseDTO() {

    }

    public UserResponseDTO(String username, String token, String password) {
        this.username = username;
        this.token = token;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
