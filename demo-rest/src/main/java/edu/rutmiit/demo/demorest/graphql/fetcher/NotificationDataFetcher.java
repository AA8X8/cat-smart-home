package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import edu.rutmiit.demo.catapicontract.dto.NotificationResponse;
import edu.rutmiit.demo.catapicontract.dto.PagedResponse;
import edu.rutmiit.demo.demorest.graphql.types.NotificationPage;
import edu.rutmiit.demo.demorest.service.NotificationService;

import java.time.LocalDateTime;

@DgsComponent
public class NotificationDataFetcher {

    private final NotificationService notificationService;

    public NotificationDataFetcher(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @DgsQuery
    public NotificationPage notifications(
            @InputArgument Boolean read,
            @InputArgument String from,
            @InputArgument String to,
            @InputArgument Integer page,
            @InputArgument Integer size
    ) {
        int p = page != null ? page : 0;
        int s = size != null ? size : 20;
        LocalDateTime fromDt = from != null ? LocalDateTime.parse(from) : null;
        LocalDateTime toDt = to != null ? LocalDateTime.parse(to) : null;
        PagedResponse<NotificationResponse> paged = notificationService.findAll(read, fromDt, toDt, p, s);
        return new NotificationPage(paged.content(), paged.pageNumber(), paged.pageSize(),
                paged.totalElements(), paged.totalPages(), paged.last());
    }

    @DgsQuery
    public NotificationResponse notificationById(@InputArgument String id) {
        return notificationService.findById(Long.parseLong(id));
    }

    @DgsMutation
    public NotificationResponse markNotificationAsRead(@InputArgument String id) {
        return notificationService.markAsRead(Long.parseLong(id));
    }
}