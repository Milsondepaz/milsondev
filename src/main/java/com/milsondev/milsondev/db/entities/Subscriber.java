package com.milsondev.milsondev.db.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_subscriber")
@ToString
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "E-mail is not valid")
    @NotBlank(message = "User Name is mandatory")
    @Column(unique=true)
    private String email;
    private Instant SubscribeDon = Instant.now();

    public String fortmadetData = getFormatedDate();

    private boolean active = true;
    private String getFormatedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.getDefault() )
                .withZone( ZoneId.systemDefault() );
        return formatter.format(SubscribeDon);
    }
}
