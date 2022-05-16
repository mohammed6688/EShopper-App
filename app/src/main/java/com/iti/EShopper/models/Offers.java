package com.iti.EShopper.models;

public class Offers {
    String id;
    String offerTitle;
    String offerBody;
    String offerImage;

    public Offers() {
    }

    public Offers(String id, String offerTitle, String offerBody, String offerImage) {
        this.id = id;
        this.offerTitle = offerTitle;
        this.offerBody = offerBody;
        this.offerImage = offerImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferBody() {
        return offerBody;
    }

    public void setOfferBody(String offerBody) {
        this.offerBody = offerBody;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }
}
