package com.jd.jg.admin.cass;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;

public class CassTableScan {

    static Logger logger = LoggerFactory.getLogger(CassTableScan.class);

    String tableName;
    String primaryKey;
    int pageSize = 600;
    long maxToken = Long.MAX_VALUE;
    String condition;
    Session session;

    public CassTableScan(Session session, String tableName,String primaryKey){
        this.session = session;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
    }

    public String getColumnNames(){
        StringBuilder sb = new StringBuilder();

        ResultSet r = session.execute("select * from " + tableName + " limit 1");
        Iterator<ColumnDefinitions.Definition> it = r.getColumnDefinitions().iterator();
        while( it.hasNext()){
            sb.append(it.next().getName());
            if( it.hasNext()){
                sb.append(",");
            }
        }
        logger.info("column loaded: {}",sb.toString());
        return sb.toString();
    }

    public void load(RowParser parser){
        Statement stmt = new SimpleStatement("select * from "+tableName);
        stmt.setFetchSize(pageSize);
        ResultSet rs = session.execute(stmt);

        int i = 0;
        for(Row row:rs){
            parser.parse(row);
            i++;
        }
        logger.info("total {} records",i);
    }

    public void loadWithToken(RowParser parser){
        long actualToken = Long.MIN_VALUE;

        String token = "token("+primaryKey+")";
        String columns = null;
        try {
            columns = token + "," + getColumnNames();
        }catch (Exception ex){
            logger.error("failed to load columns for table {}",tableName,ex);
            throw new RuntimeException("failed to load columns for table "+ex,ex);
        }
        while (true) {
            logger.info("start to scan from {}",actualToken);
            if(maxTokenReached(actualToken)) {
                logger.info("Max Token Reached for {}",tableName);
                break;
            }
            String cql = "select "+columns+" from "+tableName+" where ";
            if(Strings.isNullOrEmpty(condition)){
                cql = cql + token+">="+actualToken+" limit "+pageSize;
            }else {
                cql = cql +condition+" and "+token+">="+actualToken+" limit "+pageSize;
            }

            ResultSet rs = null;
            try {
                Statement st = new SimpleStatement(cql);
                st.setFetchSize(pageSize);
                rs = session.execute(st);
            }catch (Exception ex){
                logger.warn("error detect when scan table {}",tableName,ex);
                Random r = new Random();
                int sleepSeconds = r.nextInt(10);
                try {
                    Thread.sleep(sleepSeconds*1000l);
                    rs = session.execute(cql);
                } catch (Exception e) {
                    logger.error("failed to scan table {}",tableName);
                    throw new RuntimeException("failed to scan table {}");
                }
            }
            Iterator<Row> it = rs.iterator();
            if( ! it.hasNext() ){
                // last query get empty result
                break;
            }

            while (it.hasNext()) {
                Row row = it.next();
                actualToken = row.getLong(0);
                parser.parse(row);
            }
            actualToken += 1;
        }
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean maxTokenReached(Long actualToken){
        return actualToken == maxToken;
    }

    public interface RowParser{
        void parse(Row row);
    }


}

