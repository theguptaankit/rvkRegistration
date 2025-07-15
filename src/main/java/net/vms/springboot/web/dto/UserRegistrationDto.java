package net.vms.springboot.web.dto;

public class UserRegistrationDto {
    private String smartCardId;
    private String firstName;
    private String username;
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String smartCardId, String firstName, String username, String password) {
        this.smartCardId = smartCardId;
        this.firstName = firstName;
        this.username = username;
        this.password = password;
    }

    public String getSmartCardId() {
        return smartCardId;
    }

    public void setSmartCardId(String smartCardId) {
        this.smartCardId = smartCardId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
