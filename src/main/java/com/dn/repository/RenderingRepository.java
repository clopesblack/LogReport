package com.dn.repository;

import com.dn.model.Rendering;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenderingRepository extends MongoRepository<Rendering, String> {
}
