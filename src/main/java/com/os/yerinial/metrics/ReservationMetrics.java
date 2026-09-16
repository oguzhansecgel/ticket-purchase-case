package com.os.yerinial.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ReservationMetrics {

    private static final String OPERATION_RESULTS =
            "yerinial.reservation.operation.results";

    private final MeterRegistry meterRegistry;

    public ReservationMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void reservationOperationIncrement(
            ReservationMetricOperation operation,
            MetricsOutcome outcome
    ) {
        Counter.builder(OPERATION_RESULTS)
                .description("Total reservation operation results")
                .tag("operation", operation.code())
                .tag("outcome", outcome.code())
                .register(meterRegistry)
                .increment();
    }
}
