package com.bpx.style_sphere_backend.models.dtos;

import java.util.List;

public class OrderOutputDTO {

    private Long id;
    private String userEmail;
    private List<OrderItemOutputDTO> orderItemOutputDTOList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<OrderItemOutputDTO> getOrderItemOutputList() {
        return orderItemOutputDTOList;
    }

    public void setOrderItemOutputList(List<OrderItemOutputDTO> orderItemOutputDTOList) {
        this.orderItemOutputDTOList = orderItemOutputDTOList;
    }
}
