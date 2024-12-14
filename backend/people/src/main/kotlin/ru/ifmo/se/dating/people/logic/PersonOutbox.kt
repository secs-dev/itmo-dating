package ru.ifmo.se.dating.people.logic

import ru.ifmo.se.dating.logic.TransactionalOutbox
import ru.ifmo.se.dating.people.model.PersonVariant
import ru.ifmo.se.dating.security.auth.User

abstract class PersonOutbox : TransactionalOutbox<PersonVariant, User.Id>()
