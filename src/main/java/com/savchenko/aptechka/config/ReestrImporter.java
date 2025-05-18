//package com.savchenko.aptechka.config;
//
//import com.opencsv.bean.CsvBindByName;
//import com.opencsv.bean.CsvToBeanBuilder;
//import com.savchenko.aptechka.entity.Drug;
//import com.savchenko.aptechka.entity.DrugDetails;
//import com.savchenko.aptechka.repository.DrugRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.Data;
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
//import java.util.List;
//
//@Component
//public class ReestrImporter implements ApplicationRunner {
//
//    private final DrugRepository drugRepo;
//
//    @PersistenceContext          // дістаємо «сирий» EntityManager
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
//        try (var reader = new InputStreamReader(
//                resource.getInputStream(), Charset.forName("Cp1251"))) {
//
//            List<ReestrCsv> beans = new CsvToBeanBuilder<ReestrCsv>(reader)
//                    .withType(ReestrCsv.class)
//                    .withSeparator(';')
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build()
//                    .parse();
//
//            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//            int counter = 0;
//
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
//                drug = em.merge(drug);           // тут merge OK – рядок точно є
//
//                /* ---------- DrugDetails ---------- */
//                DrugDetails details = em.find(DrugDetails.class, id); // спроба знайти
//                if (details == null) {                               //   — не знайшли
//                    details = new DrugDetails();
//                    details.setId(id);           // спільний PK
//                    details.setDrug(drug);
//                    em.persist(details);         // **INSERT**, без merge()
//                }
//                fillDetails(details, r, df);     // оновлюємо поля
//
//                if (++counter % 500 == 0) {      // необов’язковий batch-flush
//                    em.flush();
//                    em.clear();                  // щоб не роздувати 1-го рівня cache
//                }
//            }
//        }
//    }
//
//    /* ---------------- допоміжні методи ---------------- */
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
//    private void fillDetails(DrugDetails dd, ReestrCsv r, DateTimeFormatter df) {
//        dd.setInnType(r.innType);
//        dd.setPharmGroup(r.pharmGroup);
//        dd.setAtcCode(r.atcCode);
//        dd.setApplicantName(r.applicantName);
//        dd.setApplicantAddress(r.applicantAddress);
//        dd.setManufacturerCount(r.manufacturerCount);
//        dd.setManufacturerDetails(r.manufacturerDetails);
//        dd.setRegistrationNumber(r.registrationNumber);
//        dd.setRegistrationStartDate(parseDate(r.registrationStartDate, df));
//        dd.setRegistrationEndDate(parseDate(r.registrationEndDate, df));
//        dd.setDrugType(r.drugType);
//        dd.setBiologicalOrigin(r.biologicalOrigin);
//        dd.setHerbalOrigin(r.herbalOrigin);
//        dd.setOrphanDrug(r.orphanDrug);
//        dd.setHomeopathic(r.homeopathic);
//        dd.setEarlyTerminationDate(parseDate(r.earlyTerminationDate, df));
//        dd.setEarlyTerminationReason(r.earlyTerminationReason);
//    }
//
//    private LocalDate parseDate(String txt, DateTimeFormatter df) {
//        return (txt == null || txt.isBlank()) ? null : LocalDate.parse(txt.trim(), df);
//    }
//
//    /* ---------------- CSV bean ---------------- */
//
//    @Data
//    public static class ReestrCsv {
//        @CsvBindByName(column = "ID")                         String id;
//        @CsvBindByName(column = "Торгівельне найменування")   String tradeName;
//        @CsvBindByName(column = "Міжнародне непатентоване найменування") String internationalName;
//        @CsvBindByName(column = "Форма випуску")              String dosageForm;
//        @CsvBindByName(column = "Умови відпуску")             String releaseCondition;
//        @CsvBindByName(column = "Склад")                      String composition;
//        @CsvBindByName(column = "URL_інструкції")             String instructionUrl;
//        @CsvBindByName(column = "Термін придатності: значення")         String expiryValueRaw;
//        @CsvBindByName(column = "Термін придатності: одиниця вимірювання") String expiryUnit;
//        @CsvBindByName(column = "Тип МНН")                    String innType;
//        @CsvBindByName(column = "Фармакотерапевтична група")  String pharmGroup;
//        @CsvBindByName(column = "Код АТХ")                    String atcCode;
//        @CsvBindByName(column = "Назва заявника")             String applicantName;
//        @CsvBindByName(column = "Адреса заявника")            String applicantAddress;
//        @CsvBindByName(column = "Кількість виробників")       Integer manufacturerCount;
//        @CsvBindByName(column = "Перелік виробників")         String manufacturerDetails;
//        @CsvBindByName(column = "Номер реєстраційного посвідчення") String registrationNumber;
//        @CsvBindByName(column = "Дата початку дії")           String registrationStartDate;
//        @CsvBindByName(column = "Дата закінчення дії")        String registrationEndDate;
//        @CsvBindByName(column = "Тип ЛЗ")                     String drugType;
//        @CsvBindByName(column = "Біологічного походження")    Boolean biologicalOrigin;
//        @CsvBindByName(column = "Рослинного походження")      Boolean herbalOrigin;
//        @CsvBindByName(column = "Орфанний препарат")          Boolean orphanDrug;
//        @CsvBindByName(column = "Гомеопатичний препарат")     Boolean homeopathic;
//        @CsvBindByName(column = "Дата дострокового припинення") String earlyTerminationDate;
//        @CsvBindByName(column = "Причина дострокового припинення") String earlyTerminationReason;
//    }
//}
