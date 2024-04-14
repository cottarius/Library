//package ru.cotarius.hibernatecourse.library.controllers.metric;
//
//import io.micrometer.core.instrument.MeterRegistry;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import ru.cotarius.hibernatecourse.library.service.IssueService;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Component
//public class MyMetric {
//
////    @Autowired
//    private final IssueService issueService;
//    private final AtomicInteger number;
//
//    public MyMetric(MeterRegistry meterRegistry, IssueService issueService) {
//        this.issueService = issueService;
//        number = meterRegistry.gauge("issues", new AtomicInteger());
//
//    }
//
//    @Scheduled(fixedDelay = 2000, initialDelay = 0)
//    public void schedulingTask() {
//        int counter = issueService.findAll().size();
//        number.set(counter);
//    }
//}
