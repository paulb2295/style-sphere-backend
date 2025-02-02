package com.bpx.style_sphere_backend.models.dtos;

public class AddressDTO {

    private Long id;
    private String country;
    private String county;
    private String city;
    private String street;
    private String number;


    public AddressDTO(Long id, String country, String county, String city, String street, String number) {
        this.id = id;
        this.country = country;
        this.county = county;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public AddressDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public static class Builder {
        private Long id;
        private String country;
        private String county;
        private String city;
        private String street;
        private String number;


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder county(String county) {
            this.county = county;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }


        public AddressDTO build() {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.id = this.id;
            addressDTO.country = this.country;
            addressDTO.county = this.county;
            addressDTO.city = this.city;
            addressDTO.street = this.street;
            addressDTO.number = this.number;
            return addressDTO;
        }
    }
}
