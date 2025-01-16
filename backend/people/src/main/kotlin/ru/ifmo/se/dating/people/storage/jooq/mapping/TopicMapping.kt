package ru.ifmo.se.dating.people.storage.jooq.mapping

import org.jooq.generated.tables.records.TopicRecord
import ru.ifmo.se.dating.people.model.Topic

fun TopicRecord.toModel(): Topic =
    Topic(
        id = Topic.Id(id!!),
        name = name,
        color = Topic.Color(color),
    )
