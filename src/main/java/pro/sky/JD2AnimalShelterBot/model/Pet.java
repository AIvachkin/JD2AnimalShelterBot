package pro.sky.JD2AnimalShelterBot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

/**
 Класс - сущность домашнего питомца
 */
@Entity(name = "pet")
public class Pet {
    /**
     * id домашнего питомца в БД
     */
    @Id
    private Long petId;
    /**
     * имя домашнего питомца
     */
    private String name;
    /**
     * возраст домашнего питомца
     */
    private Integer age;
    /**
     * хозяин домашнего питомца
     */
    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private User trustee;

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User getTrustee() {
        return trustee;
    }

    public void setTrustee(User trustee) {
        this.trustee = trustee;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}