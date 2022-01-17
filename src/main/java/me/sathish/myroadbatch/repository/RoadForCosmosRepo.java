package me.sathish.myroadbatch.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import me.sathish.myroadbatch.data.RoadForCosmos;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RoadForCosmosRepo extends ReactiveCosmosRepository<RoadForCosmos, String> {
    Flux<RoadForCosmos> findByMiles(String miles);
}
