package io.github.ianoliveira.ac.firstproj.services.impl;

import io.github.ianoliveira.ac.firstproj.dto.VideoDTO;
import io.github.ianoliveira.ac.firstproj.entities.Video;
import io.github.ianoliveira.ac.firstproj.repositories.VideoRepository;
import io.github.ianoliveira.ac.firstproj.services.exceptions.DataIntegratyViolationException;
import io.github.ianoliveira.ac.firstproj.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class VideoServiceImplTest {

    public static final Integer ID = 1 ;
    public static final String TITULO = "PERDIDOS EM PARIS";
    public static final String DESCRICAO = "eu voltei ta";
    public static final String URL = "https://www.youtube.com/watch?v=D_GxH_KgARU";
    public static final Integer INDEX = 0;

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final String VIDEO_JÁ_CADASTRADO_NO_SISTEMA = "Vídeo já cadastrado no sistema";

    @InjectMocks
    private VideoServiceImpl service;
    @Mock
    private VideoRepository repository;
    @Mock
    private ModelMapper mapper;

    private Video video;
    private VideoDTO videoDTO;
    private Optional<Video> optionalVideo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startVideo();
    }

    @Test
    void whenGetByIdReturnAnObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try{
            service.findById(ID);
        }catch (Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, e.getMessage());
        }
    }

    @Test
    void whenGetByIdReturnAnVideoInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalVideo);
        Video response =  service.findById(ID);

        assertNotNull(response);
        assertEquals(Video.class, response.getClass());
        assertEquals(ID, response.getId());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(video));

        List<Video> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Video.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(TITULO, response.get(INDEX).getTitulo());
        assertEquals(DESCRICAO, response.get(INDEX).getDescricao());
        assertEquals(URL, response.get(INDEX).getUrl());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(video);

        Video response = service.create(videoDTO);

        assertNotNull(response);
        assertEquals(Video.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(TITULO, response.getTitulo());
        assertEquals(URL, response.getUrl());
    }

    @Test
    void whenCreateReturnDataIntegratyViolationException() {
        when(repository.findByUrl(anyString())).thenReturn(optionalVideo);

        try{
            optionalVideo.get().setId(2);
            service.create(videoDTO);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(VIDEO_JÁ_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(video);

        Video response = service.update(videoDTO);

        assertNotNull(response);
        assertEquals(Video.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(TITULO, response.getTitulo());
        assertEquals(URL, response.getUrl());
    }

    @Test
    void whenUpdateReturnDataIntegratyViolationException() {
        when(repository.findByUrl(anyString())).thenReturn(optionalVideo);

        try{
            optionalVideo.get().setId(2);
            service.create(videoDTO);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(VIDEO_JÁ_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalVideo);
        doNothing().when(repository).deleteById(anyInt());

        service.delete(ID);

        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException(){
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            service.delete(ID);
        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    private void startVideo(){
        video = new Video(ID, TITULO, DESCRICAO, URL);

        videoDTO = new VideoDTO(ID, TITULO, DESCRICAO, URL);

        optionalVideo = Optional.of(new Video(ID, TITULO, DESCRICAO, URL));
    }
}