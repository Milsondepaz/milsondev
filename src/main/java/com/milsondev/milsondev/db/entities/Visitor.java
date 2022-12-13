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
    @Column(name="count_visit_day")
    private int countVisitDay;
    @Column(name="ip")
    private String ip;
    @Column(name="city")
    private String city;
    @Column(name="zip_code")
    private String zipCode;
    @Column(name="country")
    private String country;
    @Column(name="latitude")
    private String latitude;
    @Column(name="longitude")
    private String longitude;

    // int id artigo
    // palavra pesquisada
    @Column(name="format_data")
    public String formatData = getFormatDate();
    private String getFormatDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.getDefault() )
                .withZone( ZoneId.systemDefault() );
        return formatter.format(Instant.now()).substring(0, 10);
    }
}
