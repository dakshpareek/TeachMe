package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courseResponses")
@ToString
public class CourseResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Message can not be empty")
    @Column
    private String message;

    @NotNull(message = "Offered Price can not be null")
    @Column
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal proposedPrice;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDate;

    @Column
    private Boolean status = false;

    //Relationships with others entities

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DAOUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COURSE", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Course course;
}
