package me.jics;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(name = "Store", description = "Hold the attributes of the a drugstore")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Introspected
public class Store implements Serializable {
    private LocalDate date;
    private String id;
    private String name;
    private String boroughName;
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
