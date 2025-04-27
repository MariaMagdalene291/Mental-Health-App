package com.example.mentalhealthproject;

public class ExerciseItem {
    public String title;
    public int imageId;
    public String description;
    public int duration; // duration in minutes


    

    // Constructor with description
    public ExerciseItem(String title, int imageId, String description,int duration) {
        this.title = title;
        this.imageId = imageId;
        this.description = description;
        this.duration = duration;

    }
}
