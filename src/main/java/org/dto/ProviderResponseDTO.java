package org.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProviderResponseDTO {

    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size (min = 6, max = 50)
    private String username;

    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;

    private Set<Long> programIds = new HashSet<>();

    public ProviderResponseDTO(){}

    public ProviderResponseDTO(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public ProviderResponseDTO(Long id, String username, String email, Set<Long> programIds){
        this.id = id;
        this.username = username;
        this.email = email;
        this.programIds = (programIds != null) ? programIds : new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Long> getProgramIds(){
        return programIds;
    }

    public void setProgramIds(Set<Long> programIds){
        this.programIds = (programIds != null) ? programIds : new HashSet<>();
    }

    public void addProgramIds(Long programIds){
        if(programIds == null){
            throw new IllegalArgumentException("Program ids cannot be null");
        } else {
            this.programIds.add(programIds);
        }
    }

    public void removeProgramIds(Set<Long> programIds){
        if(programIds == null){
            throw new IllegalArgumentException("program ids cannot be null");
        }else{
            this.programIds.remove(programIds);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProviderResponseDTO)) return false;

        ProviderResponseDTO that = (ProviderResponseDTO) o;

        if (!getId().equals(that.getId())) return false;
        if (!getUsername().equals(that.getUsername())) return false;
        if (!getEmail().equals(that.getEmail())) return false;
        return getProgramIds() != null ? getProgramIds().equals(that.getProgramIds()) : that.getProgramIds() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, programIds);
    }

    @Override
    public String toString() {
        return "ConsumerResponseDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", programIds='" + programIds + '\'' +
                '}';
    }

}
