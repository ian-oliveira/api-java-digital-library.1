package io.github.ianoliveira.ac.firstproj.repositories;

import io.github.ianoliveira.ac.firstproj.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    Optional<Video> findByUrl(String url);
}