package com.jd.jg.cassandra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;


@Entity
public class Cluster {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String name;
    String type;
    List<DataCenter> dcs;

}
