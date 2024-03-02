package org.energyapp.dto;

import java.time.LocalDate;

public class ProgramEnrollmentRequestDTO {

    private Long id; // Optional for updates
    private Long consumerId;
    private Long programId;
    private LocalDate enrollmentDate;
    private LocalDate endDate; // Can be null if the enrollment is ongoing
    private String status; // Status of the enrollment
    private Long counterId;

    public ProgramEnrollmentRequestDTO () {}

    public ProgramEnrollmentRequestDTO(Long id, Long consumerId, Long programId,
                                        LocalDate enrollmentDate, LocalDate endDate,
                                        String status, Long counterId) {
        this.id = id;
        this.consumerId = consumerId;
        this.programId = programId;
        this.enrollmentDate = enrollmentDate;
        this.endDate = endDate;
        this.status = status;
        this.counterId = counterId;
    }

    public ProgramEnrollmentRequestDTO(Long id, Long consumerId, Long programId,
                                       LocalDate enrollmentDate, LocalDate endDate,
                                       String status){
        this.id = id;
        this.consumerId = consumerId;
        this.programId = programId;
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
}
