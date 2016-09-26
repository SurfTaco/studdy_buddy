package com.studbud.studbud.domain;

import java.util.ArrayList;

/**
 * Created by Aldu on 25.09.16.
 */
public class Subject {

    ArrayList<ModuleItem> modules;
    String name;
    private CourseItem courseItem;

    public Subject(String name, ArrayList<ModuleItem> modules) {
        this.name = name;
        this.modules = modules;
    }

    public Subject(ArrayList<ModuleItem> modules){
        this.modules = modules;
    }

    public ArrayList<ModuleItem> getModules() {
        return modules;
    }

    public CourseItem getCourseItem(){
        for (ModuleItem moduleItem : modules){
            this.modules = modules;
        }
        return courseItem;
    }

    public double calculateSubjectGrade() {
        double sum = 0;
        for (ModuleItem module : getModules()) {
            sum += module.getMark() * module.getWeight();
        }
        return sum;
    }
}