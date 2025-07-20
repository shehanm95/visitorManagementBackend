package com.tacniz.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitGuideDto {
    private IdVisitOptionObject visitOption;
    private List<IdServicePointObj> servicePoints;
}
