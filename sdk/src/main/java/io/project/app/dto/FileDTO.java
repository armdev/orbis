/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author armen
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class FileDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private Long organizationId;
    
    private Long ownerId;
    
    private String fileName;
    
    private String contentType;
    
    private Long fileSize;
    
    private Date uploadDate;
    
    private byte[] fileContent;
}
