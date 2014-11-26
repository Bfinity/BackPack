package com.example.bfinerocks.backpack.models;

/**
 * Created by BFineRocks on 11/20/14.
 */
public class ClassSection {
    private String classSectionName;
    private String classSectionSubject;
    private int classSectionGradeLevel;

    public ClassSection(String classSectionName, String classSectionSubject, int classSectionGradeLevel){
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
}
