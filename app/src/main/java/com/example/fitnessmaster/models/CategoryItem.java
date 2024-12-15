package com.example.fitnessmaster.models;

public class CategoryItem {
    private String title;
    private String image;
    private String slug;

    public CategoryItem(String title, String image, String slug) {
        this.title = title;
        this.image = image;
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}