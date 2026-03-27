package org.example;

import java.util.List;

public interface DotRepository {

    void save(Dot dot);

    List<Dot> findAll();

    void deleteAll();
}
