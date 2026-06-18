package edu.rutmiit.demo.demorest.graphql.types;

import edu.rutmiit.demo.catapicontract.dto.EventResponse;
import java.util.List;

public record EventPage(
        List<EventResponse> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {}