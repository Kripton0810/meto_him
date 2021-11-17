package com.kripton.flyaway;

public class vaccinModelClass {

    String add1,add2,d1,d2,vaccine,age,fee;

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }


    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public vaccinModelClass(String add1, String add2, String d1, String d2, String vaccine, String age, String fee) {
        this.add1 = add1;
        this.add2 = add2;
        this.d1 = d1;
        this.d2 = d2;
        this.vaccine = vaccine;
        this.age = age;
        this.fee = fee;
    }
}
