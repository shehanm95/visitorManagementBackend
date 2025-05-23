package com.tacniz.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitTypeDTO {

    private Long id;
    private String visitTypeName;
    private String visitTypeDescription;
    private String imageName;
    private MultipartFile image;
    private List<VisitOptionDTO> visitOptions;
}
