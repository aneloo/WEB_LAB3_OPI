package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewDto {

    private float x;
    private float y;
    private float r;
    private String time;
    private long scriptTime;
    private String status;   // для таблицы

    public ViewDto() {
    }

    public String toJSON() {
        return "{\"x\":" + x +
                ",\"y\":" + y +
                ",\"r\":" + r +
                "}";
    }
}