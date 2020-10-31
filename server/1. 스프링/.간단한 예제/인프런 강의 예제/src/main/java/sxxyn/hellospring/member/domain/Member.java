package sxxyn.hellospring.member.domain;

public class Member {

    private Long id;
    private String name;

    //단순한 예제이기 때문에 getter, setter를 다 만듦

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
