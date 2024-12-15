package com.example.fitnessmaster.models;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.fitnessmaster.Constants;

import java.util.List;

@Entity(tableName = Constants.DB_NAME)
public class ExerciseItem {
    @ColumnInfo(name = "body_part")
    private String bodyPart;

    @ColumnInfo(name = "equipment")
    private String equipment;

    @ColumnInfo(name = "gif_url")
    @DrawableRes
    private int gifUrl;

    @NonNull
    @PrimaryKey
    private String id;

    private List<String> instructions;

    private String name;

    private List<String> secondaryMuscles;

    private String target;

    @NonNull
    @ColumnInfo(name = "is_favourite")
    private Boolean isFavourite;

    public ExerciseItem(String bodyPart, String equipment, String id,
                        List<String> instructions, String name,
                        List<String> secondaryMuscles, String target) {
        this.bodyPart = bodyPart;
        this.equipment = equipment;
        this.id = id;
        this.instructions = instructions;
        this.name = name;
        this.secondaryMuscles = secondaryMuscles;
        this.target = target;
        this.isFavourite = false;
    }

    // Getters and Setters
    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public int getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(int gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSecondaryMuscles() {
        return secondaryMuscles;
    }

    public void setSecondaryMuscles(List<String> secondaryMuscles) {
        this.secondaryMuscles = secondaryMuscles;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    // toString Method
    @Override
    public String toString() {
        return "ExerciseItem{" +
                "bodyPart='" + bodyPart + '\'' +
                ", equipment='" + equipment + '\'' +
                ", gifUrl='" + gifUrl + '\'' +
                ", id='" + id + '\'' +
                ", instructions=" + instructions +
                ", name='" + name + '\'' +
                ", secondaryMuscles=" + secondaryMuscles +
                ", target='" + target + '\'' +
                '}';
    }
}
