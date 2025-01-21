import { useEffect, useState } from 'react'
import { SearchPerson } from '@/entities/person/model/Person.ts'
import { Button, Card, Section } from '@telegram-apps/telegram-ui'
import { Swiper, SwiperSlide } from 'swiper/react'
import { Pagination } from 'swiper/modules'
import { CardCell } from '@telegram-apps/telegram-ui/dist/components/Blocks/Card/components/CardCell/CardCell'
import { setBackButtonVisible } from '@/entities/back-button'
import { setBackButtonOnClick } from '@/entities/back-button/api/backButtonOnClick.ts'
import { getPeople } from '@/features/get-people/api/getPeople.ts'
import { getPicture } from '@/features/get-picture/api/getPicture.ts'
import { Topic } from '@/entities/registration-data/model/topic.ts'
import { useEffectOnce } from '@/shared/api'
import { getTopics } from '@/features/get-topics/api/getTopics.ts'
import { Faculty } from '@/entities/registration-data/model/faculty.ts'
import { Location } from '@/entities/registration-data/model/Location.ts'
import { getFaculties } from '@/features/get-faculties/api/getFaculties.ts'
import { getLocations } from '@/features/get-locations/api/getLocations.ts'

interface PictureNode {
  name: string
  base64text: string
}

const hardUrlPhoto = 'https://avatars.githubusercontent.com/u/93886405'

export const AdminPanel = () => {
  const [foundPeople, setFoundPeople] = useState<Array<SearchPerson>>([])
  // const [matchesId, setMatchesId] = useState<Array<number>>([])
  const [selectedPerson, setSelectedPerson] = useState<SearchPerson | null>(
    null,
  )
  const [pictureMap, setPictureMap] = useState<Array<PictureNode>>([])
  const [topics, setTopics] = useState<Array<Topic>>([])
  const [faculties, setFaculties] = useState<Array<Faculty>>([])
  const [locations, setLocations] = useState<Array<Location>>([])

  useEffectOnce(() => {
    getTopics(setTopics)
    getFaculties(setFaculties)
    getLocations(setLocations)
  })

  function setBackButton() {
    setBackButtonVisible(true)
    const offBack = setBackButtonOnClick(() => {
      setSelectedPerson(null)
      setBackButtonVisible(false)
      offBack()
    })
  }

  useEffect(() => {
    setPictureMap([])
    foundPeople.map((person) => {
      person.pictures.map((p) => {
        const pictureName = `photo_${person.userId}_${p.id}`
        getPicture(person.userId, p.id)
          .then((pictureB) => {
            setPictureMap((prevState) => [
              ...prevState,
              { name: pictureName, base64text: pictureB },
            ])
          })
          .catch((_) => {
            setPictureMap((prevState) => [
              ...prevState,
              { name: pictureName, base64text: hardUrlPhoto },
            ])
          })
      })
    })
  }, [foundPeople])

  return (
    <Section header={'Search people'} style={{ paddingBottom: '20%' }}>
      <Section header={'Parameters'} style={{ paddingBottom: '20%' }}>
        <button
          style={{ background: '0', border: 0 }}
          onClick={() => getPeople(setFoundPeople)}
        >
          <Button>Search</Button>
        </button>
      </Section>
      <Section header={'Result'} style={{ paddingBottom: '20%' }}>
        {!selectedPerson ? (
          <div
            key={Math.random()}
            style={{
              display: 'grid',
              gridTemplateColumns: 'repeat(2, 1fr)',
              gridTemplateRows: 'auto-fill',
              rowGap: '5px',
              columnGap: '5px',
            }}
          >
            {foundPeople.map((person) => {
              const pictureName =
                person.pictures.length > 0
                  ? `photo_${person.userId}_${person.pictures[0].id}`
                  : `photo_${person.userId}_${person.userId}`
              return (
                <Card
                  key={person.userId}
                  type="ambient"
                  onClick={() => {
                    setSelectedPerson(person)
                    setBackButton()
                  }}
                >
                  <img
                    src={
                      pictureMap.find((p) => p.name === pictureName)
                        ?.base64text || hardUrlPhoto
                    }
                    style={{
                      width: '100%',
                      height: '100%',
                      objectFit: 'cover',
                    }}
                    alt={pictureName}
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
              )
            })}
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
              {selectedPerson.pictures.map((p, index) => {
                return (
                  <SwiperSlide key={index}>
                    <img
                      src={
                        pictureMap.find(
                          (value) =>
                            value.name ===
                            `photo_${selectedPerson.userId}_${p.id}`,
                        )?.base64text || ''
                      }
                      style={{
                        width: '100%',
                        height: '100%',
                        objectFit: 'cover',
                      }}
                      alt={`Picture ${p.id}`}
                    />
                  </SwiperSlide>
                )
              })}
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
                id: {selectedPerson.userId}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                {selectedPerson.firstName} {selectedPerson.lastName}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                height: {selectedPerson.height}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                {`Location: ${locations.find((l) => l.id === selectedPerson.locationId)?.name}`}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                {JSON.stringify(
                  locations.find((l) => l.id === selectedPerson.locationId)
                    ?.coordinates,
                )}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                {`Faculty: ${faculties.find((f) => f.id === selectedPerson.facultyId)?.longName || 'unknown faculty'}`}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                birthday: {new Date(selectedPerson.birthday).toDateString()}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                zodiac sign: {selectedPerson.zodiac}
              </div>
              <div style={{ textWrap: 'pretty' }}>
                interests:
                {selectedPerson.interests.map((i) => (
                  <div key={i.topicId}>
                    <div
                      style={{
                        color: `${topics.find((t) => t.id === i.topicId)?.color || '#000000'}`,
                      }}
                    >
                      {topics.find((t) => t.id === i.topicId)?.name ||
                        'unknown'}{' '}
                      {` level: ${i.level}`}
                    </div>
                  </div>
                ))}
              </div>
            </CardCell>
          </Card>
        )}
      </Section>
    </Section>
  )
}
