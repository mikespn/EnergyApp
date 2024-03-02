package org.energyapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "counter")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 8digit serialNumber
    @NotBlank(message = "Serial number must not be blank")
    @Pattern(regexp = "\\d{8}", message = "Serial number must be exactly 8 digits")
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @OneToOne(mappedBy = "counter", cascade = CascadeType.ALL)
    private ProgramEnrollment programEnrollment;

    // Constructor
    public Counter(){}

    public Counter(String serialNumber, Consumer consumer){
        this.serialNumber = serialNumber;
        this.consumer = consumer;
    }

    public Counter(String serialNumber, Consumer consumer, ProgramEnrollment programEnrollment){
        this.serialNumber = serialNumber;
        this.consumer = consumer;
        this.programEnrollment = programEnrollment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        if (serialNumber != null && serialNumber.matches("\\d{8}")) {
            this.serialNumber = serialNumber;
        } else {
            throw new IllegalArgumentException("Serial number must be an 8-digit number.");
        }
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public void assignToConsumer(Consumer consumer) {
        if (consumer != null) {
            this.consumer = consumer;
            consumer.getCounters().add(this);
        }
    }

    public void removeFromConsumer() {
        if (this.consumer != null) {
            this.consumer.getCounters().remove(this);
            this.consumer = null;
        }
    }

    public ProgramEnrollment getProgramEnrollment() {
        return programEnrollment;
    }

    public void setProgramEnrollment(ProgramEnrollment programEnrollment) {
        this.programEnrollment = programEnrollment;
    }

    public void assignToProgramEnrollment(ProgramEnrollment enrollment) {
        this.programEnrollment = enrollment;
        if (enrollment != null) {
            enrollment.setCounter(this);
        }
    }

    public void removeFromProgramEnrollment() {
        if (this.programEnrollment != null) {
            this.programEnrollment.setCounter(null);
            this.programEnrollment = null;
        }
    }



    @Override
    public String toString() {
        return "Counter{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", consumerId='" + (consumer != null ? consumer.getId() : "null") + '\'' +
                ", programEnrollment='" + (programEnrollment != null ? programEnrollment.getId() : "null") + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Counter)) return false;

        Counter counter = (Counter) o;

        if (!getId().equals(counter.getId())) return false;
        return getSerialNumber().equals(counter.getSerialNumber());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getSerialNumber().hashCode();
        return result;
    }
}
