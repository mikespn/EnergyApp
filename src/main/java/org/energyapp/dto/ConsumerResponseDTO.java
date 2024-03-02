package org.energyapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ConsumerResponseDTO {

    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 50, message = "Username must be between 6 and 50 char long")
    private String username;

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
    public ConsumerResponseDTO(){}

    public ConsumerResponseDTO(String username, String email, String firstName, String lastName, Set<Long> counterIds, Set<Long> programEnrollmentIds) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.counterIds = counterIds;
        this.programEnrollmentIds = programEnrollmentIds;
    }

    public ConsumerResponseDTO(String username, String email, String firstName, String lastName) {
        this.username = username;
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

    public void setCounterIds(Set<Long> counterIds) {
        this.counterIds = (counterIds != null) ? counterIds : new HashSet<>();
    }

    public void addCounterIds(Long counterIds){
        if (counterIds == null){
            throw new IllegalArgumentException("Counter ID cannot be null");
        }else { this.counterIds.add(counterIds);}
    }

    public void removeCounterIds(Long counterIds){
        if(counterIds == null){
            throw new IllegalArgumentException("Counter Id cannot be null");
        } else {
            this.counterIds.remove(counterIds);
        }
    }

    public Set<Long> getProgramEnrollmentIds() {
        return programEnrollmentIds;
    }

    public void setProgramEnrollmentIds(Set<Long> programEnrollmentIds) {
        this.programEnrollmentIds = (programEnrollmentIds != null) ? programEnrollmentIds : new HashSet<>();
    }

    public void addProgramEnrollmentIds(Long programEnrollmentId){
        if(programEnrollmentId == null){
            throw new IllegalArgumentException("ProgramEnrollmentId cannot be null");
        }else{
            this.programEnrollmentIds.add(programEnrollmentId);
        }
    }

    public void removeProgramEnrollmentIds(Long programEnrollmentId){
        if(programEnrollmentId == null){
            throw new IllegalArgumentException("ProgramEnrollmentId cannot be null");
        }else{
            this.programEnrollmentIds.remove(programEnrollmentId);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumerResponseDTO that = (ConsumerResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(counterIds, that.counterIds);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, firstName, lastName, counterIds);
    }

    @Override
    public String toString() {
        return "ConsumerResponseDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", counterIds=" + counterIds +
                '}';
    }

}

