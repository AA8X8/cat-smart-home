package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import edu.rutmiit.demo.catapicontract.dto.*;
import edu.rutmiit.demo.demorest.graphql.types.ActionInput;
import edu.rutmiit.demo.demorest.graphql.types.ActionPage;
import edu.rutmiit.demo.demorest.service.ActionService;

@DgsComponent
public class ActionDataFetcher {

    private final ActionService actionService;

    public ActionDataFetcher(ActionService actionService) {
        this.actionService = actionService;
    }

    @DgsQuery
    public ActionPage actions(
            @InputArgument String catId,
            @InputArgument ActionRequest.ActionType type,
            @InputArgument Integer page,
            @InputArgument Integer size
    ) {
        int p = page != null ? page : 0;
        int s = size != null ? size : 20;
        Long catIdLong = catId != null ? Long.parseLong(catId) : null;
        PagedResponse<ActionResponse> paged = actionService.findAll(catIdLong, type, p, s);
        return new ActionPage(paged.content(), paged.pageNumber(), paged.pageSize(),
                paged.totalElements(), paged.totalPages(), paged.last());
    }

    @DgsQuery
    public ActionResponse actionById(@InputArgument String id) {
        return actionService.findById(Long.parseLong(id));
    }

    @DgsMutation
    public ActionResponse executeAction(@InputArgument("input") ActionInput input) {
        ActionRequest request = new ActionRequest(
                ActionRequest.ActionType.valueOf(input.getType().name()),
                input.getCatId() != null ? Long.parseLong(input.getCatId()) : null,
                input.getParameters()
        );
        return actionService.executeAction(request);
    }
}