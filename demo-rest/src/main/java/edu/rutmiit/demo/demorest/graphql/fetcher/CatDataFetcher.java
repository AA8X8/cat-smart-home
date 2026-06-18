package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import edu.rutmiit.demo.catapicontract.dto.*;
import edu.rutmiit.demo.demorest.graphql.types.CatPage;
import edu.rutmiit.demo.demorest.service.CatService;

@DgsComponent
public class CatDataFetcher {

    private final CatService catService;

    public CatDataFetcher(CatService catService) {
        this.catService = catService;
    }

    @DgsQuery
    public CatPage cats(@InputArgument Integer page, @InputArgument Integer size) {
        int p = page != null ? page : 0;
        int s = size != null ? size : 20;
        PagedResponse<CatResponse> paged = catService.findAll(p, s);
        return new CatPage(paged.content(), paged.pageNumber(), paged.pageSize(),
                paged.totalElements(), paged.totalPages(), paged.last());
    }

    @DgsQuery
    public CatResponse catById(@InputArgument String id) {
        return catService.findById(Long.parseLong(id));
    }

    @DgsMutation
    public CatResponse createCat(@InputArgument("input") CatRequest input) {
        return catService.create(input);
    }

    @DgsMutation
    public CatResponse updateCat(@InputArgument String id, @InputArgument("input") CatRequest input) {
        return catService.update(Long.parseLong(id), input);
    }

    @DgsMutation
    public CatResponse patchCat(@InputArgument String id, @InputArgument("input") CatPatchRequest input) {
        return catService.patch(Long.parseLong(id), input);
    }

    @DgsMutation
    public String deleteCat(@InputArgument String id) {
        catService.delete(Long.parseLong(id));
        return id;
    }
}