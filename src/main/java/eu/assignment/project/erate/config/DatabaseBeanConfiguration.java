package eu.assignment.project.erate.config;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;
import java.util.ArrayList;

@Configuration
public class DatabaseBeanConfiguration {

    @Value("${datasource.server.url:}")
    private String serverUrl;

    @Value("${datasource.db.name:Examples}")
    private String databaseName;

    @Value("${datasource.collection.name:test}")
    private String collectionName;

    @Bean
    public DBCollection mongoDbCollection() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(serverUrl);
        DB database = mongoClient.getDB(databaseName);
        DBCollection dbCollection = database.getCollection(collectionName);
        checkAndInitializeRecordsIfRequired(dbCollection);
        return dbCollection;
    }

    private void checkAndInitializeRecordsIfRequired(DBCollection dbCollection) {
        DBObject query = new BasicDBObject("_id", "idKey");
        DBCursor cursor = dbCollection.find(query);
        if (cursor.one() == null) {
            DBObject arrayData = new BasicDBObject("_id", "idKey").append("data", new ArrayList<Integer>());
            dbCollection.insert(arrayData);
        }
    }
}
