package me.jics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jics.deser.CoorsDeserializer;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Introspected
public class Pharmacy {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("fecha")
    private LocalDate date;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm' hrs.'")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonProperty("funcionamiento_hora_apertura")
    private LocalTime openingHourOperation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm' hrs.'")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonProperty("funcionamiento_hora_cierre")
    private LocalTime closingHourOperation;
    @JsonProperty("local_telefono")
    private String storePhone;
    @JsonProperty("local_lat")
    @JsonDeserialize(using = CoorsDeserializer.class)
    private Double storeLat;
    @JsonProperty("local_lng")
    @JsonDeserialize(using = CoorsDeserializer.class)
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
