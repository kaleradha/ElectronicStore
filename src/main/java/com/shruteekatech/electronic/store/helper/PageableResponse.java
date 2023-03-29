package com.shruteekatech.electronic.store.helper;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
@Builder
public class PageableResponse <T>{
    private List<T>content;

    private boolean lastpage;
    private Integer pageSize;
    private Integer pageNumber;
    private long totalElement;
}
