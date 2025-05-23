//package com.savchenko.aptechka.config;
//
//import com.savchenko.aptechka.entity.Drug;
//import com.savchenko.aptechka.mapper.DrugDocumentMapper;
//import com.savchenko.aptechka.repository.DrugRepository;
//import com.savchenko.aptechka.service.DrugSearchService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@Profile("!test")
//@RequiredArgsConstructor
//public class ElasticDataLoader implements CommandLineRunner {
//
//    private final DrugRepository drugRepo;
//    private final DrugDocumentMapper mapper;
//    private final DrugSearchService searchService;
//
//    @Override
//    @Transactional(readOnly = true)
//    public void run(String... args) {
//        int page = 0, size = 50;
//        Page<Drug> chunk;
//        do {
//            chunk = drugRepo.findAll(PageRequest.of(page, size));
//            for (Drug drug : chunk) {
//                var doc = mapper.toDocument(drug);
//                searchService.index(doc);
//            }
//            page++;
//        } while (!chunk.isLast());
//
//        long total = drugRepo.count();
//        System.out.printf("→ Indexing in Elasticsearch complete, %d drugs indexed%n", total);
//    }
//}
