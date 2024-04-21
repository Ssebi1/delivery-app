package com.sebi.deliver.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @SequenceGenerator(name = "message_sequence", sequenceName = "message_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_sequence")
    @Schema(name = "Message id", description = "Unique identifier", example = "1")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    @Column( length = 100000)
    @Schema(name = "Message", description = "Message", example = "Hello!")
    private String message;

    @Schema(name = "Message date", description = "Message date", example = "2021-01-01 10:22:00")
    private String date;

    @NonNull
    @Schema(name = "Message name", description = "Message name", example = "John Doe")
    private String name;

    @NonNull
    @Schema(name = "Message email", description = "Message email", example = "test@yahoo.com")
    private String email;

    @NonNull
    @Schema(name = "Message phone", description = "Message phone", example = "0722222222")
    private String phone;
}
