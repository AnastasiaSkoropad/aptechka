package com.savchenko.aptechka.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "drugs")
@Data
public class DrugDocument {
    @Id
    private String id;

    private String tradeName;
    private String internationalName;
    private String composition;
}
