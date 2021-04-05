package me.jics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Pharmacy {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty("fecha")
    private LocalDateTime date;
    @JsonProperty("local_id")
    private String storeId;
    @JsonProperty("local_nombre")
    private String storeName;
    @JsonProperty("comuna_nombre")
    private String boroughName;
    @JsonProperty("localidad_nombre")
    private String locationName;
    @JsonProperty("local_direccion")
    private String storeAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonProperty("funcionamiento_hora_apertura")
    private LocalDateTime openingHourOperation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonProperty("funcionamiento_hora_cierre")
    private LocalDateTime closingHourOperation;
    @JsonProperty("local_telefono")
    private String storePhone;
    @JsonProperty("local_lat")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Double storeLat;
    @JsonProperty("local_lng")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Double storeLng;
    @JsonProperty("funcionamiento_dia")
    private String operationDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("fk_region")
    private Integer regionFk;
    @JsonProperty("fk_comuna")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer boroughFk;
}
