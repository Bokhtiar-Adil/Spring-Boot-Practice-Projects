package com.springprac.learningRESTapi.services;

import com.springprac.learningRESTapi.domain.dto.AuthorDto;
import com.springprac.learningRESTapi.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorentity);

    void delete(Long id);
}
