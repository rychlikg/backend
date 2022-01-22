package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.Template;
import com.grzegorz.rychlik.backend.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;

    public Template findByName(String name){
        return templateRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(name));
    }

    public  Template save(Template template){
        return templateRepository.save(template);
    }
}
