package ru.ifmo.se.dating.people.exception

import ru.ifmo.se.dating.security.auth.User

class IncompletePersonException(id: User.Id, field: String) :
    DomainException("Person with id $id is incomplete: field $field is null")
