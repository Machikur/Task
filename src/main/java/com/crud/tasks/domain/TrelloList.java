package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class TrelloList {
    private String id;
    private String name;
    private boolean isClosed;


}
