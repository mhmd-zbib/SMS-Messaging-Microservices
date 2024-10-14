package dev.zbib.server.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
