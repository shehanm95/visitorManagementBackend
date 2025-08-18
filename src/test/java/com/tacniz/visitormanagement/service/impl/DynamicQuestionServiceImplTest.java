package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.DynamicQuestionDTO;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import com.tacniz.visitormanagement.model.DynamicQuestion;
import com.tacniz.visitormanagement.repo.DynamicQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DynamicQuestionServiceImplTest {

    @Mock
    private DynamicQuestionRepository repository;

    @Mock
    private DynamicQuestionMapper mapper;

    @InjectMocks
    private DynamicQuestionServiceImpl service;

    private AutoCloseable closeable;

    private DynamicQuestion question;
    private DynamicQuestionDTO questionDto;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        question = DynamicQuestion.builder()
                .id(1L)
                .questionText("Sample Question")
                .build();

        questionDto = DynamicQuestionDTO.builder()
                .id(1L)
                .questionText("Sample Question")
                .build();
    }

    @Test
    void testGetQuestionsByVisitOptionId() {
        when(repository.findByVisitOptionId(1L)).thenReturn(List.of(question));
        when(mapper.toDto(question)).thenReturn(questionDto);

        List<DynamicQuestionDTO> result = service.getQuestionsByVisitOptionId(1L);

        assertEquals(1, result.size());
        assertEquals("Sample Question", result.getFirst().getQuestionText());
        verify(repository).findByVisitOptionId(1L);
    }

    @Test
    void testSaveQuestion() {
        when(mapper.toEntity(questionDto)).thenReturn(question);
        when(repository.save(question)).thenReturn(question);
        when(mapper.toDto(question)).thenReturn(questionDto);

        DynamicQuestionDTO result = service.saveQuestion(questionDto);

        assertEquals("Sample Question", result.getQuestionText());
        verify(repository).save(question);
    }

    @Test
    void testGetQuestionById() {
        when(repository.findById(1L)).thenReturn(Optional.of(question));
        when(mapper.toDto(question)).thenReturn(questionDto);

        DynamicQuestionDTO result = service.getQuestionById(1L);

        assertEquals("Sample Question", result.getQuestionText());
        verify(repository).findById(1L);
    }

    @Test
    void testGetQuestionById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.getQuestionById(1L)
        );

        assertTrue(exception.getMessage().contains("Question not found with id"));
    }

    @Test
    void testUpdateQuestion() {
        when(repository.findById(1L)).thenReturn(Optional.of(question));
        when(mapper.toEntity(questionDto)).thenReturn(question);
        when(repository.save(question)).thenReturn(question);
        when(mapper.toDto(question)).thenReturn(questionDto);

        DynamicQuestionDTO result = service.updateQuestion(questionDto);

        assertEquals("Sample Question", result.getQuestionText());
        verify(repository).save(question);
    }

    @Test
    void testDeleteQuestion() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteQuestion(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testDeleteQuestion_NotExists() {
        when(repository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.deleteQuestion(1L)
        );

        assertTrue(exception.getMessage().contains("id not exist"));
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(question));
        when(mapper.toDto(question)).thenReturn(questionDto);

        List<DynamicQuestionDTO> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Sample Question", result.getFirst().getQuestionText());
    }
}
