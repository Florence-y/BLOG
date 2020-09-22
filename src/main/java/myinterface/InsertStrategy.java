package myinterface;

import java.sql.PreparedStatement;

/**
 * @author Florence
 */
public interface InsertStrategy {
    /**
     *
     * @param value
     * @param sqlStatement
     * @return
     */
    PreparedStatement strategy(Object[] value,PreparedStatement sqlStatement);
}
