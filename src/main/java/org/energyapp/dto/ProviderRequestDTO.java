package org.energyapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProviderRequestDTO {

    private Long id;
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 6,  max = 50)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    private Set<Long> programIds = new HashSet<>();

    public ProviderRequestDTO(){}

    public ProviderRequestDTO(Long id, String username, String password, String email){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public ProviderRequestDTO(Long id, String username, String password, String email, Set<Long> programIds){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.programIds = (programIds != null) ? programIds : new HashSet<>();
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

    public Set<Long> getProgramIds(){
        return programIds;
    }

    public void setProgramIds(Set<Long> programIds) {
        this.programIds = (programIds != null) ? programIds : new HashSet<>();
    }

    public void addProgramIds(Long programId){
        if(programId == null){
            throw new IllegalArgumentException("ProgramIds cannot be null");
        }else{
            this.programIds.add(programId);
        }
    }

    public void removeProgramIds(Long programId){
        if(programId == null){
            throw new IllegalArgumentException("ProgramId cannot be null");
        }else{
            this.programIds.remove(programId);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProviderRequestDTO)) return false;

        ProviderRequestDTO that = (ProviderRequestDTO) o;

        if (!getId().equals(that.getId())) return false;
        if (!getUsername().equals(that.getUsername())) return false;
        if (!getPassword().equals(that.getPassword())) return false;
        if (!getEmail().equals(that.getEmail())) return false;
        return getProgramIds() != null ? getProgramIds().equals(that.getProgramIds()) : that.getProgramIds() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email);
    }

    @Override
    public String toString() {
        return "ConsumerRequestDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", programIds=" + programIds +
                '}';
    }
}
