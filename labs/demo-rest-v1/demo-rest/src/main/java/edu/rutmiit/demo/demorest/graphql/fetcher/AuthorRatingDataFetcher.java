package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import edu.rutmiit.demo.booksapicontract.dto.AuthorResponse;

/**
 * DataFetcher для вычисляемого поля averageRating у автора.
 * <p>
 * Срабатывает когда клиент запрашивает средний рейтинг автора:
 * <p>
 * query {
 * author(id: "1") {
 * fullName
 * averageRating
 * }
 * }
 * <p>
 * Поле averageRating не хранится в базе данных, а вычисляется на лету.
 * В реальном проекте здесь была бы логика расчёта на основе отзывов на книги.
 */
@DgsComponent
public class AuthorRatingDataFetcher {

    /**
     * Возвращает средний рейтинг автора.
     *
     * @param dfe окружение с доступом к родительскому объекту Author
     */
    @DgsData(parentType = "Author", field = "averageRating")
    public Float averageRating(DataFetchingEnvironment dfe) {
        // Получаем автора, для которого вычисляется рейтинг
        AuthorResponse author = dfe.getSource();

        return 4.5f;
    }
}