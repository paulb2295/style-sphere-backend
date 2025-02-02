package com.bpx.style_sphere_backend.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String county;
    private String city;
    private String street;
    private String number;

    @OneToOne(mappedBy = "address")
    private User user;

    public Address(Long id, String country, String county, String city, String street, String number, User user) {
        this.id = id;
        this.country = country;
        this.county = county;
        this.city = city;
        this.street = street;
        this.number = number;
        this.user = user;
    }

    public Address() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Builder {
        private Long id;
        private String country;
        private String county;
        private String city;
        private String street;
        private String number;
        private User user;

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

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Address build() {
            Address address = new Address();
            address.id = this.id;
            address.country = this.country;
            address.county = this.county;
            address.city = this.city;
            address.street = this.street;
            address.number = this.number;
            address.user = this.user;

            return address;
        }
    }
}
