package test;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import until.ElasticUtil;

import static org.junit.Assert.*;

public class ElasticUtilTest {
    @Test
    public void testGetConnection(){
        RestHighLevelClient restHighLevelClient = ElasticUtil.getRestHighLevelClient();
        System.out.println();
    }
}