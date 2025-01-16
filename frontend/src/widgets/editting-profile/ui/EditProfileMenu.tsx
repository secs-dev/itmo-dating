import { Person } from '@/entities/person/model/Person.ts'
import { useState } from 'react'
import { useEffectOnce } from '@/shared/api'
import {
  Button,
  Input,
  List,
  Section,
  Select,
  Slider,
} from '@telegram-apps/telegram-ui'
import { editProfile } from '@/features/edit-profile/api/editProfile.ts'
import { getUser } from '@/features/get_user/api/getUser.ts'
import { $authStore } from '@/features/authentication/api/authFx.ts'
// import {Picture} from "@/entities/person/model/Picture.ts";
// import {Interest} from "@/entities/person/model/Interest.ts";
// import {Location} from "@/entities/person/model/Location.ts";

const actP: Person = {
  id: 1234567,
  zodiac: 'aries',
  updateMoment: new Date(),
  firstName: 'Ivan',
  lastName: 'Ivan',
  pictures: [
    {
      id: 1,
      small: 'https://avatars.githubusercontent.com/u/93886405',
      medium: 'https://avatars.githubusercontent.com/u/93886405',
      large: 'https://avatars.githubusercontent.com/u/93886405',
    },
    {
      id: 2,
      small: 'https://avatars.githubusercontent.com/u/93886405',
      medium: 'https://avatars.githubusercontent.com/u/93886405',
      large: 'https://avatars.githubusercontent.com/u/93886405',
    },
  ],
  interests: [
    {
      topic: {
        id: 123,
        name: 'programming',
        icon: {
          id: 123,
          small: 'https://avatars.githubusercontent.com/u/93886405',
          medium: 'https://avatars.githubusercontent.com/u/93886405',
          large: 'https://avatars.githubusercontent.com/u/93886405',
        },
        color: '#dasda',
      },
      level: 5,
    },
  ],
  height: 111,
  birthday: new Date(),
  faculty: 'piict',
  location: {
    name: 'nameLoc',
    coordinates: {
      latitude: 321,
      longitude: 321,
    },
  },
}

interface EditProfileMenuProps {
  setEditButtonClicked: (value: boolean) => void
}

export const EditProfileMenu = ({
  setEditButtonClicked,
}: EditProfileMenuProps) => {
  const [actualPerson, setActualPerson] = useState<Person>(actP)

  useEffectOnce(() => {
    getUser(Number($authStore.getState().userId)).then((userResp) => {
      const user = userResp.data
      setActualPerson({
        id: user.userId,
        zodiac: user.zodiac,
        updateMoment: new Date(),
        firstName: user.firstName,
        lastName: user.lastName,
        pictures: [],
        interests: user.interests,
        height: user.height,
        birthday: new Date(user.birthday),
        faculty: `${user.facultyId}`,
        location: {
          name: 'unknown',
          coordinates: { latitude: 1, longitude: 2 },
        },
      })
    })
  })

  return (
    <>
      <List>
        <Input
          header="Name"
          value={actualPerson.firstName}
          onChange={(e) =>
            setActualPerson((previous) => ({
              ...previous,
              firstName: e.target.value,
            }))
          }
        />
        <Input
          header="Surname"
          value={actualPerson.lastName}
          onChange={(e) =>
            setActualPerson((previous) => ({
              ...previous,
              lastName: e.target.value,
            }))
          }
        />
        <Section header={`Your height ${actualPerson.height} cm`}>
          <Slider
            step={1}
            defaultValue={Number(actualPerson.height)}
            onChange={(e) =>
              setActualPerson((previous) => ({ ...previous, height: e }))
            }
            min={50}
            max={300}
          />
        </Section>
        <Select
          header="Faculty"
          onChange={(e) =>
            setActualPerson((previous) => ({
              ...previous,
              faculty: e.target.value,
            }))
          }
        >
          <option>ПИиКТ</option>
          <option>Другое</option>
        </Select>
        <Button
          onClick={() => {
            editProfile(actualPerson)
            setEditButtonClicked(false)
          }}
        >
          Edit profile
        </Button>
      </List>
    </>
  )
}
