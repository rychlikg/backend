package com.grzegorz.rychlik.backend.mapper;

import com.grzegorz.rychlik.backend.model.dao.Template;
import com.grzegorz.rychlik.backend.model.dto.TemplateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    Template toDao(TemplateDto templateDto);
}
