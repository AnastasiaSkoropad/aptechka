//package com.savchenko.aptechka.config;
//
//import com.opencsv.bean.*;
//import com.opencsv.enums.CSVReaderNullFieldIndicator;
//import com.opencsv.exceptions.CsvConstraintViolationException;
//import com.opencsv.exceptions.CsvDataTypeMismatchException;
//import com.savchenko.aptechka.entity.Drug;
//import com.savchenko.aptechka.entity.DrugDetails;
//import com.savchenko.aptechka.repository.DrugRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.MultiValuedMap;
//import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * Імпорт CSV-реєстру лікарських засобів у таблиці drug та drug_details.
// */
//@Slf4j
//@Component
//public class ReestrImporter implements ApplicationRunner {
//
//    private static final DateTimeFormatter UKR_DMY =
//            DateTimeFormatter.ofPattern("dd.MM.yyyy");
//
//    /** слова-маркер, що означають «дата відсутня / безстроково» */
//    private static final Set<String> INFINITE_DATE_WORDS = Set.of(
//            "необмежений", "необмежена", "не обмежений", "не обмежена",
//            "безстроково", "безстроковий", "безстрокова"
//    );
//
//    private final DrugRepository drugRepo;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    public ReestrImporter(DrugRepository drugRepo) {
//        this.drugRepo = drugRepo;
//    }
//
//    @Override
//    @Transactional
//    public void run(ApplicationArguments args) throws Exception {
//
//        var resource = new ClassPathResource("db/data/reestr.csv");
//        try (var reader = new InputStreamReader(resource.getInputStream(),
//                Charset.forName("Cp1251"))) {
//
//            List<ReestrCsv> beans = new CsvToBeanBuilder<ReestrCsv>(reader)
//                    .withType(ReestrCsv.class)
//                    .withSeparator(';')
//                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build()
//                    .parse();
//
//            int counter = 0;
//            for (ReestrCsv r : beans) {
//                String id = r.id.trim();
//
//                /* ---------- Drug ---------- */
//                Drug drug = drugRepo.findById(id)
//                        .orElseGet(() -> {
//                            Drug d = new Drug();
//                            d.setId(id);
//                            return d;
//                        });
//                fillDrug(drug, r);
//                drug = em.merge(drug);
//
//                /* ---------- DrugDetails ---------- */
//                DrugDetails details = em.find(DrugDetails.class, id);
//                if (details == null) {
//                    details = new DrugDetails();
//                    details.setId(id);
//                    details.setDrug(drug);
//                    em.persist(details);
//                }
//                fillDetails(details, r);
//
//                if (++counter % 500 == 0) {
//                    em.flush();
//                    em.clear();
//                    log.debug("Flushed {} records", counter);
//                }
//            }
//            log.info("Імпортовано {} рядків із {}", counter, resource.getFilename());
//        }
//    }
//
//    /* -------------------- helpers -------------------- */
//
//    private void fillDrug(Drug d, ReestrCsv r) {
//        d.setTradeName(r.tradeName);
//        d.setInternationalName(r.internationalName);
//        d.setDosageForm(r.dosageForm);
//        d.setReleaseCondition(r.releaseCondition);
//        d.setComposition(r.composition);
//        d.setInstructionUrl(r.instructionUrl);
//        d.setExpiryValue(r.expiryValueRaw);
//        d.setExpiryUnit(r.expiryUnit);
//    }
//
//    private void fillDetails(DrugDetails dd, ReestrCsv r) {
//        dd.setInnType(r.innType);
//        dd.setPharmGroup(r.pharmGroup);
//
//        /* АТС-коди → одне поле через кому */
//        dd.setAtcCode(Stream.of(r.atcCode1, r.atcCode2, r.atcCode3)
//                .filter(s -> s != null && !s.isBlank())
//                .collect(Collectors.joining(", ")));
//
//        dd.setApplicantName(r.applicantName);
//        dd.setApplicantAddress(r.applicantAddress);
//
//        /* Виробники */
//        Collection<String> mfrValues = Optional.ofNullable(r.manufacturers)
//                .map(MultiValuedMap::values)
//                .orElseGet(List::of);
//
//        String manufacturers = mfrValues.stream()
//                .filter(Objects::nonNull)
//                .collect(Collectors.joining(" | "));
//        dd.setManufacturerDetails(manufacturers.isBlank() ? null : manufacturers);
//        dd.setManufacturerCount(mfrValues.isEmpty() ? null : mfrValues.size());
//
//        dd.setRegistrationNumber(r.registrationNumber);
//        dd.setRegistrationStartDate(parseDate(r.registrationStartDate));
//        dd.setRegistrationEndDate(parseDate(r.registrationEndDate));
//
//        dd.setDrugType(r.drugType);
//        dd.setBiologicalOrigin(r.biologicalOrigin);
//        dd.setHerbalOrigin(r.herbalOrigin);
//        dd.setOrphanDrug(r.orphanDrug);
//        dd.setHomeopathic(r.homeopathic);
//
//        dd.setEarlyTerminationDate(parseDate(r.earlyTerminationDate));
//        dd.setEarlyTerminationReason(r.earlyTerminationReason);
//    }
//
//    /**
//     * Парсить дату у форматі dd.MM.yyyy.
//     * Якщо поле порожнє або містить «необмежений» / «безстроково» — повертає {@code null}.
//     */
//    private LocalDate parseDate(String txt) {
//        if (txt == null) return null;
//
//        String value = txt.trim().toLowerCase(Locale.ROOT);
//        if (value.isBlank() || INFINITE_DATE_WORDS.contains(value))
//            return null;
//
//        return LocalDate.parse(value, UKR_DMY);
//    }
//
//    /* ------------------- CSV bean -------------------- */
//
//    @Getter
//    @Setter
//    public static class ReestrCsv {
//
//        @CsvBindByName(column = "ID") String id;
//
//        @CsvBindByName(column = "Торгівельне найменування")
//        String tradeName;
//
//        @CsvBindByName(column = "Міжнародне непатентоване найменування")
//        String internationalName;
//
//        @CsvBindByName(column = "Форма випуску")          String dosageForm;
//        @CsvBindByName(column = "Умови відпуску")         String releaseCondition;
//        @CsvBindByName(column = "Склад (діючі)")          String composition;
//        @CsvBindByName(column = "URL інструкції")         String instructionUrl;
//
//        @CsvBindByName(column = "Термін придатності: значення")
//        String expiryValueRaw;
//        @CsvBindByName(column = "Термін придатності: одиниця вимірювання")
//        String expiryUnit;
//
//        @CsvBindByName(column = "Тип МНН")                String innType;
//        @CsvBindByName(column = "Фармакотерапевтична група")
//        String pharmGroup;
//
//        @CsvBindByName(column = "Код АТС 1") String atcCode1;
//        @CsvBindByName(column = "Код АТС 2") String atcCode2;
//        @CsvBindByName(column = "Код АТС 3") String atcCode3;
//
//        @CsvBindByName(column = "Заявник: назва українською")
//        String applicantName;
//        @CsvBindByName(column = "Заявник: адреса")        String applicantAddress;
//
//        @CsvBindAndJoinByName(column = "Виробник .*",
//                elementType = String.class)
//        MultiValuedMap<String, String> manufacturers =
//                new ArrayListValuedHashMap<>();
//
//        @CsvBindByName(column = "Номер Реєстраційного посвідчення")
//        String registrationNumber;
//        @CsvBindByName(column = "Дата початку дії") String registrationStartDate;
//        @CsvBindByName(column = "Дата закінчення")  String registrationEndDate;
//
//        @CsvBindByName(column = "Тип ЛЗ") String drugType;
//
//        @CsvCustomBindByName(column = "ЛЗ біологічного походження",
//                converter = YesNoToBoolean.class)
//        Boolean biologicalOrigin;
//        @CsvCustomBindByName(column = "ЛЗ рослинного походження",
//                converter = YesNoToBoolean.class)
//        Boolean herbalOrigin;
//        @CsvCustomBindByName(column = "ЛЗ-сирота",
//                converter = YesNoToBoolean.class)
//        Boolean orphanDrug;
//        @CsvCustomBindByName(column = "Гомеопатичний ЛЗ",
//                converter = YesNoToBoolean.class)
//        Boolean homeopathic;
//
//        @CsvBindByName(column = "Дострокове припинення: останній день дії")
//        String earlyTerminationDate;
//        @CsvBindByName(column = "Дострокове припинення: причина")
//        String earlyTerminationReason;
//    }
//
//    /* ---------- конвертер «так/ні» → Boolean ---------- */
//
//    public static class YesNoToBoolean
//            extends AbstractBeanField<Boolean, String> {
//
//        @Override
//        protected Boolean convert(String value)
//                throws CsvDataTypeMismatchException, CsvConstraintViolationException {
//            if (value == null || value.isBlank()) return null;
//            return switch (value.trim().toLowerCase(Locale.ROOT)) {
//                case "так" -> Boolean.TRUE;
//                case "ні"  -> Boolean.FALSE;
//                default    -> throw new CsvDataTypeMismatchException(
//                        "Неприпустиме значення для Boolean: " + value);
//            };
//        }
//    }
//}
