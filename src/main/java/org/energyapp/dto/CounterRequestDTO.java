package org.energyapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class CounterRequestDTO {

    private Long id; // Optional, used for identifying the counter in update operations

    @NotBlank(message = "Serial number must not be blank")
    @Pattern(regexp = "\\d{8}", message = "Serial number must be exactly 8 digits")
    private String serialNumber;

    private Long consumerId; // ID of the associated consumer
    private Long programEnrollmentId;  // id of the associated programEnrollment

    // Constructors
    public CounterRequestDTO() {}

    public CounterRequestDTO(Long id, String serialNumber, Long consumerId, Long programEnrollmentId) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.consumerId = consumerId;
        this.programEnrollmentId = programEnrollmentId;
    }

    // Standard getters and setters
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
        this.serialNumber = serialNumber;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getProgramEnrollmentId() {
        return programEnrollmentId;
    }

    public void setProgramId(Long programId) {
        this.programEnrollmentId = programEnrollmentId;
    }

    // Optionally, override toString, equals, and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CounterRequestDTO)) return false;
        CounterRequestDTO that = (CounterRequestDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(serialNumber, that.serialNumber) &&
                Objects.equals(consumerId, that.consumerId) &&
                Objects.equals(programEnrollmentId, that.programEnrollmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, consumerId, programEnrollmentId);
    }

    @Override
    public String toString() {
        return "CounterRequestDTO{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", consumerId=" + consumerId +
                ", programEnrollmentId=" + programEnrollmentId +
                '}';
    }
}
