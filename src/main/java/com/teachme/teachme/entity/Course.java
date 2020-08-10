package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title can not be empty")
    @Size(min = 6, message = "Title must be greater 6")
    @Column
    private String title;

    @NotBlank(message = "Description can not be empty")
    @Column
    private String description;

    @NotNull(message = "Offered Price can not be null")
    @Column
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal offeredPrice;

    @NotNull(message = "Duration can not be null")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column
    private BigDecimal courseDuration;

    @Column
    private Float averageRating;

    @Column
    private Boolean isPublic = true;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedDate;

    //Relationships with others entities
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "COURSE_SKILL",
            joinColumns = {@JoinColumn(name = "COURSE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "SKILL_ID", referencedColumnName = "ID")})
    private Set<Skill> skills = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser user;


}
