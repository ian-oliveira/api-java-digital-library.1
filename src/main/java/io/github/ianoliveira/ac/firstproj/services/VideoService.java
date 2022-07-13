package io.github.ianoliveira.ac.firstproj.services;

import io.github.ianoliveira.ac.firstproj.dto.VideoDTO;
import io.github.ianoliveira.ac.firstproj.entities.Video;

import java.util.List;

public interface VideoService {
    List<Video> findAll();

    Video findById(Long id);

    Video create(VideoDTO dto);

    Video update(VideoDTO dto);

    void delete(Long id);
}