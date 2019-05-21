package com.imooc.course.thrift.user.dto;

public class TeacherDTO extends UserDTO{
    private String into;
    private int stars;

    public String getInto() {
        return into;
    }

    public void setInto(String into) {
        this.into = into;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
