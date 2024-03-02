package org.energyapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "program")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Program name cannot be blank")
    @Size(max = 100, message = "Program name cannot be more than 100 words")
    private String name;

    @Size(max= 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull
    private BigDecimal dayRate;
    private BigDecimal nightRate;
    private boolean isNightRateApplicable;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProgramEnrollment> enrollments = new HashSet<>();


    //Constructors
    public Program(){}

    public Program(String name, String description, BigDecimal dayRate, BigDecimal nightRate, boolean isNightRateApplicable, Provider provider, Category category){
        this.name = name;
        this.description = description;
        this.dayRate = dayRate;
        this.nightRate = nightRate;
        this.isNightRateApplicable = isNightRateApplicable;
        this.provider = provider;
        this.category = category;
    }

    public Long getId() {
        return id;
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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Getter and setter for enrollments
    public Set<ProgramEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<ProgramEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    // Bidirectional relationship helper methods
    public void addEnrollment(ProgramEnrollment enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setProgram(this);
    }

    public void removeEnrollment(ProgramEnrollment enrollment) {
        this.enrollments.remove(enrollment);
        enrollment.setProgram(null);
    }


    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dayRate='" + dayRate + '\'' +
                ", isNightRateApplicable='" + (isNightRateApplicable ? nightRate : "Not Applicable") + '\'' +
                ", providerId='" + (provider != null ? provider.getId() :"null")+ '\'' +
                ", providerName='" +(provider != null ? provider.getUsername() : "null") + '\'' +
                ", category='" + (category != null ? category.getId() : "null") + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Program)) return false;
        Program program = (Program) o;
        return Objects.equals(getId(), program.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
