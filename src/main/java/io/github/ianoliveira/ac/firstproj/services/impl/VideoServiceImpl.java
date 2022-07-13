package io.github.ianoliveira.ac.firstproj.services.impl;

import io.github.ianoliveira.ac.firstproj.dto.VideoDTO;
import io.github.ianoliveira.ac.firstproj.entities.Video;
import io.github.ianoliveira.ac.firstproj.repositories.VideoRepository;
import io.github.ianoliveira.ac.firstproj.services.VideoService;
import io.github.ianoliveira.ac.firstproj.services.exceptions.DataIntegratyViolationException;
import io.github.ianoliveira.ac.firstproj.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Video> findAll() {
        return repository.findAll();
    }

    @Override
    public Video findById(Integer id) {
        Optional<Video> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public Video create(VideoDTO dto) {
        findByUrl(dto);
        return repository.save(mapper.map(dto, Video.class));
    }

    @Override
    public Video update(VideoDTO dto) {
        findByUrl(dto);
        return repository.save(mapper.map(dto, Video.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByUrl(VideoDTO dto){
        Optional<Video> video = repository.findByUrl(dto.getUrl());
        if(video.isPresent() && !video.get().getId().equals(dto.getId())){
            throw new DataIntegratyViolationException("Vídeo já cadastrado no sistema");
        }
    }


}