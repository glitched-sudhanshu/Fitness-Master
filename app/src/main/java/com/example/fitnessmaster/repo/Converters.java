package com.example.fitnessmaster.repo;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class
Converters {

    @TypeConverter
    public String fromList(List<String> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public List<String> toList(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}

