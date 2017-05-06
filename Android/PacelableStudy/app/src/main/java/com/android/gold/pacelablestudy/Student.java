package com.android.gold.pacelablestudy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gold on 2017. 5. 6..
 */

public class Student implements Parcelable{

    String name;
    int grade;
    int age;
    int phoneNum;

    public Student(String name, int grade, int age, int phoneNum) {
        this.name = name;
        this.grade = grade;
        this.age = age;
        this.phoneNum = phoneNum;
    }


    protected Student(Parcel in) {
        name = in.readString();
        grade = in.readInt();
        age = in.readInt();
        phoneNum = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(grade);
        dest.writeInt(age);
        dest.writeInt(phoneNum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }
}
