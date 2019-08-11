package com.dn.repository;

import com.dn.model.Rendering;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RenderingRepository extends MongoRepository<Rendering, Long> {
}
