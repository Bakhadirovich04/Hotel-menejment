package org.example.entity;

import lombok.*;
import org.example.entity.enums.RoomState;
import org.example.entity.enums.RoomType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Room {
    private String id;
    private String roomNumber;
    private Double price;
    private Integer capacity;
    private Integer floor;
    private RoomState state;
    private RoomType roomType;
}