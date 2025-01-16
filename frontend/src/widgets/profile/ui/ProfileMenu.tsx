import { Button, Card, Image, Section } from '@telegram-apps/telegram-ui'
import { Swiper, SwiperSlide } from 'swiper/react'
import { Pagination } from 'swiper/modules'
import { CardCell } from '@telegram-apps/telegram-ui/dist/components/Blocks/Card/components/CardCell/CardCell'
import { Person } from '@/entities/person/model/Person.ts'
import { useState } from 'react'
import { useEffectOnce } from '@/shared/api'
import { Icon28Edit } from '@telegram-apps/telegram-ui/dist/icons/28/edit'
import { EditProfileMenu } from '@/widgets/editting-profile/ui/EditProfileMenu.tsx'
import { getUser } from '@/features/get_user/api/getUser.ts'
import { $authStore } from '@/features/authentication/api/authFx.ts'

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

export const ProfileMenu = () => {
  const [actualPerson, setActualPerson] = useState<Person>(actP)
  const [editButtonClicked, setEditButtonClicked] = useState<boolean>(false)
  useEffectOnce(() => {
    getUser(Number($authStore.getState().userId)).then((userResp) => {
      const user = userResp.data
      setActualPerson({
        id: user.userId,
        zodiac: user.zodiac,
        updateMoment: new Date(),
        firstName: user.firstName,
        lastName: user.lastName,
        pictures: [
          {
            id: 1,
            small: 'https://avatars.githubusercontent.com/u/93886405',
            medium: 'https://avatars.githubusercontent.com/u/93886405',
            large: 'https://avatars.githubusercontent.com/u/93886405',
          },
        ],
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
    <Section header={'Your profile'} style={{ paddingBottom: '20%' }}>
      {editButtonClicked ? (
        <EditProfileMenu setEditButtonClicked={setEditButtonClicked} />
      ) : (
        <>
          <div style={{ marginBottom: '5%' }}>
            <Button
              before={<Icon28Edit />}
              mode={'bezeled'}
              onClick={() => setEditButtonClicked(true)}
            >
              Change profile
            </Button>
          </div>
          <Card
            type="plain"
            style={{
              background: 'var(--tgui--secondary_bg_color)',
              width: '100%',
            }}
          >
            <Swiper slidesPerView={1} rewind={true} modules={[Pagination]}>
              {actualPerson?.pictures.map((p, index) => (
                <SwiperSlide key={index}>
                  <img
                    src={p.small}
                    style={{
                      width: '100%',
                      height: '100%',
                      objectFit: 'cover',
                    }}
                    alt={`Picture ${p.id}`}
                  />
                </SwiperSlide>
              ))}
            </Swiper>
            <CardCell
              readOnly
              style={{
                color: 'var(--tgui--text_color)',
                display: 'block',
                textWrap: 'pretty',
                backgroundColor: 'var(--tgui--bg_color)',
              }}
            >
              <div style={{ textWrap: 'pretty' }}>
                {actualPerson?.firstName} {actualPerson?.lastName}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                height: {actualPerson?.height}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                location: {JSON.stringify(actualPerson?.location)}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                faculty: {actualPerson?.faculty}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                birthday: {actualPerson?.birthday.toDateString()}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                lastUpdate: {actualPerson?.updateMoment.toString()}
              </div>
              <div style={{ textWrap: 'pretty' }}>id: {actualPerson?.id}</div>
              <div style={{ textWrap: 'pretty' }}>
                zodiac sign: {actualPerson?.zodiac}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                interests:{' '}
                {actualPerson?.interests.map((i) => (
                  <div key={i.topic.id}>
                    <div>
                      <div>
                        <Image size={20} src={i.topic.icon.small} />
                        <div>
                          {i.topic.id} {i.topic.name} {i.topic.color}
                        </div>
                      </div>
                    </div>
                    <div style={{ textWrap: 'pretty' }}>level: {i.level}</div>
                  </div>
                ))}
              </div>
            </CardCell>
          </Card>
        </>
      )}
    </Section>
  )
}
