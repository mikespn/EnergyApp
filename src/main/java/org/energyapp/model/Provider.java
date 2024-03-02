package org.energyapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "provider")
public class Provider extends User {

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Program> programs = new HashSet<>();

    public Provider(){
        super();
        this.programs = new HashSet<>();
    }

    public Provider(String username, String password, String email){
        super(username, password, email);
        this.programs = new HashSet<>();
    }

    public Provider(Long id, String username, String password, String email, Set<Program> programs){
        super(username, password, email);
        this.programs = (programs != null) ? programs : new HashSet<>();
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    public  Set<Program> getPrograms(){
        return programs;
    }

    public void setPrograms(Set<Program> programs){
        this.programs.clear();
        if(programs != null){
            for (Program program : programs){
                addProgram(program);
            }
        }
    }

    // Method to add a program in the Set
    public void addProgram(Program program){
        if(program != null){
            programs.add(program);
            program.setProvider(this);
        }
    }

    //Method to remove a program.
    public void removeProgram(Program program){
        if(programs.contains(program)){
            programs.remove(program);
            program.setProvider(null);
        }
    }

    @Override
    public String toString() {
        return  "Provider{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", programCount='" + (programs != null ? programs.size() : 0) + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
