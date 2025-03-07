package ru.ifmo.se.dating.people.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.ifmo.se.dating.people.api.generated.TopicsApiDelegate
import ru.ifmo.se.dating.people.api.mapping.toMessage
import ru.ifmo.se.dating.people.logic.InterestService
import ru.ifmo.se.dating.people.model.generated.TopicMessage

@Controller
class HttpTopicsApi(
    private val interestService: InterestService,
) : TopicsApiDelegate {
    override fun topicsGet(): ResponseEntity<Flow<TopicMessage>> = runBlocking {
        interestService.getAllTopics()
            .map { it.toMessage() }
            .let { ResponseEntity.ok(it) }
    }
}
