/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 * @author armena
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Claim implements Serializable{
    
    private String iss;
    private String sub;
    private String aud;
    private String iat;
    private String exp;
    
}
