package com.example.senfit.ui.inperson;

import android.graphics.Bitmap;

/*
 * author: Portia siddiqua(107741175)
 *
 *  * */
public class InpersonClassData {
    private int gymClassId;
    private String clasName;
    private String date;
    private String time;
    private String instructorName;
    private Bitmap image;
    private Boolean enrolled;

    public Boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        this.enrolled = enrolled;
    }

    public String getClasName() {
        return clasName;
    }

    public void setClasName(String clasName) {
        this.clasName = clasName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getGymClassId() {
        return gymClassId;
    }

    public void setGymClassId(int gymClassId) {
        this.gymClassId = gymClassId;
    }
}
