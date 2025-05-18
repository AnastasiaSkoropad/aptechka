package com.savchenko.aptechka.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.suggest.Completion;

@Document(indexName = "drugs")
@Data
public class DrugDocument {
    @Id
    private String id;

    private String tradeName;
    private String internationalName;
    private String composition;

    @CompletionField(maxInputLength = 100)
    private Completion suggest;
}
