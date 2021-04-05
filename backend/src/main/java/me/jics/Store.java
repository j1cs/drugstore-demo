package me.jics;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Introspected
public class Store {
    private LocalDateTime date;
    private String id;
    private String name;
    private String communeName;
    private String location;
    private String address;
    private LocalDateTime openingHours;
    private LocalDateTime closingHours;
    private String phone;
    private Double latitude;
    private Double longitude;
    private String openingDay;
    private Integer region;
    private Integer borough;
}
