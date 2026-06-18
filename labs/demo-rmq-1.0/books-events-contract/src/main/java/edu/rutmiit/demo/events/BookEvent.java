package edu.rutmiit.demo.events;

/**
 * Семейство событий, связанных с книгами.
 * <p>
 * Sealed interface ограничивает набор наследников — компилятор гарантирует,
 * что BookEvent может быть только Created, Updated или Deleted.
 * Это делает switch/pattern matching исчерпывающим, нет риска забыть обработать какой-то тип события.:
 * <p>
 * switch (event) {
 * case BookEvent.Created c -> ...
 * case BookEvent.Updated u -> ...
 * case BookEvent.Deleted d -> ...
 * // компилятор знает, что других вариантов нет
 * }
 * <p>
 * Полиморфная десериализация выполняется не через Jackson-аннотации,
 * а через поле eventType в EventMetadata — consumer определяет конкретный тип
 * по routing key и десериализует payload в нужный record напрямую.
 */
public sealed interface BookEvent {

    /**
     * Книга создана. Содержит все ключевые атрибуты новой книги.
     */
    record Created(
            Long bookId,
            String title,
            String isbn,
            Long authorId,
            String authorFullName,
            String genre,
            Integer publishedYear
    ) implements BookEvent {
    }

    /**
     * Книга обновлена. Содержит актуальное состояние после обновления.
     * В промышленных системах здесь могут быть и старые значения (before/after),
     * но для демонстрации достаточно нового состояния.
     */
    record Updated(
            Long bookId,
            String title,
            String isbn,
            String genre,
            Integer publishedYear
    ) implements BookEvent {
    }

    /**
     * Книга удалена. Достаточно идентификатора — после удаления данных нет.
     */
    record Deleted(
            Long bookId,
            String title
    ) implements BookEvent {
    }

    /**
     * Книга обогащена аналитикой — результат gRPC-вызова к analytics-серверу.
     * <p>
     * Событие публикуется grpc-enrichment-client после получения ответа
     * от grpc-analytics-server. Содержит вычисленные метрики книги.
     */
    record Enriched(
            Long bookId,
            String title,
            int estimatedReadingMinutes,
            String difficultyLevel,
            double recommendationScore,
            String eraClassification
    ) implements BookEvent {
    }
}
