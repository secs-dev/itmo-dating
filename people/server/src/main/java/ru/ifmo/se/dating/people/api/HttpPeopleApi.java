package ru.ifmo.se.dating.people.api;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.ifmo.se.dating.people.model.generated.Faculty;
import ru.ifmo.se.dating.people.model.generated.Person;
import ru.ifmo.se.dating.people.model.generated.PersonDraft;
import ru.ifmo.se.dating.people.model.generated.ZodiacSign;

import ru.ifmo.se.dating.people.api.generated.PeopleApiDelegate;

@Service
class HttpPeopleApi implements PeopleApiDelegate {

    @SuppressWarnings("checkstyle:ParameterNumber")
    @Override
    public ResponseEntity<Set<Person>> peopleGet(
        Long offset, Long limit, String firstName, String lastName,
        Integer picturesCountMin, Integer picturesCountMax,
        List<@Min(1) Long> topicId, Integer heightMin,
        Integer heightMax, LocalDate birthdayMin, LocalDate birthdayMax,
        List<ZodiacSign> zodiac, List<Faculty> faculty, Double latitude,
        Double longitude, Integer radius, OffsetDateTime updatedMin,
        OffsetDateTime updatedMax, Set<String> sortBy
    ) {
        return ResponseEntityStub.create();
    }

    @Override
    public ResponseEntity<Void> peoplePersonIdDelete(Long personId) {
        return ResponseEntityStub.create();
    }

    @Override
    public ResponseEntity<Person> peoplePersonIdGet(Long personId) {
        return ResponseEntityStub.create();
    }

    @Override
    public ResponseEntity<Void> peoplePersonIdPatch(
        Long personId, PersonDraft personDraft) {
        return ResponseEntityStub.create();
    }

    @Override
    public ResponseEntity<Person> peoplePost(
        UUID idempotencyKey, PersonDraft personDraft) {
        return ResponseEntityStub.create();
    }
}
