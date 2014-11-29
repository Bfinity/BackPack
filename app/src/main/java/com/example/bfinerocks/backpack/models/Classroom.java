package com.example.bfinerocks.backpack.models;

import android.os.Parcel;

/**
 * Created by BFineRocks on 11/20/14.
 */
public class Classroom implements android.os.Parcelable {
    private String classSectionName;
    private String classSectionSubject;
    private int classSectionGradeLevel;

    public Classroom(String classSectionName, String classSectionSubject, int classSectionGradeLevel){
        this.classSectionName = classSectionName;
        this.classSectionSubject = classSectionSubject;
        this.classSectionGradeLevel = classSectionGradeLevel;
    }

    public String getClassSectionName() {
        return classSectionName;
    }

    public void setClassSectionName(String classSectionName) {
        this.classSectionName = classSectionName;
    }

    public String getClassSectionSubject() {
        return classSectionSubject;
    }

    public void setClassSectionSubject(String classSectionSubject) {
        this.classSectionSubject = classSectionSubject;
    }

    public int getClassSectionGradeLevel() {
        return classSectionGradeLevel;
    }

    public void setClassSectionGradeLevel(int classSectionGradeLevel) {
        this.classSectionGradeLevel = classSectionGradeLevel;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.classSectionName);
        dest.writeString(this.classSectionSubject);
        dest.writeInt(this.classSectionGradeLevel);
    }

    private Classroom(Parcel in) {
        this.classSectionName = in.readString();
        this.classSectionSubject = in.readString();
        this.classSectionGradeLevel = in.readInt();
    }

    public static final Creator<Classroom> CREATOR = new Creator<Classroom>() {
        public Classroom createFromParcel(Parcel source) {
            return new Classroom(source);
        }

        public Classroom[] newArray(int size) {
            return new Classroom[size];
        }
    };
}
