package com.wallet.digitalwallet.common.response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
@Tag(name = "Stats", description = "Dashboard statistics and analytics")
public class StatsController {

    private static final Logger log = LoggerFactory.getLogger(StatsController.class);
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @Operation(
        summary = "Get dashboard statistics",
        description = "Returns total users, wallets, transactions, and money flow stats"
    )
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getDashboardStats() {
        log.info("GET /api/stats/dashboard called");
        DashboardStatsDto stats = statsService.getDashboardStats();
        return ResponseEntity.ok(
                ApiResponse.success("Dashboard stats fetched successfully", stats));
    }
}