package eu.assignment.project.erate.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoDbRepositoryService implements RepositoryService {

    private final DBCollection mongoDbCollection;

    @Override
    public List<Integer> listArray() {
        DBObject query = new BasicDBObject("_id", "idKey");
        DBCursor cursor = mongoDbCollection.find(query);
        return cursor.one() == null ? new ArrayList<Integer>() : (List<Integer>) cursor.one().get("data");
    }

    @Override
    public void addElement(Integer element) {
        DBObject query = new BasicDBObject("_id", "idKey");
        DBCursor cursor = mongoDbCollection.find(query);
        DBObject dbObject = cursor.one();
        List<Integer> dbData = (List<Integer>) dbObject.get("data");
        dbData.add(element);
        dbObject.put("data", dbData);
        mongoDbCollection.save(dbObject);
    }

    @Override
    public void emptyArray() {
        DBObject query = new BasicDBObject("_id", "idKey");
        DBCursor cursor = mongoDbCollection.find(query);
        DBObject dbObject = cursor.one();
        dbObject.put("data", new ArrayList<Integer>());
        mongoDbCollection.save(dbObject);
    }
}
