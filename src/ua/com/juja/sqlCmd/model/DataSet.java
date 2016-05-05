package ua.com.juja.sqlCmd.model;

import java.util.List;
import java.util.Set;

/**
 * Created by Julia on 05.05.2016.
 */
public interface DataSet {
    void put(String name, Object value);

    List<Object> getValues();

    Set<String> getNames();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
