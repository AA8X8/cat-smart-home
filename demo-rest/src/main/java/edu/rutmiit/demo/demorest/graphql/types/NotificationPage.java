package edu.rutmiit.demo.demorest.graphql.types;

import edu.rutmiit.demo.catapicontract.dto.NotificationResponse;
import java.util.List;

public record NotificationPage(
        List<NotificationResponse> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {}