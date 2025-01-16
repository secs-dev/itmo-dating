package ru.ifmo.se.dating.people.api.mapping

import ru.ifmo.se.dating.pagging.SortingKey
import ru.ifmo.se.dating.people.logic.PersonField
import ru.ifmo.se.dating.people.model.generated.PersonSortingKeyMessage

@Suppress("LongMethod")
fun PersonSortingKeyMessage.toModel(): SortingKey<PersonField> =
    when (this) {
        PersonSortingKeyMessage.first_name -> {
            SortingKey(PersonField.FIRST_NAME, isReversed = false)
        }

        PersonSortingKeyMessage.last_name -> {
            SortingKey(PersonField.LAST_NAME, isReversed = false)
        }

        PersonSortingKeyMessage.height -> {
            SortingKey(PersonField.HEIGHT, isReversed = false)
        }

        PersonSortingKeyMessage.birthday -> {
            SortingKey(PersonField.BIRTHDAY, isReversed = false)
        }

        PersonSortingKeyMessage.updated -> {
            SortingKey(PersonField.UPDATED, isReversed = false)
        }

        PersonSortingKeyMessage.first_name_desc -> {
            SortingKey(PersonField.FIRST_NAME, isReversed = true)
        }

        PersonSortingKeyMessage.last_name_desc -> {
            SortingKey(PersonField.LAST_NAME, isReversed = true)
        }

        PersonSortingKeyMessage.height_desc -> {
            SortingKey(PersonField.HEIGHT, isReversed = true)
        }

        PersonSortingKeyMessage.birthday_desc -> {
            SortingKey(PersonField.BIRTHDAY, isReversed = true)
        }

        PersonSortingKeyMessage.updated_desc -> {
            SortingKey(PersonField.UPDATED, isReversed = true)
        }
    }
