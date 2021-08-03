package eu.assignment.project.erate.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MongoDbRepositoryService implements RepositoryService {

    @Autowired
    private DBCollection mongoDbCollection;

    @Override
    public List<Integer> listArray() {
        DBObject query = new BasicDBObject("_id", "idKey");
        DBCursor cursor = mongoDbCollection.find(query);
        return cursor.one() == null ? new ArrayList<Integer>() : (List<Integer>) cursor.one().get("data");
    }

    @Override
    public Boolean addElement(Integer element) {
        DBObject query = new BasicDBObject("_id", "idKey");
        DBCursor cursor = mongoDbCollection.find(query);
        DBObject jo = cursor.one();
        List<Integer> dbData = (List<Integer>) jo.get("data");
        dbData.add(element);
        jo.put("data", dbData);
        System.out.println(dbData);
        mongoDbCollection.save(jo);
        return Boolean.TRUE;
    }
}
