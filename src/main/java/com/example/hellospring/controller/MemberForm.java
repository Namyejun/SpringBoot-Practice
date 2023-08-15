package com.example.hellospring.controller;

// 폼에서 날라온 정보를 받아 객체를 생성해준다.
// 그리고 그 객체가 controller의 메소드에 넣어진다.
public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
