package ua.com.juja.sqlCmd.model;

/**
 * Created by Julia on 05.05.2016.
 */
public interface DataSet {
    void put(String name, Object value);

    Object[] getValues();

    String[] getNames();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
