package com.savchenko.aptechka.repository;

import com.savchenko.aptechka.entity.DrugDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DrugDocumentRepository extends ElasticsearchRepository<DrugDocument, String> {
}
