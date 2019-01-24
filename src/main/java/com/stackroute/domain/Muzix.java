/**
 * Muzix Application domain class and used lombok annotations to reduce the number of codes
 */
package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document //declares the class as a document
@Data //contains data to create getters and setters
@NoArgsConstructor //to create a default constructor
@AllArgsConstructor //to create a parametrized consrtuctor
@Builder // to create an instance of current class
public class Muzix {

    //Variables
    @Id
    private int trackId;
    private String trackName;
    private String comment;
}
