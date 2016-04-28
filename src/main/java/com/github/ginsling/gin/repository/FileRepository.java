package com.github.ginsling.gin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository
    extends CrudRepository<File, String>
{
}
