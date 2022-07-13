package io.github.ianoliveira.ac.firstproj.resources;

import io.github.ianoliveira.ac.firstproj.dto.VideoDTO;
import io.github.ianoliveira.ac.firstproj.entities.Video;
import io.github.ianoliveira.ac.firstproj.services.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class VideoResourceTest {

    public static final Integer ID = 1 ;
    public static final String TITULO = "PERDIDOS EM PARIS";
    public static final String DESCRICAO = "eu voltei ta";
    public static final String URL = "https://www.youtube.com/watch?v=D_GxH_KgARU";
    public static final Integer INDEX = 0;

    private Video video;
    private VideoDTO videoDTO;


    @InjectMocks
    private VideoResource resource;

    @Mock
    private VideoService service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startVideo();
    }

    @Test
     void whenGetByIdReturnSuccess(){
        when(service.findById(anyInt())).thenReturn(video);
        when(mapper.map(any(), any())).thenReturn(videoDTO);

        ResponseEntity<VideoDTO> response = resource.getById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(VideoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(TITULO, response.getBody().getTitulo());
        assertEquals(DESCRICAO, response.getBody().getDescricao());
        assertEquals(URL, response.getBody().getUrl());

    }

    @Test
    void whenGetAllVideosReturnAListOfVideoDTO() {
        when(service.findAll()).thenReturn(List.of(video));
        when(mapper.map(any(), any())).thenReturn(videoDTO);

        ResponseEntity<List<VideoDTO>> response = resource.getAllVideos();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(VideoDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(TITULO, response.getBody().get(INDEX).getTitulo());
        assertEquals(DESCRICAO, response.getBody().get(INDEX).getDescricao());
        assertEquals(URL, response.getBody().get(INDEX).getUrl());
    }

    @Test
    void whenCreateReturnSuccess() {
        when(service.create(any())).thenReturn(video);

        ResponseEntity<VideoDTO> response = resource.create(videoDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateReturnSuccess() {
        when(service.update(videoDTO)).thenReturn(video);
        when(mapper.map(any(), any())).thenReturn(videoDTO);

        ResponseEntity<VideoDTO> response = resource.update(ID, videoDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(VideoDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(TITULO, response.getBody().getTitulo());
        assertEquals(DESCRICAO, response.getBody().getDescricao());
        assertEquals(URL, response.getBody().getUrl());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyInt());

        ResponseEntity<VideoDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyInt());
    }


    private void startVideo(){
         video = new Video(ID, TITULO, DESCRICAO, URL);

         videoDTO = new VideoDTO(ID, TITULO, DESCRICAO, URL);
    }
}
