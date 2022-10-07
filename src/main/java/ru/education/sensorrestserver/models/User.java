package ru.education.sensorrestserver.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.education.sensorrestserver.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author Kirill Popov
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String username;
    @Positive
    private Integer yearOfBirth;
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

}
