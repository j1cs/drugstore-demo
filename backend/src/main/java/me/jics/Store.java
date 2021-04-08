package me.jics;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Introspected
public class Store {
    private LocalDate date;
    private String id;
    private String name;
    private String communeName;
    private String location;
    private String address;
    private LocalTime openingHours;
    private LocalTime closingHours;
    private String phone;
    private Double latitude;
    private Double longitude;
    private String openingDay;
    private Integer region;
    private Integer borough;
}
