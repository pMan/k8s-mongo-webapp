package kube.demo.spring.data.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import kube.demo.spring.data.mongodb.model.Log;

public interface LogRepository extends MongoRepository<Log, String> {
  List<Log> findByHostname(String hostname);
}
