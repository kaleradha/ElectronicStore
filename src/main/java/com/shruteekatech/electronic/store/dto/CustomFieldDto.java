package com.shruteekatech.electronic.store.dto;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldDto {
    private String isactive;
    private String createdBy;
    private LocalDateTime createdOn;
    private String lastModifiedBy;
    private LocalDateTime modifiedOn;
}
