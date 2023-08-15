package com.example.hellospring.domain;

import javax.persistence.*;

@Entity // 이제부터 JPA가 관리하는 Entity가 된다.
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // PK값이고 DB에서 자동으로 값을 생성해 줄거라는 뜻
    private Long id;
    // @Column(name = "username") // 만약 DB의 컬럼네임이 다르게 설정돼있으면 이렇게 컨트롤
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
