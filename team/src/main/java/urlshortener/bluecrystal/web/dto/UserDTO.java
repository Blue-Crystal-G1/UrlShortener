package urlshortener.bluecrystal.web.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

public class UserDTO {

    @Size(min = 1)
    @JsonProperty("firstName")
    private String firstName;

    @Size(min = 1)
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("matchingPassword")
    private String matchingPassword;

    @JsonProperty("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    private Long role;

    public long getRole() {
        return role;
    }

    public void setRole(final long role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=").append(firstName).append(", lastName=").append(lastName)
                .append(", password=").append(password).append(", matchingPassword=").append(matchingPassword)
                .append(", email=").append(email).append(", role=").append(role).append("]");
        return builder.toString();
    }

}
