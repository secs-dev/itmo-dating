import { Swiper, SwiperClass, SwiperSlide } from 'swiper/react'
import { Person } from '@/entities/person/model/Person.ts'
import { useEffect, useState } from 'react'
import { Card, Image, Section } from '@telegram-apps/telegram-ui'
import { CardCell } from '@telegram-apps/telegram-ui/dist/components/Blocks/Card/components/CardCell/CardCell'
import 'swiper/css'
import 'swiper/css/pagination'

import { Pagination } from 'swiper/modules'
import { CardChip } from '@telegram-apps/telegram-ui/dist/components/Blocks/Card/components/CardChip/CardChip'
import { setAttitude } from '@/features/set-attitude/api/setAttitude.ts'
import { useEffectOnce } from '@/shared/api'
import { getSuggestions } from '@/features/get-suggestions/api/getSuggestions.ts'
import { getUser } from '@/features/get_user/api/getUser.ts'

//src={"https://avatars.githubusercontent.com/u/93886405"}

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

const actP2: Person = {
  id: 1234567,
  zodiac: 'aries',
  updateMoment: new Date(),
  firstName: 'Vitya',
  lastName: 'Vitya',
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

const actP3: Person = {
  id: 1234567,
  zodiac: 'aries',
  updateMoment: new Date(),
  firstName: 'Dima',
  lastName: 'Dima',
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

const personArrayOld = [actP, actP2, actP3]
personArrayOld.map(() => {})
export const SwiperMenu = () => {
  const [_, setSwiperRef] = useState<SwiperClass | undefined>()
  const [iter, setIter] = useState<number>(0)
  const [actualPerson, setActualPerson] = useState<Person>(actP)
  const [attitudeGotten, setAttitudeGotten] = useState<boolean>(false)
  const [personArray, setPersonArray] = useState<Array<Person>>([])
  const [personIdArray, setPersonIdArray] = useState<Array<number>>([])

  useEffectOnce(() => {
    getSuggestions(setPersonIdArray, 20)
  })

  useEffect(() => {
    for (const personId of personIdArray) {
      getUser(personId).then((personResponse) => {
        const person = personResponse.data
        const newUser: Person = {
          id: person.userId,
          zodiac: person.zodiac,
          updateMoment: new Date(),
          firstName: person.firstName,
          lastName: person.lastName,
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
          height: person.height,
          birthday: new Date(person.birthday),
          faculty: 'piict',
          location: {
            name: 'nameLoc',
            coordinates: {
              latitude: 321,
              longitude: 321,
            },
          },
        }
        setPersonArray((prevState) => [...prevState, newUser])
      })
    }
  }, [personIdArray])

  useEffect(() => {
    if (attitudeGotten && personArray.length > 0) {
      setActualPerson(personArray[(iter + 1) % personArray.length])
      setIter((prevState) => (prevState + 1) % personArray.length)
      setAttitudeGotten(false)
    }
    getSuggestions(setPersonIdArray, 5)
  }, [attitudeGotten])

  return (
    <Section
      header={'Recommendations'}
      style={{ display: 'block', height: '50%', paddingBottom: '20%' }}
    >
      <Card
        type="plain"
        style={{
          background: 'var(--tgui--secondary_bg_color)',
          width: '100%',
        }}
      >
        <CardChip
          readOnly
          onClick={(_) => {
            setAttitude(actualPerson.id, 'skip')
            setAttitudeGotten(true)
          }}
          style={{
            left: '7%',
            marginTop: '75%',
            width: '35%',
            height: '7%',
            zIndex: '2',
            textAlign: 'center',
            backgroundColor: 'red',
          }}
        >
          Skip
        </CardChip>
        <CardChip
          readOnly
          onClick={(_) => {
            setAttitude(actualPerson.id, 'like')
            setAttitudeGotten(true)
          }}
          style={{
            right: '7%',
            marginTop: '75%',
            width: '35%',
            height: '7%',
            zIndex: '2',
            textAlign: 'center',
            backgroundColor: 'green',
          }}
        >
          Like
        </CardChip>
        <Swiper
          slidesPerView={1}
          rewind={true}
          onSwiper={setSwiperRef}
          modules={[Pagination]}
        >
          {actualPerson.pictures.map((p, index) => (
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
            {actualPerson.firstName} {actualPerson.lastName}
          </div>
          <div style={{ textWrap: 'pretty' }}>
            height: {actualPerson.height}
          </div>
          <div style={{ textWrap: 'pretty' }}>
            location: {JSON.stringify(actualPerson.location)}
          </div>
          <div style={{ textWrap: 'pretty' }}>
            faculty: {actualPerson.faculty}
          </div>
          <div style={{ textWrap: 'pretty' }}>
            birthday: {actualPerson.birthday.toDateString()}
          </div>
          <div style={{ textWrap: 'pretty' }}>
            lastUpdate: {actualPerson.updateMoment.toString()}
          </div>
          <div style={{ textWrap: 'pretty' }}>id: {actualPerson.id}</div>
          <div style={{ textWrap: 'pretty' }}>
            zodiac sign: {actualPerson.zodiac}
          </div>
          <div style={{ textWrap: 'pretty' }}>
            interests:{' '}
            {actualPerson.interests.map((i) => (
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
    </Section>
  )
}
