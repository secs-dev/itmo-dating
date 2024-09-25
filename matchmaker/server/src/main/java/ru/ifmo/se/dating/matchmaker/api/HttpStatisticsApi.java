package ru.ifmo.se.dating.matchmaker.api;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.ifmo.se.dating.matchmaker.model.generated.StatisticsAttitudesGet200ResponseInner;
import ru.ifmo.se.dating.matchmaker.api.generated.StatisticsApiDelegate;

@Service
class HttpStatisticsApi implements StatisticsApiDelegate {

    @Override
    public ResponseEntity<Set<StatisticsAttitudesGet200ResponseInner>>
    statisticsAttitudesGet() {
        return ResponseEntityStub.create();
    }
}
