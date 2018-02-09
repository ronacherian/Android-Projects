package com.example.roncherian.inclass08;

/**
 * Created by roncherian on 30/10/17.
 */

public class ItunesApp {

    String appName;
    String appPrice;
    String smallImageUrl, largeImageUrl;
    private long _id;

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(String appPrice) {
        this.appPrice = appPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItunesApp itunesApp = (ItunesApp) o;

        if (appName != null ? !appName.equals(itunesApp.appName) : itunesApp.appName != null)
            return false;
        if (appPrice != null ? !appPrice.equals(itunesApp.appPrice) : itunesApp.appPrice != null)
            return false;
        if (smallImageUrl != null ? !smallImageUrl.equals(itunesApp.smallImageUrl) : itunesApp.smallImageUrl != null)
            return false;
        return largeImageUrl != null ? largeImageUrl.equals(itunesApp.largeImageUrl) : itunesApp.largeImageUrl == null;

    }
}
