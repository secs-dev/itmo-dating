package ru.ifmo.se.dating.matchmaker.api;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.ifmo.se.dating.matchmaker.model.AttitudeKind;

@Service
class HttpPeopleApi implements PeopleApiDelegate {

    @Override
    public ResponseEntity<Void> peoplePersonIdAttitudesIncomingAttitudeKindPost(
            Long personId, AttitudeKind attitudeKind) {
        return ResponseEntityStub.create();
    }

    @Override
    public ResponseEntity<Set<Long>> peoplePersonIdMatchesGet(Long personId) {
        return ResponseEntityStub.create();
    }
}
