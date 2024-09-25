package ru.ifmo.se.dating.matchmaker.api;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
class HttpSuggestionsApi implements SuggestionsApiDelegate {

    @Override
    public ResponseEntity<Set<Long>> suggestionsGet(Long limit) {
        return ResponseEntityStub.create();
    }
}
