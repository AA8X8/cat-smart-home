package edu.rutmiit.demo.demorest.graphql.types;

import edu.rutmiit.demo.catapicontract.dto.CatResponse;
import java.util.List;

public record CatPage(
        List<CatResponse> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {}