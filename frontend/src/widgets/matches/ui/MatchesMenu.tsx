import { Card, Image, Section } from '@telegram-apps/telegram-ui'
import { Person, PersonV2 } from '@/entities/person/model/Person.ts'
import { useEffect, useState } from 'react'
import { CardCell } from '@telegram-apps/telegram-ui/dist/components/Blocks/Card/components/CardCell/CardCell'
import { Swiper, SwiperSlide } from 'swiper/react'
import { Pagination } from 'swiper/modules'
import { setBackButtonVisible } from '@/entities/back-button'
import { setBackButtonOnClick } from '@/entities/back-button/api/backButtonOnClick.ts'
import { useEffectOnce } from '@/shared/api'
import { getMatches } from '@/features/get-matches/api/getMatches.ts'
import { getUser } from '@/features/get_user/api/getUser.ts'

// const actP: Person = {
//   id: 1234567,
//   zodiac: 'aries',
//   updateMoment: new Date(),
//   firstName: 'Ivan',
//   lastName: 'Ivan',
//   pictures: [
//     {
//       id: 1,
//       small: 'https://avatars.githubusercontent.com/u/93886405',
//       medium: 'https://avatars.githubusercontent.com/u/93886405',
//       large: 'https://avatars.githubusercontent.com/u/93886405',
//     },
//     {
//       id: 2,
//       small: 'https://avatars.githubusercontent.com/u/93886405',
//       medium: 'https://avatars.githubusercontent.com/u/93886405',
//       large: 'https://avatars.githubusercontent.com/u/93886405',
//     },
//   ],
//   interests: [
//     {
//       topic: {
//         id: 123,
//         name: 'programming',
//         icon: {
//           id: 123,
//           small: 'https://avatars.githubusercontent.com/u/93886405',
//           medium: 'https://avatars.githubusercontent.com/u/93886405',
//           large: 'https://avatars.githubusercontent.com/u/93886405',
//         },
//         color: '#dasda',
//       },
//       level: 5,
//     },
//   ],
//   height: 111,
//   birthday: new Date(),
//   faculty: 'piict',
//   location: {
//     name: 'nameLoc',
//     coordinates: {
//       latitude: 321,
//       longitude: 321,
//     },
//   },
// }
//
// const actP2: Person = {
//   id: 1234567,
//   zodiac: 'aries',
//   updateMoment: new Date(),
//   firstName: 'Vitya',
//   lastName: 'Vitya',
//   pictures: [
//     {
//       id: 1,
//       small: 'https://avatars.githubusercontent.com/u/93886405',
//       medium: 'https://avatars.githubusercontent.com/u/93886405',
//       large: 'https://avatars.githubusercontent.com/u/93886405',
//     },
//     {
//       id: 2,
//       small: 'https://avatars.githubusercontent.com/u/93886405',
//       medium: 'https://avatars.githubusercontent.com/u/93886405',
//       large: 'https://avatars.githubusercontent.com/u/93886405',
//     },
//   ],
//   interests: [
//     {
//       topic: {
//         id: 123,
//         name: 'programming',
//         icon: {
//           id: 123,
//           small: 'https://avatars.githubusercontent.com/u/93886405',
//           medium: 'https://avatars.githubusercontent.com/u/93886405',
//           large: 'https://avatars.githubusercontent.com/u/93886405',
//         },
//         color: '#dasda',
//       },
//       level: 5,
//     },
//   ],
//   height: 111,
//   birthday: new Date(),
//   faculty: 'piict',
//   location: {
//     name: 'nameLoc',
//     coordinates: {
//       latitude: 321,
//       longitude: 321,
//     },
//   },
// }
//
// const actP3: Person = {
//   id: 1234567,
//   zodiac: 'aries',
//   updateMoment: new Date(),
//   firstName: 'Dima',
//   lastName: 'Dima',
//   pictures: [
//     {
//       id: 1,
//       small: 'https://avatars.githubusercontent.com/u/93886405',
//       medium: 'https://avatars.githubusercontent.com/u/93886405',
//       large: 'https://avatars.githubusercontent.com/u/93886405',
//     },
//     {
//       id: 2,
//       small: 'https://avatars.githubusercontent.com/u/93886405',
//       medium: 'https://avatars.githubusercontent.com/u/93886405',
//       large: 'https://avatars.githubusercontent.com/u/93886405',
//     },
//   ],
//   interests: [
//     {
//       topic: {
//         id: 123,
//         name: 'programming',
//         icon: {
//           id: 123,
//           small: 'https://avatars.githubusercontent.com/u/93886405',
//           medium: 'https://avatars.githubusercontent.com/u/93886405',
//           large: 'https://avatars.githubusercontent.com/u/93886405',
//         },
//         color: '#dasda',
//       },
//       level: 5,
//     },
//   ],
//   height: 111,
//   birthday: new Date(),
//   faculty: 'piict',
//   location: {
//     name: 'nameLoc',
//     coordinates: {
//       latitude: 321,
//       longitude: 321,
//     },
//   },
// }

// const _personArray = [actP, actP2, actP3, actP, actP2, actP3, actP]
export const MatchesMenu = () => {
  const [matches, setMatches] = useState<Array<Person>>([])
  const [matchesId, setMatchesId] = useState<Array<number>>([])
  const [selectedPerson, setSelectedPerson] = useState<Person | null>(null)

  useEffectOnce(() => {
    getMatches(setMatchesId)
  })

  useEffect(() => {
    for (const personId of matchesId) {
      getUser(personId).then((personResponse) => {
        const person = personResponse.data as PersonV2
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
        setMatches((prevState) => [...prevState, newUser])
      })
    }
  }, [matchesId])

  function setBackButton() {
    setBackButtonVisible(true)
    const offBack = setBackButtonOnClick(() => {
      setSelectedPerson(null)
      setBackButtonVisible(false)
      offBack()
    })
  }

  return (
    <Section header={'Your matches'} style={{ paddingBottom: '20%' }}>
      {!selectedPerson ? (
        <div
          style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(2, 1fr)',
            gridTemplateRows: 'auto-fill',
            rowGap: '5px',
            columnGap: '5px',
          }}
        >
          {matches.map((person) => (
            <Card
              key={person.id}
              type="ambient"
              onClick={() => {
                setSelectedPerson(person)
                setBackButton()
              }}
            >
              <img
                src={person.pictures[0].small}
                style={{
                  width: '100%',
                  height: '100%',
                  objectFit: 'cover',
                }}
              />
              <CardCell
                readOnly
                style={{
                  display: 'block',
                  backgroundColor: 'rgba(0, 0, 0, 0)',
                }}
              >
                {person.firstName} {person.lastName}
              </CardCell>
            </Card>
          ))}
        </div>
      ) : (
        <Card
          type="plain"
          style={{
            background: 'var(--tgui--secondary_bg_color)',
            width: '100%',
          }}
        >
          <Swiper slidesPerView={1} rewind={true} modules={[Pagination]}>
            {selectedPerson.pictures.map((p, index) => (
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
              {selectedPerson.firstName} {selectedPerson.lastName}
            </div>
            <div style={{ textWrap: 'pretty' }}>
              height: {selectedPerson.height}
            </div>
            <div style={{ textWrap: 'pretty' }}>
              location: {JSON.stringify(selectedPerson.location)}
            </div>
            <div style={{ textWrap: 'pretty' }}>
              faculty: {selectedPerson.faculty}
            </div>
            <div style={{ textWrap: 'pretty' }}>
              birthday: {selectedPerson.birthday.toDateString()}
            </div>
            <div style={{ textWrap: 'pretty' }}>
              lastUpdate: {selectedPerson.updateMoment.toString()}
            </div>
            <div style={{ textWrap: 'pretty' }}>id: {selectedPerson.id}</div>
            <div style={{ textWrap: 'pretty' }}>
              zodiac sign: {selectedPerson.zodiac}
            </div>
            <div style={{ textWrap: 'pretty' }}>
              interests:{' '}
              {selectedPerson.interests.map((i) => (
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
      )}
    </Section>
  )
}
