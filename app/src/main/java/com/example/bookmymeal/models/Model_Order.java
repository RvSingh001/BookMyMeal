package com.example.bookmymeal.models;

import com.google.gson.annotations.SerializedName;

public class Model_Order {

    public String order_id;
    public String orders_item;
    public String cus_name;
    public String email;
    public String address;
    public String contact;
    public String total;
    public String payment;
    public String order_date;
    public String status;

    public Model_Order(String order_id, String orders_item, String cus_name, String email, String address,
                       String contact, String total, String payment, String order_date, String status) {
        this.order_id = order_id;
        this.orders_item = orders_item;
        this.cus_name = cus_name;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.total = total;
        this.payment = payment;
        this.order_date = order_date;
        this.status=status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrders_item() {
        return orders_item;
    }

    public String getCus_name() {
        return cus_name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getTotal() {
        return total;
    }

    public String getPayment() {
        return payment;
    }

    public String getOrder_date() {
        return order_date;
    }
    public String getStatus()
    {
        return status;
    }
}
