package com.savchenko.aptechka.controller;

import com.savchenko.aptechka.dto.DrugDocumentDto;
import com.savchenko.aptechka.dto.SuggestionDto;
import com.savchenko.aptechka.service.DrugSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugSearchController {
    private final DrugSearchService searchService;

    @GetMapping("/search")
    public Page<DrugDocumentDto> searchDrugs(
            @RequestParam("q") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "tradeName") String sort,
            @RequestParam(defaultValue = "asc") String order
    ) {
        return searchService.search(query, page, size, sort, order);
    }

    @GetMapping("/suggest")
    public SuggestionDto suggest(@RequestParam("q") String prefix) {
        return searchService.suggest(prefix);
    }
}