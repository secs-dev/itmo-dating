import {
  List,
  Multiselect,
  Placeholder,
  Rating,
  Section,
} from '@telegram-apps/telegram-ui'
import React, { useEffect, useState } from 'react'
import { Topic } from '@/entities/registration-data/model/topic.ts'
import { getTopics } from '@/features/get-topics/api/getTopics.ts'
import { MultiselectOption } from '@telegram-apps/telegram-ui/dist/components/Form/Multiselect/types'
import { RegistrationData } from '@/entities'
import { Interest } from '@/entities/registration-data/model/interest.ts'
import { useEffectOnce } from '@/shared/api'

interface RegistrationInterestsProps {
  registrationData: RegistrationData
  changeRD: React.Dispatch<React.SetStateAction<RegistrationData>>
}

export const RegistrationInterests = ({
  changeRD,
}: RegistrationInterestsProps) => {
  const MAX_INTEREST_COUNT = 8
  const [topics, setTopics] = useState<Array<Topic>>([])
  const [selected, setSelected] = useState<Array<MultiselectOption>>([])

  useEffectOnce(() => {
    getTopics(setTopics)
  })

  const [interests, setInterests] = useState<Map<number, Interest>>(new Map())

  useEffect(() => {
    setInterests((prevState) => {
      const oldInterests = Array.from(prevState.values()).filter(
        (i) => i.topicId in selected.map((s) => s.value),
      )
      const newSelectedOptionInterests = selected.filter(
        (s) => !(Number(s.value) in interests),
      )
      const newSelectedInterests: Interest[] = newSelectedOptionInterests.map(
        (s) => {
          return {
            topicId: Number(s.value),
            level: 5,
          }
        },
      )
      const unitedInterests = [...oldInterests, ...newSelectedInterests]
      const newMap: Map<number, Interest> = new Map()

      unitedInterests.forEach((i) => newMap.set(i.topicId, i))

      return newMap
    })
  }, [selected])

  useEffect(() => {
    changeRD((prevState) => {
      return { ...prevState, interests: Array.from(interests.values()) }
    })
  }, [interests])
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
                  value={interests.get(Number(topic.value))?.level}
                  onChange={(e) =>
                    setInterests((prevState) => {
                      const newMap = new Map(prevState)
                      newMap.set(Number(topic.value), {
                        topicId: Number(topic.value),
                        level: e,
                      })
                      return newMap
                    })
                  }
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
