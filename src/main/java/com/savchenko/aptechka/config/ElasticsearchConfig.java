//package com.savchenko.aptechka.config;
//
//import com.savchenko.aptechka.entity.DrugDocument;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ElasticsearchConfig {
//    private final ElasticsearchOperations operations;
//
//    @PostConstruct
//    public void createIndexAndMapping() {
//        var ops = operations.indexOps(DrugDocument.class);
//        if (ops.exists()) {
//            ops.delete();
//        }
//        ops.create();
//        ops.putMapping(ops.createMapping());
//    }
//}
