package com.iti.EShopper.models;

public class Banner {

    private String rectangularBanner;
    private String slidingBanner;
    private String verticalBanner;
    private String latestModification;


    public Banner(String rectangularBanner, String slidingBanner, String verticalBanner, String latestModification) {
        this.rectangularBanner = rectangularBanner;
        this.slidingBanner = slidingBanner;
        this.verticalBanner = verticalBanner;
        this.latestModification = latestModification;
    }

    public Banner() {

    }

    public String getRectangularBanner() {
        return rectangularBanner;
    }

    public void setRectangularBanner(String rectangularBanner) {
        this.rectangularBanner = rectangularBanner;
    }

    public String getSlidingBanner() {
        return slidingBanner;
    }

    public void setSlidingBanner(String slidingBanner) {
        this.slidingBanner = slidingBanner;
    }

    public String getVerticalBanner() {
        return verticalBanner;
    }

    public void setVerticalBanner(String verticalBanner) {
        this.verticalBanner = verticalBanner;
    }

    public String getLatestModification() {
        return latestModification;
    }

    public void setLatestModification(String latestModification) {
        this.latestModification = latestModification;
    }
}