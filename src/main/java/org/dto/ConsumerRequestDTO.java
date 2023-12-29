package org.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ConsumerRequestDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 50, message = "Username must be between 6 and 50 char long")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50chars")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50chars")
    private String lastName;

    private Set<Long> counterIds = new HashSet<>();

    private Set<Long> programEnrollmentIds = new HashSet<>();
    public ConsumerRequestDTO(){};

    public ConsumerRequestDTO(String username, String password, String email, String firstName, String lastName, Set<Long> counterIds,
                              Set<Long> programEnrollmentIds) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.counterIds = counterIds;
        this.programEnrollmentIds = programEnrollmentIds;
    }

    public ConsumerRequestDTO(String username, String password, String email, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Long> getCounterIds() {
        return counterIds;
    }

    public void setCounterIds(Set<Long> counterIds){
        this.counterIds = (counterIds != null) ? counterIds : new HashSet<>();
    }

    public void addCounterIds(Long counterIds){
        if(counterIds == null){
            throw new IllegalArgumentException("counterIds cannot be null");
        }else{
            this.counterIds.add(counterIds);
        }
    }

    public void removeCounterIds(Long counterIds){
        if(counterIds == null){
            throw new IllegalArgumentException("counterId cannot be null");
        }else {
            this.counterIds.add(counterIds);
        }
    }

    public Set<Long> getProgramEnrollmentIds() {
        return programEnrollmentIds;
    }

    public void setProgramEnrollmentIds(Set<Long> programEnrollmentIds){
        this.programEnrollmentIds = (programEnrollmentIds != null) ? programEnrollmentIds : new HashSet<>();
    }

    public void addProgramEnrollmentId(Long programEnrollmentId) {
        if (programEnrollmentId != null) {
            this.programEnrollmentIds.add(programEnrollmentId);
        }
    }

    public void removeProgramEnrollmentId(Long programEnrollmentId){
        this.programEnrollmentIds.remove(programEnrollmentId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsumerRequestDTO)) return false;
        ConsumerRequestDTO that = (ConsumerRequestDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(counterIds, that.counterIds) &&
                Objects.equals(programEnrollmentIds, that.programEnrollmentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, firstName, lastName, counterIds, programEnrollmentIds);
    }

    @Override
    public String toString() {
        return "ConsumerRequestDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", counterIds=" + counterIds +
                ", programEnrollmentIds=" + programEnrollmentIds +
                '}';
    }
}
