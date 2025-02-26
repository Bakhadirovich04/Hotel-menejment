package org.example.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class History {
    private String id;
    private Borrow borrow;
}