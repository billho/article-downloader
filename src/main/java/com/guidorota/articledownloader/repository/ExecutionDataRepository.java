package com.guidorota.articledownloader.repository;

import com.guidorota.articledownloader.entity.ExecutionData;

import java.util.List;

public interface ExecutionDataRepository {

    void store(ExecutionData executionData);

    List<ExecutionData> getAll();

}
