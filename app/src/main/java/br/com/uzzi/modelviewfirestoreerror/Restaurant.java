package br.com.uzzi.modelviewfirestoreerror;

import com.google.firebase.firestore.GeoPoint;

public class Restaurant {
    private String phone;
    private String address;
    private String cnpj;
    private String name;
    private GeoPoint latLong;
    private String uId;

    public Restaurant() {}

    public Restaurant(String phone, String name, String uId)
    {
        this.phone = phone;
        this.name = name;
        this.uId = uId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoPoint getLatLong() {
        return latLong;
    }

    public void setLatLong(GeoPoint latLong) {
        this.latLong = latLong;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
