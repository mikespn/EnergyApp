package org.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "consumer")
public class Consumer extends User{

    private String firstName;
    private String lastName;


    @OneToMany(mappedBy = "consumer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Counter> counters = new HashSet<>();

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL)
    private Set<ProgramEnrollment> programEnrollments = new HashSet<>();
    public Consumer(){
        super();
    }

    // Constructor with all user properties
    public Consumer(String username, String lastName, String firstName, String password, String email) {
        super(username, password, email);
        this.lastName = lastName;
        this.firstName = firstName;
        this.counters = new HashSet<>();
        this.programEnrollments = new HashSet<>();
    }

    // Constructor with user properties and counters and programm enrolled
    public Consumer(String username, String lastName, String firstName, String password, String email, Set<Counter> counters,
                    Set<ProgramEnrollment> programEnrollments) {
        super(username, password, email);
        this.lastName = lastName;
        this.firstName = firstName;
        this.counters = counters != null ? counters : new HashSet<>();
        this.programEnrollments = programEnrollments != null ? programEnrollments : new HashSet<>();
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

    public Set<Counter> getCounters() {
        return counters;
    }

    public void setCounters(Set<Counter> counters) {
        this.counters = (counters != null) ? counters : new HashSet<>();
    }

    public boolean addCounter(Counter counter) {
        if(counter == null){
            return false;
        }
        //Check if the counter is already associated
        if(counters.contains(counter)){
            return false; // counter already associated
        }
        counters.add(counter);
        counter.setConsumer(this);
        return true;
    }

    public boolean removeCounter(Counter counter) {
        if(counter != null && counters.contains(counter)){
            counters.remove(counter);
            counter.setConsumer(null);
            return true;
        }
        return false;
    }

    public Set<ProgramEnrollment> getProgramEnrollments() {
        return programEnrollments;
    }

    public void setProgramEnrollments(Set<ProgramEnrollment> programEnrollments) {
        this.programEnrollments = (programEnrollments != null) ? programEnrollments : new HashSet<>();
    }
    public boolean addProgramEnrollment(ProgramEnrollment programEnrollment) {
        if(programEnrollment == null){
            return false;
        }
        if(programEnrollments.contains(programEnrollment)){
            return false; // Consumer has already enrolled to this program
        }
        programEnrollments.add(programEnrollment);
        programEnrollment.setConsumer(this);
        return true;
    }

    public boolean removeProgramEnrollment(ProgramEnrollment programEnrollment) {
        if(programEnrollment != null && programEnrollments.contains(programEnrollment)){
            programEnrollments.remove(programEnrollment);
            programEnrollment.setConsumer(null);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return  "Consumer{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", lastname='" +getLastName() + '\'' +
                ", firstname='" +getFirstName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", counterCount='" + (counters != null ? counters.size() : 0) + '\'' +
                ", programsEnrolled='" +(programEnrollments != null ? programEnrollments.size() : 0) + '\'' +
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
