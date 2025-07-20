package com.tacniz.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdVisitOptionObject {
    private Long id;
    private String visitOptionName;
}
