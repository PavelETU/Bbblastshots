package com.wordpress.lonelytripblog.bbblastshots.data;

/**
 * Template for object representing shot.
 */

public class Shot {
    private int id;
    private String title;
    private String description;
    private String url_hidpi;
    private String url_normal;
    private String url_teaser;

    public Shot(int id, String title, String description, String url_hidpi, String url_normal, String url_teaser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url_hidpi = url_hidpi;
        this.url_normal = url_normal;
        this.url_teaser = url_teaser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_hidpi() {
        return url_hidpi;
    }

    public void setUrl_hidpi(String url_hidpi) {
        this.url_hidpi = url_hidpi;
    }

    public String getUrl_normal() {
        return url_normal;
    }

    public void setUrl_normal(String url_normal) {
        this.url_normal = url_normal;
    }

    public String getUrl_teaser() {
        return url_teaser;
    }

    public void setUrl_teaser(String url_teaser) {
        this.url_teaser = url_teaser;
    }
}
