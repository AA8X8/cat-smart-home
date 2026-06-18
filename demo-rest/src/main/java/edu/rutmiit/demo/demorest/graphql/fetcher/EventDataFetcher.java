package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import edu.rutmiit.demo.catapicontract.dto.*;
import edu.rutmiit.demo.demorest.graphql.types.EventPage;
import edu.rutmiit.demo.demorest.service.EventService;

import java.time.LocalDateTime;

@DgsComponent
public class EventDataFetcher {

    private final EventService eventService;

    public EventDataFetcher(EventService eventService) {
        this.eventService = eventService;
    }

    @DgsQuery
    public EventPage events(
            @InputArgument EventType type,
            @InputArgument String catId,
            @InputArgument String from,
            @InputArgument String to,
            @InputArgument Integer page,
            @InputArgument Integer size
    ) {
        int p = page != null ? page : 0;
        int s = size != null ? size : 20;
        Long catIdLong = catId != null ? Long.parseLong(catId) : null;
        LocalDateTime fromDt = from != null ? LocalDateTime.parse(from) : null;
        LocalDateTime toDt = to != null ? LocalDateTime.parse(to) : null;
        PagedResponse<EventResponse> paged = eventService.findAll(type, catIdLong, fromDt, toDt, p, s);
        return new EventPage(paged.content(), paged.pageNumber(), paged.pageSize(),
                paged.totalElements(), paged.totalPages(), paged.last());
    }

    @DgsQuery
    public EventResponse eventById(@InputArgument String id) {
        return eventService.findById(Long.parseLong(id));
    }

    @DgsMutation
    public EventResponse createEvent(@InputArgument("input") EventRequest input) {
        return eventService.createEvent(input);
    }
}