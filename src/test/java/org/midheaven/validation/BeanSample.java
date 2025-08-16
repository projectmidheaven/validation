package org.midheaven.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BeanSample {

    private int age;
    private LocalDate birthday;
    private String name;
    private List<BeanSample> parents = new ArrayList<>();

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BeanSample> getParents() {
        return parents;
    }

    public void setParents(List<BeanSample> parents) {
        this.parents = parents;
    }
}
