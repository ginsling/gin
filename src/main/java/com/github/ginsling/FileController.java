package com.github.ginsling;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.ginsling.gin.repository.File;
import com.github.ginsling.gin.repository.FileRepository;

/**
 * @author cvent (28/04/16)
 */
@RestController
public class FileController
{
    private static Logger logger = LogManager.getLogger();

    @Value( "${gin.data.directory}" )
    private String storageDir;

    @Autowired
    private FileRepository fileRepository;

    @RequestMapping( value = "/file", method = RequestMethod.POST )
    public ResponseEntity uploadFile( MultipartHttpServletRequest request )
    {
        for ( Iterator<String> iterator = request.getFileNames(); iterator.hasNext(); )
        {
            MultipartFile multipartFile = request.getFile( iterator.next() );

            // write file to disk
            Path path = Paths.get( storageDir );

            try
            {
                multipartFile.transferTo( path.toFile() );
            }
            catch ( IOException e )
            {
                logger.error( "Could not write to path '" + path.toString() + "'", e );

                // todo write exception handler ???
                return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );
            }

            // persist file object
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();

            File file = new File();
            file.setId( UUID.randomUUID().toString() );
            file.setOwner( userName );

            fileRepository.save( file );
        }

        return new ResponseEntity( HttpStatus.CREATED );
    }
}
