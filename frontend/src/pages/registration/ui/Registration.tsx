import { RegistrationCommonInfo } from '@/widgets'
import { Placeholder, Section } from '@telegram-apps/telegram-ui'
import { useState } from 'react'
import { fetchTgUser } from '@/features/fetch-tg-user/api/fetchTgUser.ts'
import {
  setMainButtonParams,
  setMainButtonVisible,
} from '@/entities/main-button'
import { setMainButtonOnClick } from '@/entities/main-button/api/mainButtonOnClick.ts'
import { RegistrationPictures } from '@/widgets/registration-pictures'
import { setBackButtonVisible } from '@/entities/back-button'
import { setBackButtonOnClick } from '@/entities/back-button/api/backButtonOnClick.ts'
import { router } from '@/app/routes/api'
import { RegistrationInterests } from '@/widgets/registration-interests'
import { useEffectOnce } from '@/shared/api'
import { patchPerson } from '@/features/patch-person/api/patchPerson.ts'
import { PersonStatusMessage } from '@/entities/person/model/Status.ts'
import { getUser } from '@/features/get_user/api/getUser.ts'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { PersonDraftV2 } from '@/entities/person/model/Person.ts'
import {
  $registrationDataStore,
  registrationDataFx,
} from '@/entities/registration-data/api/registrationDataStore.ts'
import { deleteTopic } from '@/features/delete-topic/api/deleteTopic.ts'
import { putTopic } from '@/features/put-topic/api/putTopic.ts'

export const Registration = () => {
  const registrationOrder = [
    'registration-common',
    'registration-pictures',
    'registration-interests',
  ]

  const [key, changeKey] = useState(0)

  const tgUser = fetchTgUser()

  useEffectOnce(() => {
    registrationDataFx({
      ...$registrationDataStore.getState(),
      firstName: tgUser?.firstName || '',
      lastName: tgUser?.lastName || '',
    })
      .then()
      .catch()
  })

  useEffectOnce(() => {
    setMainButtonParams('Next', false, true)
    setButtons(0)

    getUser(Number($authStore.getState().userId)).then((userResponse) => {
      const user = userResponse.data as PersonDraftV2
      console.log('User gotten')
      console.log(user)
      const prevState = $registrationDataStore.getState()

      registrationDataFx({
        ...prevState,
        firstName: user.firstName || prevState.firstName,
        lastName: user.lastName || prevState.lastName,
        height: user.height || prevState.height,
        facultyId: user.facultyId || prevState.facultyId,
        birthday: user.birthday ? new Date(user.birthday) : prevState.birthday,
        pictures:
          user.pictures?.map((p) => {
            return { id: p.id }
          }) || [],
        interests:
          user.interests?.map((i) => {
            return { topicId: i.topicId, level: Number(i.level.toString()) }
          }) || [],
        locationId: user.locationId || prevState.locationId,
      })
        .then()
        .catch()
    })
  })

  function patch(status: PersonStatusMessage): Promise<void> {
    switch (status) {
      case PersonStatusMessage.draft: {
        //FIXME after fixing topic putting
        ;[1, 2, 3, 4, 5].forEach((t) => deleteTopic(t))

        $registrationDataStore
          .getState()
          .interests?.forEach((i) => putTopic(i.topicId, i.level))
        return patchPerson($registrationDataStore.getState(), status)
          .then((_) => console.log('Patch person draft status'))
          .catch((e) =>
            console.log(`Patch person draft error\n${e.toString()}`),
          )
      }
      case PersonStatusMessage.ready: {
        return patchPerson($registrationDataStore.getState(), status)
          .then((_) => {
            console.log('Patch person ready status')
          })
          .catch((e) => {
            console.log(`Patch person ready error\n${e.toString()}`)
          })
      }
      default: {
        throw Error('Unknown status of person status')
      }
    }
  }

  function setButtons(localKey: number) {
    switch (localKey) {
      case 0: {
        const offBack = setBackButtonOnClick(() => {
          setBackButtonVisible(false)
          setMainButtonVisible(false)
          patch(PersonStatusMessage.draft).then().catch()
          router.back()
          offBack()
          offMain()
        })
        const offMain = setMainButtonOnClick(() => {
          changeKey(1)
          setButtons(1)
          patch(PersonStatusMessage.draft).then().catch()
          offMain()
          offBack()
        })
        setBackButtonVisible(true)
        setMainButtonVisible(true)
        break
      }
      case registrationOrder.length - 1: {
        const offBack = setBackButtonOnClick(() => {
          changeKey(localKey - 1)
          setButtons(localKey - 1)
          patch(PersonStatusMessage.draft).then().catch()
          offBack()
          offMain()
        })
        const offMain = setMainButtonOnClick(() => {
          patch(PersonStatusMessage.draft).then(() =>
            patch(PersonStatusMessage.ready)
              .then(() => router.back())
              .catch(() => {
                changeKey(0)
                setButtons(0)
              }),
          )
          offMain()
          offBack()
        })
        setMainButtonVisible(true)
        setBackButtonVisible(true)
        break
      }
      default: {
        const offBack = setBackButtonOnClick(() => {
          changeKey(localKey - 1)
          setButtons(localKey - 1)
          patch(PersonStatusMessage.draft).then().catch()
          offBack()
          offMain()
        })
        const offMain = setMainButtonOnClick(() => {
          changeKey(localKey + 1)
          setButtons(localKey + 1)
          patch(PersonStatusMessage.draft).then().catch()
          offMain()
          offBack()
        })
        setMainButtonVisible(true)
        setBackButtonVisible(true)
        break
      }
    }
  }

  function renderSwitchWidget(key: number) {
    switch (registrationOrder[key]) {
      case 'registration-common': {
        return (
          <RegistrationCommonInfo
          // registrationData={registrationData}
          // changeRD={changeRD}
          />
        )
      }
      case 'registration-pictures': {
        return (
          <RegistrationPictures
          // registrationData={registrationData}
          // changeRD={changeRD}
          />
        )
      }
      case 'registration-interests': {
        return (
          <RegistrationInterests
          // registrationData={registrationData}
          // changeRD={changeRD}
          />
        )
      }
      default:
        return (
          <Placeholder description="Bonus level" header="Congratulations">
            <img
              alt="Telegram sticker"
              className="blt0jZBzpxuR4oDhJc8s"
              src="https://xelene.me/telegram.gif"
              style={{ display: 'block', width: '50%', height: '50%' }}
            />
          </Placeholder>
        )
    }
  }

  return (
    <Section header="Registration" style={{ display: 'block', height: '50%' }}>
      {renderSwitchWidget(key)}
    </Section>
  )
}
