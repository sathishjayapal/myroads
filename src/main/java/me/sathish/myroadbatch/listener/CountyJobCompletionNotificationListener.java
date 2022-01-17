package me.sathish.myroadbatch.listener;

import me.sathish.myroadbatch.data.Road;
import me.sathish.myroadbatch.data.RoadForCosmos;
import me.sathish.myroadbatch.repository.RoadForCosmosRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class CountyJobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountyJobCompletionNotificationListener.class);
    private final EntityManager entityManager;

    @Autowired
    public CountyJobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Object count = entityManager.createQuery("from County").getResultList().get(0);
            System.out.println("The count is " + count);
        }
    }
}
