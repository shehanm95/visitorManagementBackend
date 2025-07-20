package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.DynamicQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceHolderDto {
    private Long id;
    List<DynamicQuestionDTO> referenceQuestions;
}
