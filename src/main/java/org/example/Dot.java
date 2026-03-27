package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Locale;

@Getter
@Setter
@Entity
@Table(name = "dots")
public class Dot implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private float x = 0.0f;

    @Column
    private float y = 0.0f;

    @Column
    private float r = 2.0f;

    @Column
    private boolean status;

    @Column
    private String time;

    @Column
    private long scriptTime;

    public String getStatus() {
        return status ? "Да!" : "Нет!";
    }

}
