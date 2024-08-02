package com.example.threadsafetest.people;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class People {
    @Id
    @GeneratedValue
    @Column(name = "people_id")
    private Long id;

    private String name;

    private Integer count;

    public People(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    @Version
    private Long version;
}
