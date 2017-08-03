package com.wordpress.lonelytripblog.bbblastshots.data;


import io.realm.RealmObject;

/**
 * Class for serving images. Created for the sake of Realm.
 */

public class MyImages extends RealmObject {
    private String hidpi;
    private String normal;
    private String teaser;

    public MyImages() {}

    public MyImages(String hidpi, String normal, String teaser) {
        this.hidpi = hidpi;
        this.normal = normal;
        this.teaser = teaser;
    }

    public String getUrl() {
        // If high quality picture exist - take it,
        // if not - take with normal quality if it exist, if not - take a teaser.
        StringBuilder url = new StringBuilder(this.hidpi == null ?
                this.normal == null ? this.teaser : this.normal
                : this.hidpi);
        // Replace https with http (otherwise devices with api less than 21 will get 502 error).
        if (url.charAt(4) == 's') {
            url.deleteCharAt(4);
        }
        return url.toString();
    }

    public String getHidpi() {
        return hidpi;
    }

    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }
}