package io.github.ianoliveira.ac.firstproj.resources;

import io.github.ianoliveira.ac.firstproj.dto.VideoDTO;
import io.github.ianoliveira.ac.firstproj.entities.Video;
import io.github.ianoliveira.ac.firstproj.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAllVideos(){
        return ResponseEntity.ok()
                .body(service.findAll()
                        .stream().map(x -> mapper.map(x, VideoDTO.class))
                        .collect(Collectors.toList()));
    }

    @GetMapping(ID)
    public ResponseEntity<VideoDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), VideoDTO.class));
    }

    @PostMapping
    public ResponseEntity<VideoDTO> create( @Valid @RequestBody VideoDTO dto){
        Video newObj = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(ID)
    public ResponseEntity<VideoDTO> update(@PathVariable Integer id, @Valid @RequestBody VideoDTO dto){
        dto.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(dto), VideoDTO.class));
    }

    @DeleteMapping(ID)
    public ResponseEntity<VideoDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}