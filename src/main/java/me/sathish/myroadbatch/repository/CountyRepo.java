package me.sathish.myroadbatch.repository;

import me.sathish.myroadbatch.data.Road;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public interface CountyRepo extends CrudRepository<Road, Id> {

}

