package com.tacniz.visitormanagement.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.*;
import com.tacniz.visitormanagement.model.Visit;
import com.tacniz.visitormanagement.repo.DynamicQuestionRepository;
import com.tacniz.visitormanagement.repo.ReferenceHolderRepo;
import com.tacniz.visitormanagement.repo.ServicePointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FullVisitMapper {
    private final ObjectMapper objectMapper;
    
    @Autowired
    private ServicePointRepository serviceRepo;
    @Autowired
    private DynamicQuestionRepository questionRepo;
    @Autowired
    private ReferenceHolderRepo refRepo;


}
