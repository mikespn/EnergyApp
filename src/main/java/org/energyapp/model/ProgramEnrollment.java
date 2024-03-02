package org.energyapp.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ProgramEnrollment")
public class ProgramEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counter_id", referencedColumnName = "id")
    private Counter counter;

    //indicates when the enrollment started
    private LocalDate enrollmentDate;

    //Indicated when the enrollment ended.
    private LocalDate endDate;

    // Indicates the status of the Program
    private String status;

    public ProgramEnrollment(){}

    public ProgramEnrollment(Consumer consumer, Program program, LocalDate enrollmentDate, String status, Counter counter){
        this.consumer = consumer;
        this.program = program;
        this.counter = counter;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public ProgramEnrollment(Consumer consumer, Program program, LocalDate enrollmentDate, String status){
        this.consumer = consumer;
        this.program = program;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
        if (counter != null && counter.getProgramEnrollment() != this) {
            counter.assignToProgramEnrollment(this);
        }
    }

    public Counter getCounter() {
        return counter;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramEnrollment)) return false;

        ProgramEnrollment that = (ProgramEnrollment) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "ProgramEnrollment{" +
                "id=" + id +
                ", consumerId='" +(consumer != null ? consumer.getId() : null) + '\'' +
                ", counterId='" +(counter != null ? counter.getId() : null) + '\'' +
                ", programId='" +(program != null ? program.getId() : null) + '\'' +
                ", enrollmentDate='" + enrollmentDate + '\'' +
                ", status" + status + '\'' +
                '}';
    }
}
