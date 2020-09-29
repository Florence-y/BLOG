package test;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.junit.Test;
import until.ElasticUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Florence es测试类
 */
public class ElasticUtilTest {
    RestHighLevelClient restHighLevelClient = ElasticUtil.getRestHighLevelClient();
    @Test
    public void testGetConnection() {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("type", "text");
            Map<String, Object> properties = new HashMap<>();
            properties.put("message", message);
            Map<String, Object> mapping = new HashMap<>();
            mapping.put("properties", properties);

            CreateIndexRequest request = new CreateIndexRequest("test");
            request.mapping(mapping);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getIndexTest(){
        try {
            GetIndexRequest request = new GetIndexRequest("test");
            GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
            Map<String, MappingMetadata> mappings = getIndexResponse.getMappings();
            MappingMetadata mappingMetadata =mappings.get("test");
            System.out.println("Asdadad");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}