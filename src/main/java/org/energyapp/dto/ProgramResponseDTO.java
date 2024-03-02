package org.energyapp.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ProgramResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal dayRate;
    private BigDecimal nightRate;
    private boolean isNightRateApplicable;

    private Long providerId; // ID of the associated provider
    private Long categoryId; // ID of the associated category

    // Constructors
    public ProgramResponseDTO() {}

    public ProgramResponseDTO(Long id, String name, String description, BigDecimal dayRate, BigDecimal nightRate, boolean isNightRateApplicable, Long providerId, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dayRate = dayRate;
        this.nightRate = nightRate;
        this.isNightRateApplicable = isNightRateApplicable;
        this.providerId = providerId;
        this.categoryId = categoryId;
    }

    public ProgramResponseDTO(Long id, String name, String description, BigDecimal dayRate, Long providerId, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dayRate = dayRate;
        this.providerId = providerId;
        this.categoryId = categoryId;
    }

    public ProgramResponseDTO(Long id, String name, String description, BigDecimal dayRate, Long providerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dayRate = dayRate;
        this.providerId = providerId;
    }

    // Standard getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDayRate() {
        return dayRate;
    }

    public void setDayRate(BigDecimal dayRate) {
        this.dayRate = dayRate;
    }

    public BigDecimal getNightRate() {
        return nightRate;
    }

    public void setNightRate(BigDecimal nightRate) {
        this.nightRate = nightRate;
    }

    public boolean isNightRateApplicable() {
        return isNightRateApplicable;
    }

    public void setNightRateApplicable(boolean nightRateApplicable) {
        isNightRateApplicable = nightRateApplicable;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramResponseDTO)) return false;
        ProgramResponseDTO that = (ProgramResponseDTO) o;
        return isNightRateApplicable == that.isNightRateApplicable &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dayRate, that.dayRate) &&
                Objects.equals(nightRate, that.nightRate) &&
                Objects.equals(providerId, that.providerId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, dayRate, nightRate, isNightRateApplicable, providerId, categoryId);
    }

    @Override
    public String toString() {
        return "ProgramResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dayRate=" + dayRate +
                ", nightRate=" + nightRate +
                ", isNightRateApplicable=" + isNightRateApplicable +
                ", providerId=" + providerId +
                ", categoryId=" + categoryId +
                '}';
    }
}
