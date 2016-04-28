package com.github.ginsling.gin.repository;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class File
{
    @Id
    private String id;

    private String owner;

    private boolean isPublic = false;
}
