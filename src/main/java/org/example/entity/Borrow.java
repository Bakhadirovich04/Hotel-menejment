package org.example.entity;

import lombok.*;
import org.example.entity.enums.BorrowType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Borrow {
    private String id;
    private User user;

    private Room room;

    private Integer day;
    private java.util.Date dateFrom;
    private java.util.Date dateTo;

    private BorrowType borrowType;
}