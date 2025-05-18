package com.savchenko.aptechka.mapper;

import com.savchenko.aptechka.entity.Drug;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import com.savchenko.aptechka.entity.DrugDocument;
import com.savchenko.aptechka.dto.DrugDocumentDto;
import org.mapstruct.MappingTarget;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DrugDocumentMapper {
    DrugDocumentDto toDto(DrugDocument document);
    DrugDocument toDocument(Drug drug);

    @AfterMapping
    default void populateSuggest(@MappingTarget DrugDocument doc, Drug src) {
        List<String> inputs = new ArrayList<>();

        // повна назва
        inputs.add(src.getTradeName());
        // міжнародна назва
        if (src.getInternationalName() != null) {
            inputs.add(src.getInternationalName());
        }
        // кожне «слово» та частини після дефісів
        Arrays.stream(src.getTradeName().split("[\\s\\-]+"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .forEach(inputs::add);

        doc.setSuggest(new Completion(inputs.toArray(String[]::new)));
    }

}