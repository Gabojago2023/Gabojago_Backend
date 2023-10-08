package com.gabojago.trip.user.dto;

public class MailDto {

    private String address;
    private String title;
    private String message;

    public MailDto() {

    }

    public MailDto(String address, String title, String message) {
        super();
        this.address = address;
        this.title = title;
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MailDto [address=").append(address).append(", title=").append(title)
                .append(", message=")
                .append(message).append("]");
        return builder.toString();
    }
}