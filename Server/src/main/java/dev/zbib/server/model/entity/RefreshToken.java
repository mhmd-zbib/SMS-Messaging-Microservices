package dev.zbib.server.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id")
    public User user;

    @Id
    @GeneratedValue
    private Long id;

    private String refreshToken;

}
