package com.wordpress.lonelytripblog.bbblastshots.data;

import io.realm.RealmObject;

/**
 * Template for object representing shot.
 */

public class Shot extends RealmObject {
    private int id;
    private String title;
    private String description;
    private MyImages images;
    private boolean animated;

    public Shot() {}

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shot)) return false;

        Shot shot = (Shot) o;

        if (getId() != shot.getId()) return false;
        if (isAnimated() != shot.isAnimated()) return false;
        if (getTitle() != null ? !getTitle().equals(shot.getTitle()) : shot.getTitle() != null)
            return false;
        return getDescription() != null ? getDescription().equals(shot.getDescription()) : shot.getDescription() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (isAnimated() ? 1 : 0);
        return result;
    }

    public String getUrl() {
        return images.getUrl();
    }


    public Shot(int id, String title, String description, String url_hidpi, String url_normal, String url_teaser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = new MyImages(url_hidpi, url_normal, url_teaser);
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


}
