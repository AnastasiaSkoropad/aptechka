package com.savchenko.aptechka.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Suggestion;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption;
import com.savchenko.aptechka.dto.DrugDocumentDto;
import com.savchenko.aptechka.dto.SuggestionDto;
import com.savchenko.aptechka.entity.DrugDocument;
import com.savchenko.aptechka.mapper.DrugDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DrugSearchService{
    private final ElasticsearchClient client;
    private final DrugDocumentMapper mapper;

    public void index(DrugDocument document) {
        try {
            client.index(i -> i
                    .index("drugs")
                    .id(document.getId())
                    .document(document)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<DrugDocumentDto> search(
            String query,
            int page,
            int size,
            String sortField,
            String sortOrder
    ) {

        SearchResponse<DrugDocument> resp;
        try {
            resp = client.search(r -> r
                            .index("drugs")
                            .from(page * size)        // int → Integer, помилки більше не буде
                            .size(size)
                            .query(q -> q
                                    .multiMatch(mm -> mm
                                            .query(query)
                                            .fields("tradeName^3", "internationalName", "composition")
                                            .fuzziness("AUTO")
                                            .type(TextQueryType.BestFields)
                                    )
                            ),
                    DrugDocument.class
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to search documents", e);
        }

        List<DrugDocumentDto> dtos = resp.hits().hits().stream()
                .map(hit -> mapper.toDto(hit.source()))
                .toList();

        long total = resp.hits().total().value();
        var pageReq = PageRequest.of(page, size, Sort.by(
                Sort.Direction.fromString(sortOrder), sortField
        ));
        return new PageImpl<>(dtos, pageReq, total);
    }


    public SuggestionDto suggest(String prefix) {
        // 1. Нормалізуємо префікс у верхній регістр
        String normalized = prefix == null ? "" : prefix.toUpperCase(Locale.ROOT);

        SearchResponse<DrugDocument> resp;
        try {
            resp = client.search(r -> r
                            .index("drugs")
                            .source(src -> src.fetch(false))
                            .suggest(s -> s
                                    .suggesters("drug-suggest", sg -> sg
                                            .prefix(normalized)
                                            .completion(c -> c
                                                    .field("suggest")
                                                    .skipDuplicates(true)
                                                    .size(10)
                                                    // fuzzy дозволяє ловити невеликі помилки
                                                    .fuzzy(fz -> fz.fuzziness("AUTO"))
                                            )
                                    )
                            ),
                    DrugDocument.class
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to fetch suggestions from Elasticsearch", e);
        }

        Map<String, ? extends List<? extends Suggestion<?>>> suggestMap = resp.suggest();
        List<String> suggestions = extract(suggestMap, "drug-suggest");
        return new SuggestionDto(suggestions);
    }

    private static List<String> extract(
            Map<String, ? extends List<? extends Suggestion<?>>> suggestions,
            String key
    ) {
        List<? extends Suggestion<?>> list = suggestions.get(key);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list.stream()
                .filter(Suggestion::isCompletion)
                .flatMap(s -> s.completion().options().stream())
                .map(CompletionSuggestOption::text)
                .map(Object::toString)
                .distinct()
                .toList();
    }
}