package hu.bme.aut.temalab.order_processor.controller.dto;

import lombok.Data;

@Data
public class AddressDto {
    private long id;
    private String zipCode;
    private String city;
    private String street;
    private String houseNumber;
    private String comment;
}