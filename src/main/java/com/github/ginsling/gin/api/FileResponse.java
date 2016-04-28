package com.github.ginsling.gin.api;

import lombok.Getter;
import lombok.Setter;

/**
 * Response sent after file was uploaded. Containing the id the file was stored under and it's original uploadName
 */
@Getter
@Setter
public class FileResponse
{
    private String id;

    private String uploadName;
}
