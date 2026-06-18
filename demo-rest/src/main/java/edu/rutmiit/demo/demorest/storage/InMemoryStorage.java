package edu.rutmiit.demo.demorest.storage;

import edu.rutmiit.demo.catapicontract.dto.ActionResponse;
import edu.rutmiit.demo.catapicontract.dto.CatResponse;
import edu.rutmiit.demo.catapicontract.dto.EventResponse;
import edu.rutmiit.demo.catapicontract.dto.NotificationResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryStorage {
    public final Map<Long, CatResponse> cats = new ConcurrentHashMap<>();
    public final Map<Long, EventResponse> events = new ConcurrentHashMap<>();
    public final Map<Long, ActionResponse> actions = new ConcurrentHashMap<>();
    public final Map<Long, NotificationResponse> notifications = new ConcurrentHashMap<>();

    public final AtomicLong catSeq = new AtomicLong(0);
    public final AtomicLong eventSeq = new AtomicLong(0);
    public final AtomicLong actionSeq = new AtomicLong(0);
    public final AtomicLong notificationSeq = new AtomicLong(0);

    @PostConstruct
    public void init() {

        NotificationResponse testNotif = NotificationResponse.builder()
                .id(notificationSeq.incrementAndGet())
                .title("Тестовое уведомление")
                .body("Добро пожаловать в умный дом кота")
                .sentAt(LocalDateTime.now())
                .read(false)
                .build();
        notifications.put(testNotif.getId(), testNotif);

        // Кот 1
        CatResponse cat1 = CatResponse.builder()
                .id(catSeq.incrementAndGet())
                .name("Барсик")
                .breed("Сиамская")
                .birthDate(LocalDate.of(2020, 3, 15))
                .description("Любит спать на диване")
                .activityStatus("спит")
                .createdAt(LocalDateTime.now())
                .build();
        cats.put(cat1.getId(), cat1);

        // Кот 2
        CatResponse cat2 = CatResponse.builder()
                .id(catSeq.incrementAndGet())
                .name("Мурка")
                .breed("Персидская")
                .birthDate(LocalDate.of(2019, 7, 10))
                .description("Пушистая, любит играть")
                .activityStatus("играет")
                .createdAt(LocalDateTime.now())
                .build();
        cats.put(cat2.getId(), cat2);
    }
}