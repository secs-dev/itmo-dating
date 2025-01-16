package ru.ifmo.se.dating.people.api.mapping

import ru.ifmo.se.dating.people.model.Topic
import ru.ifmo.se.dating.people.model.generated.TopicMessage

fun Topic.toMessage(): TopicMessage =
    TopicMessage(
        id = id.number.toLong(),
        name = name,
        color = color.hex,
    )
