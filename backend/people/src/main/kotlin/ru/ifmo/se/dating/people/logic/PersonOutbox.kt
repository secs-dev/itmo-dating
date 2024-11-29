package ru.ifmo.se.dating.people.logic

import ru.ifmo.se.dating.logic.TransactionalOutbox
import ru.ifmo.se.dating.people.model.Person
import ru.ifmo.se.dating.security.auth.User

abstract class PersonOutbox : TransactionalOutbox<Person, User.Id>()
