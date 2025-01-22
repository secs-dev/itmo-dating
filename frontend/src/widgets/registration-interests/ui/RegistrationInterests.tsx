import {
  List,
  Multiselect,
  Placeholder,
  Rating,
  Section,
} from '@telegram-apps/telegram-ui'
import { useEffect, useState } from 'react'
import { Topic } from '@/entities/registration-data/model/topic.ts'
import { getTopics } from '@/features/get-topics/api/getTopics.ts'
import { MultiselectOption } from '@telegram-apps/telegram-ui/dist/components/Form/Multiselect/types'
import { Interest } from '@/entities/registration-data/model/interest.ts'
import { useEffectOnce } from '@/shared/api'
import {
  $registrationDataStore,
  registrationDataFx,
} from '@/entities/registration-data/api/registrationDataStore.ts'

export const RegistrationInterests = () => {
  const MAX_INTEREST_COUNT = 8
  const [topics, setTopics] = useState<Array<Topic>>([])
  const [selected, setSelected] = useState<Array<MultiselectOption>>([])
  const [isFirstFlag, setFirstFlag] = useState(false)
  const [interests, setInterests] = useState<Array<Interest>>([])
  const [interestChanged, setInterestChanged] = useState(false)

  useEffectOnce(() => {
    getTopics(setTopics)
  })

  useEffect(() => {
    console.log(
      'topics selected',
      topics,
      selected,
      $registrationDataStore.getState().interests,
    )
    const rdInterests = $registrationDataStore.getState().interests || []
    const newSelected = rdInterests
      .map((i) => ({
        value: i.topicId,
        label: topics.find((t) => t.id === i.topicId)?.name,
      }))
      .filter((i) => typeof i.label !== 'undefined')
      .map((i) => i as MultiselectOption)
    const newInterests: Array<Interest> = rdInterests.map((i) => ({
      topicId: i.topicId,
      level: i.level,
    }))
    if (isFirstFlag) {
      setSelected(newSelected)
      setInterests(newInterests)
      setInterestChanged(true)
    }
    setFirstFlag(true)
  }, [topics])

  useEffect(() => {
    if (isFirstFlag) {
      setInterests((prevState) => [
        ...prevState.filter((p) =>
          selected.map((s) => Number(s.value)).includes(p.topicId),
        ),
        ...selected
          .map((s) => ({ topicId: s.value, level: 3 }))
          .filter((s) => typeof s.topicId !== 'undefined')
          .filter(
            (s) =>
              !prevState.map((ss) => ss.topicId).includes(Number(s.topicId)),
          )
          .map((s) => ({ topicId: s.topicId as number, level: s.level })),
      ])
      setInterestChanged(true)
    }
  }, [selected])

  useEffect(() => {
    console.log('interests changed')
    if (isFirstFlag && interestChanged) {
      console.log('interests changed state', $registrationDataStore.getState())
      registrationDataFx({
        ...$registrationDataStore.getState(),
        interests: interests,
      }).then()
      setInterestChanged(false)
    }
  }, [interests, interestChanged])

  return (
    <Section header="Your interests">
      <List>
        <Multiselect
          header="Topics"
          value={selected}
          onChange={setSelected}
          options={topics
            .filter(() => selected.length < MAX_INTEREST_COUNT)
            .map((topic) => {
              return { value: topic.id, label: topic.name }
            })}
        />
        <Section header="Rate your interests">
          {selected.length != 0 ? (
            selected.map((topic) => (
              <Section key={topic.value} header={topic.label}>
                <Rating
                  value={
                    interests.find((i) => i.topicId === Number(topic.value))
                      ?.level || 3
                  }
                  onChange={(e) => {
                    if (e < 1 || e > 5) {
                      throw Error('Unsupported value')
                    }
                    setInterests((prevState) => {
                      const newState = prevState
                      const foundElementIndex = newState.findIndex(
                        (i) => i.topicId === Number(topic.value),
                      )
                      newState[foundElementIndex].level = e
                      return newState
                    })
                    setInterestChanged(true)
                  }}
                  precision={1}
                />
              </Section>
            ))
          ) : (
            <Placeholder
              description="You did not choose any interests"
              header="Empty yet"
            >
              <img
                alt="Telegram sticker"
                className="blt0jZBzpxuR4oDhJc8s"
                src="https://xelene.me/telegram.gif"
                style={{ display: 'block', width: '50%', height: '50%' }}
              />
            </Placeholder>
          )}
        </Section>
      </List>
    </Section>
  )
}
