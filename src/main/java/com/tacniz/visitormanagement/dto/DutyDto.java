package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.DutyState;
import com.tacniz.visitormanagement.model.ServicePoint;
import com.tacniz.visitormanagement.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutyDto {
    private Long id;
    private ServicePointDto servicePoint;
    private UserDto officer;
    private DutyState dutyState;
    private LocalDateTime AcceptedTime;
}
