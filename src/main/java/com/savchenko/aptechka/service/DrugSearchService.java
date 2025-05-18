package com.savchenko.aptechka.service;

import com.savchenko.aptechka.entity.DrugDocument;
import com.savchenko.aptechka.repository.DrugDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrugSearchService {
    private final DrugDocumentRepository repository;

    public void index(DrugDocument document) {
        repository.save(document);
    }
}
