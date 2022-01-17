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
public class RoadsJobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoadsJobCompletionNotificationListener.class);
    private final EntityManager entityManager;
    @Autowired
    private RoadForCosmosRepo roadForCosmosRepo;

    @Autowired
    public RoadsJobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.debug("Finishing up H2 insert. Going to Couch DB");
//        this.roadForCosmosRepo.deleteAll().block();
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            Object count = entityManager.createQuery("from Road").getResultList().get(0);
            List<Road> roadList = entityManager.createQuery("from Road ").getResultList();
//            processForCosmosDB(roadList);
            System.out.println("The count is " + count);
        }
    }

    private void processForCosmosDB(List<Road> roadList) {
        this.roadForCosmosRepo.deleteAll().block();
        roadList.stream().forEach(road -> {
            RoadForCosmos roadForCosmos = new RoadForCosmos();
            roadForCosmos.setId(road.getId());
            roadForCosmos.setCounty(road.getCounty());
            roadForCosmos.setCommunity(road.getCommunity());
            roadForCosmos.setMiles(road.getMiles());
            roadForCosmos.setRoad_name(road.getRoad_name());
            roadForCosmos.setLine_to_web_page(road.getLine_to_web_page());
            roadForCosmos.setRustic_road_number(road.getRustic_road_number());
            final RoadForCosmos data = roadForCosmosRepo.save(roadForCosmos).block();
        });
    }
}
