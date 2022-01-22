package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.mapper.TemplateMapper;
import com.grzegorz.rychlik.backend.model.dto.TemplateDto;
import com.grzegorz.rychlik.backend.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @PostMapping
    public void saveTemplate(@RequestBody TemplateDto templateDto){
        templateService.save(templateMapper.toDao(templateDto));
    }
}
