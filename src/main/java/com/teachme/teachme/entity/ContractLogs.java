package com.teachme.teachme.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContractLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*
     * SPACE TO ADD CONTRACT ID AND CONTRACT TYPE
     * */


    @ManyToOne
    @JoinColumn(name="RequestContract")
    @JsonIgnore
    private RequestContract requestContract;


    @ManyToOne
    @JoinColumn(name="CourseContract")
    @JsonIgnore
    private CourseContract courseContract;

    @NotBlank(message = "Log message can not be empty")
    @Column
    private String logMessage;

    @NotNull(message = "Lecture duration can not be null")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column
    private BigDecimal lectureDuration;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm")
    @Past
    private LocalDateTime createdDate;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm")
    @Past
    private LocalDateTime endDate;

    @Column
    boolean isVerified = false;

    @Column
    boolean updateRequested = false;

}
