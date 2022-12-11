package com.milsondev.milsondev.db.entities;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tb_visitor")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String zipCode;
    private String country;
    private String latitude;
    private String longitude;
    private Instant visitData = Instant.now();
    public String fortmadetData = getFormatedDate();
    private String getFormatedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.getDefault() )
                .withZone( ZoneId.systemDefault() );
        return formatter.format(visitData);
    }
}
