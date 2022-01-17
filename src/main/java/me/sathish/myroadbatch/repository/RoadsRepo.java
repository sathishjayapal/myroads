package me.sathish.myroadbatch.repository;

import me.sathish.myroadbatch.data.Road;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface RoadsRepo extends JpaRepository<Road, Id> {

}

