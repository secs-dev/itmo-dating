package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.people.api.generated.TopicsApiDelegate
import ru.ifmo.se.dating.people.api.mapping.toMessage
import ru.ifmo.se.dating.people.logic.TopicService
import ru.ifmo.se.dating.people.model.generated.TopicMessage

@Controller
class HttpTopicsApi(
    private val topicService: TopicService,
) : TopicsApiDelegate {
    override fun topicsGet(): ResponseEntity<Flow<TopicMessage>> =
        topicService.getAll()
            .map { it.toMessage() }
            .let { ResponseEntity.ok(it) }
}
