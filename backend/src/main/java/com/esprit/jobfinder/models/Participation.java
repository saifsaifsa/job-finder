package com.esprit.jobfinder.models;

        import jakarta.persistence.Entity;
        import jakarta.persistence.GeneratedValue;
        import jakarta.persistence.GenerationType;
        import jakarta.persistence.Id;
        import jakarta.validation.constraints.NotNull;
        import lombok.Data;

        import java.time.LocalDateTime;

@Data
@Entity
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private Long userId;

    @NotNull(message = "L'ID du quiz est obligatoire")
    private Long quizId;

    @NotNull(message = "Le temps de début est obligatoire")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double score;

    // Getters and Setters
}
