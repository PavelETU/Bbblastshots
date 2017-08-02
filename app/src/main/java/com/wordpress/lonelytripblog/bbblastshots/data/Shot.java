package com.wordpress.lonelytripblog.bbblastshots.data;

import io.realm.RealmObject;

/**
 * Template for object representing shot.
 */

public class Shot {
    private int id;
    private String title;
    private String description;
    private MyImages images;
    private boolean animated;

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


    private class MyImages {
        private String hidpi;
        private String normal;
        private String teaser;

        public MyImages(String hidpi, String normal, String teaser) {
            this.hidpi = hidpi;
            this.normal = normal;
            this.teaser = teaser;
        }

    }

    public String getUrl() {
        // If high quality picture exist - take it,
        // if not - take with normal quality if it exist, if not - take a teaser.
        StringBuilder url = new StringBuilder(this.images.hidpi == null ?
                this.images.normal == null ? this.images.teaser : this.images.normal
                : this.images.hidpi);
        // Replace https with http (otherwise devices with api less than 21 will get 502 error).
        if (url.charAt(4) == 's') {
            url.deleteCharAt(4);
        }
        return url.toString();
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
