package org.energyapp.dto;

import java.time.LocalDate;
import java.util.Objects;

public class ProgramEnrollmentResponseDTO {

    private Long id;
    private Long consumerId; // ID of the enrolled consumer
    private Long programId; // ID of the program enrolled in
    private LocalDate enrollmentDate; // Date of enrollment
    private LocalDate endDate; // Date when the enrollment ended (can be null if ongoing)
    private String status; // Status of the enrollment
    private Long counterId;

    // Default constructor
    public ProgramEnrollmentResponseDTO() {}

    // Constructor with all fields
    public ProgramEnrollmentResponseDTO(Long id, Long consumerId, Long programId, LocalDate enrollmentDate, LocalDate endDate, String status) {
        this.id = id;
        this.consumerId = consumerId;
        this.programId = programId;
        this.enrollmentDate = enrollmentDate;
        this.endDate = endDate;
        this.status = status;
    }

    public ProgramEnrollmentResponseDTO(Long id, Long consumerId, Long programId, LocalDate enrollmentDate, LocalDate endDate, String status, Long counterId) {
        this.id = id;
        this.consumerId = consumerId;
        this.programId = programId;
        this.counterId = counterId;
        this.enrollmentDate = enrollmentDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getCounterId() {
        return counterId;
    }

    public void setCounterId(Long counterId) {
        this.counterId = counterId;
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

    @Override
    public String toString() {
        return "ProgramEnrollmentResponseDTO{" +
                "id=" + id +
                ", consumerId=" + consumerId +
                ", programId=" + programId +
                ", counterId=" + counterId +
                ", enrollmentDate=" + enrollmentDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramEnrollmentResponseDTO)) return false;
        ProgramEnrollmentResponseDTO that = (ProgramEnrollmentResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(consumerId, that.consumerId) &&
                Objects.equals(programId, that.programId) &&
                Objects.equals(counterId, that.counterId) &&
                Objects.equals(enrollmentDate, that.enrollmentDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, consumerId, programId,counterId, enrollmentDate, endDate, status);
    }
}
