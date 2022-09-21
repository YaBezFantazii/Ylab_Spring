package com.edu.ulab.app.storage;

import java.util.*;

public interface Storage<K> {
    K newEntity (K entity);
    K updateEntity (K entity);
    K getEntity (Long id);
    List<K> getListEntity (Long id);
    void deleteEntity(Long id);
}
