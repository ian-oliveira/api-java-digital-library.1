package io.github.ianoliveira.ac.firstproj.resources;

import io.github.ianoliveira.ac.firstproj.dto.VideoDTO;
import io.github.ianoliveira.ac.firstproj.entities.Video;
import io.github.ianoliveira.ac.firstproj.repositories.VideoRepository;
import io.github.ianoliveira.ac.firstproj.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/videos")
public class VideoResource {

    public static final String ID = "/{id}";

    @Autowired
    private VideoService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private VideoRepository repository;


    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAllVideos(){
        return ResponseEntity.ok()
                .body(service.findAll()
                        .stream().map(x -> mapper.map(x, VideoDTO.class))
                        .collect(Collectors.toList()));
    }

    @GetMapping(ID)
    public ResponseEntity<VideoDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), VideoDTO.class));
    }

    @PostMapping
    public ResponseEntity<VideoDTO> create(@RequestBody VideoDTO dto){
        Video newObj = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(ID)
    public ResponseEntity<VideoDTO> update(@PathVariable Long id, @RequestBody VideoDTO dto){
        dto.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(dto), VideoDTO.class));
    }

    @DeleteMapping(ID)
    public ResponseEntity<VideoDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}