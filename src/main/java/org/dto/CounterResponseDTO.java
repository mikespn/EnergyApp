package org.dto;

import java.util.Objects;

public class CounterResponseDTO {

    private Long id;
    private String serialNumber;
    private Long consumerId;
    private Long programEnrollmentId;

    // Constructors
    public CounterResponseDTO() {}

    public CounterResponseDTO(Long id, String serialNumber, Long consumerId, Long programEnrollmentId) {
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

    public Long getProgramEnrollmentId(){
        return programEnrollmentId;
    }

    public void setProgramEnrollmentId(Long programEnrollmentId){
        this.programEnrollmentId = programEnrollmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CounterResponseDTO)) return false;
        CounterResponseDTO that = (CounterResponseDTO) o;
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
        return "CounterResponseDTO{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", consumerId=" + consumerId +
                ", programEnrollmentId=" + programEnrollmentId +
                '}';
    }
}
