package com.sudheer.aisolver.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;

    @Column(length = 5000)
    private String problem;

    @Column(length = 20000)
    private String solution;

    private LocalDateTime createdAt;
}
