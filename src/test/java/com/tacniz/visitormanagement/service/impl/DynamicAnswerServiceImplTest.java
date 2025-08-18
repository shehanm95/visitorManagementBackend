package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.DynamicAnswerDto;
import com.tacniz.visitormanagement.mapper.DynamicAnswerMapper;
import com.tacniz.visitormanagement.model.DynamicAnswer;
import com.tacniz.visitormanagement.repo.DynamicAnswerRepository;
import com.tacniz.visitormanagement.repo.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamicAnswerServiceImplTest {

    @Mock
    private DynamicAnswerRepository answerRepository;

    @Mock
    private DynamicAnswerMapper dynamicAnswerMapper;

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private DynamicAnswerServiceImpl dynamicAnswerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAll_shouldReturnSavedDtos() {
        DynamicAnswerDto dto = new DynamicAnswerDto();
        DynamicAnswer entity = new DynamicAnswer();

        List<DynamicAnswerDto> dtoList = Collections.singletonList(dto);
        List<DynamicAnswer> entityList = Collections.singletonList(entity);

        when(dynamicAnswerMapper.toEntityList(dtoList)).thenReturn(entityList);
        when(answerRepository.saveAll(entityList)).thenReturn(entityList);
        when(dynamicAnswerMapper.toDtoList(entityList)).thenReturn(dtoList);

        List<DynamicAnswerDto> result = dynamicAnswerService.saveAll(dtoList);

        assertEquals(dtoList, result);
        verify(dynamicAnswerMapper).toEntityList(dtoList);
        verify(answerRepository).saveAll(entityList);
        verify(dynamicAnswerMapper).toDtoList(entityList);
    }
}
