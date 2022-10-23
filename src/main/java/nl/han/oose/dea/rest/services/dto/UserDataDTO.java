package nl.han.oose.dea.rest.services.dto;

public class UserDataDTO {
    private String userName;
    private String fullName;
    private String token;
    private String password;

    public UserDataDTO(String userName, String fullName, String token, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.token = token;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
