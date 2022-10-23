package nl.han.oose.dea.rest.datasource;

public class UserData {
    private String userName;
    private String fullName;
    private String token;
    private String password;

    public UserData(String userName, String fullName, String token, String password) {
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
