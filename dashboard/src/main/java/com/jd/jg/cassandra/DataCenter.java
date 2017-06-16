package com.jd.jg.cassandra;

import javax.persistence.Id;
import java.util.List;

public class DataCenter {

    @Id
    Long id;

    String name;
    String position;
    String type;
    List<Node> hosts;

}
