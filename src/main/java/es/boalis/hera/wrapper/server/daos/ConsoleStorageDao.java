package es.boalis.hera.wrapper.server.daos;

import es.boalis.hera.wrapper.server.BeanMessage;
import es.boalis.hera.wrapper.server.StorageDao;

public class ConsoleStorageDao implements StorageDao {

    @Override
    public void insert(BeanMessage message) throws Exception {
        System.out.println(message);
    }
}
