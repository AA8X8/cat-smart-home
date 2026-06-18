package edu.rutmiit.demo.demorest.graphql.types;

import edu.rutmiit.demo.catapicontract.dto.ActionResponse;
import java.util.List;

public record ActionPage(
        List<ActionResponse> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {}