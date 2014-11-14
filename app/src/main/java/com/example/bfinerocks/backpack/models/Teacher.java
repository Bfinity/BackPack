package com.example.bfinerocks.backpack.models;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 11/14/14.
 */
public class Teacher implements android.os.Parcelable {
    private String userName;
    private String userClassification;
    private ArrayList<Student> myStudents;
    private ArrayList<Guardian> studentGuardians;
    private ArrayList<LessonPlans> myLessonPlans;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userClassification);
        dest.writeSerializable(this.myStudents);
        dest.writeSerializable(this.studentGuardians);
        dest.writeSerializable(this.myLessonPlans);
    }

    public Teacher() {
    }

    private Teacher(Parcel in) {
        this.userName = in.readString();
        this.userClassification = in.readString();
        this.myStudents = (ArrayList<Student>) in.readSerializable();
        this.studentGuardians = (ArrayList<Guardian>) in.readSerializable();
        this.myLessonPlans = (ArrayList<LessonPlans>) in.readSerializable();
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        public Teacher createFromParcel(Parcel source) {
            return new Teacher(source);
        }

        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };
}
