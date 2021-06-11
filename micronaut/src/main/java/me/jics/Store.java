package me.jics;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jics.deser.CapitalizeSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(name = "Store", description = "Hold the attributes of the a drugstore")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Introspected(classes = {CapitalizeSerializer.class})
public class Store implements Serializable {
    private LocalDate date;
    private String id;
    @JsonSerialize(using = CapitalizeSerializer.class)
    private String name;
    @JsonSerialize(using = CapitalizeSerializer.class)
    private String boroughName;
    @JsonSerialize(using = CapitalizeSerializer.class)
    private String location;
    @JsonSerialize(using = CapitalizeSerializer.class)
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
