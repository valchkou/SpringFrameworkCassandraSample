package com.valchkou.sample.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.gt;
import static com.datastax.driver.core.querybuilder.QueryBuilder.in;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.EntityTypeParser;
import com.datastax.driver.mapping.meta.EntityFieldMetaData;
import com.datastax.driver.mapping.meta.EntityTypeMetadata;
import com.valchkou.sample.model.Message;


@Repository
public class MessageDAO extends BaseDAO {

    public List<Message> queryUnreadMessages(String userName, UUID lastMsgId, String[] participants) {
        Statement s = buildQueryForMessages(lastMsgId, participants);
        List<Message> list = getByQuery(Message.class, s);
        Collections.sort(list);
        return list;
    }
    
    /** Retrieve all messages which timeuuid greater than given one*/
    protected <T> Statement buildQueryForMessages(UUID lastMsgId, Object[] participants) {
        EntityTypeMetadata emeta = EntityTypeParser.getEntityMetadata(Message.class);
        EntityFieldMetaData fid = emeta.getFieldMetadata("id");
        EntityFieldMetaData fun = emeta.getFieldMetadata("userName");
        if (fid != null && fun != null) {
            Select select = QueryBuilder.select().all().from(sf.getKeyspace(), emeta.getTableName());
            select.where(in(fun.getColumnName(), participants));
            if (lastMsgId != null) {
                return select.where(gt(fid.getColumnName(), lastMsgId));
            }
            return select;
        }
        return null;
    }   
}
