package com.savchenko.aptechka.mapper;

import com.savchenko.aptechka.entity.Drug;
import com.savchenko.aptechka.entity.DrugDetails;
import com.savchenko.aptechka.entity.DrugDocument;
import org.springframework.stereotype.Component;

@Component
public class DrugDocumentMapper {
    public DrugDocument toDocument(Drug drug, DrugDetails details) {
        var doc = new DrugDocument();
        doc.setId(drug.getId());
        doc.setTradeName(drug.getTradeName());
        doc.setInternationalName(drug.getInternationalName());
        doc.setComposition(drug.getComposition());
        return doc;
    }
}
