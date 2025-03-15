package com.example.metropolitanapi.output;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Activity_Out {
    private Long id;
    private String name;
    private Date scheduled;
    private String spaces;
}
