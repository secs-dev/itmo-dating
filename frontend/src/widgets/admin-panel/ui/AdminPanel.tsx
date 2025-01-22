import { useEffect, useState } from 'react'
import { SearchPerson } from '@/entities/person/model/Person.ts'
import {
  Button,
  Card,
  Input,
  List,
  Multiselect,
  Section,
  Select,
} from '@telegram-apps/telegram-ui'
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
import { GetPeopleQueryParams } from '@/features/get-people/model/getPeopleQueryParams.ts'
import { MultiselectOption } from '@telegram-apps/telegram-ui/dist/components/Form/Multiselect/types'
import { ZodiacSignMessage } from '@/entities/person/model/ZodiacSign.ts'

interface PictureNode {
  name: string
  base64text: string
}

const hardUrlPhoto = 'https://avatars.githubusercontent.com/u/93886405'
const regexDate = /^\d{4}-\d{2}-\d{2}$/
const regexDateTime = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}Z$/
const sortByKeys = [
  'first_name',
  'last_name',
  'height',
  'birthday',
  'updated',
  'first_name_desc',
  'last_name_desc',
  'height_desc',
  'birthday_desc',
  'updated_desc',
]

export const AdminPanel = () => {
  const [foundPeople, setFoundPeople] = useState<Array<SearchPerson>>([])
  const [selectedPerson, setSelectedPerson] = useState<SearchPerson | null>(
    null,
  )
  const [pictureMap, setPictureMap] = useState<Array<PictureNode>>([])
  const [topics, setTopics] = useState<Array<Topic>>([])
  const [faculties, setFaculties] = useState<Array<Faculty>>([])
  const [locations, setLocations] = useState<Array<Location>>([])
  const [queryParams, setQueryParams] = useState<GetPeopleQueryParams>({
    offset: 0,
    limit: 8,
    first_name: null,
    last_name: null,
    pictures_count_min: null,
    pictures_count_max: null,
    topic_id: null,
    height_min: null,
    height_max: null,
    birthday_min: null,
    birthday_max: null,
    zodiac: null,
    facultyId: null,
    latitude: null,
    longitude: null,
    radius: null,
    updated_min: null,
    updated_max: null,
    sort_by: null,
  })
  const [topicIds, setTopicIds] = useState<Array<MultiselectOption>>([])
  const [birthdayMin, setBirthdayMin] = useState<string>('')
  const [birthdayMinStatus, setBirthdayMinStatus] = useState<
    'default' | 'error'
  >('default')
  const [birthdayMax, setBirthdayMax] = useState<string>('')
  const [birthdayMaxStatus, setBirthdayMaxStatus] = useState<
    'default' | 'error'
  >('default')
  const [updatedMin, setUpdatedMin] = useState<string>('')
  const [updatedMinStatus, setUpdatedMinStatus] = useState<'default' | 'error'>(
    'default',
  )
  const [updatedMax, setUpdatedMax] = useState<string>('')
  const [updatedMaxStatus, setUpdatedMaxStatus] = useState<'default' | 'error'>(
    'default',
  )
  const [selectedSortKeys, setSelectedSortKeys] = useState<
    Array<MultiselectOption>
  >([])

  useEffectOnce(() => {
    getTopics(setTopics)
    getFaculties(setFaculties)
    getLocations(setLocations)
  })

  useEffect(() => {
    if (topicIds.length == 0)
      setQueryParams((prevState) => ({ ...prevState, topic_id: null }))
    else
      setQueryParams((prevState) => ({
        ...prevState,
        topic_id: topicIds.map((t) => Number(t.value)),
      }))
  }, [topicIds])

  useEffect(() => {
    if (selectedSortKeys.length == 0)
      setQueryParams((prevState) => ({ ...prevState, sort_by: null }))
    else
      setQueryParams((prevState) => ({
        ...prevState,
        sort_by: selectedSortKeys.map((t) => t.value.toString()),
      }))
  }, [selectedSortKeys])

  useEffect(() => {
    const status =
      checkDate(birthdayMin) || birthdayMin === '' ? 'default' : 'error'
    setBirthdayMinStatus(status)
    if (status === 'error')
      setQueryParams((prevState) => ({ ...prevState, birthday_min: null }))
    else
      setQueryParams((prevState) => ({
        ...prevState,
        birthday_min: birthdayMin,
      }))
  }, [birthdayMin, birthdayMinStatus])

  useEffect(() => {
    const status =
      checkDate(birthdayMax) || birthdayMax === '' ? 'default' : 'error'
    setBirthdayMaxStatus(status)
    if (status === 'error')
      setQueryParams((prevState) => ({ ...prevState, birthday_max: null }))
    else
      setQueryParams((prevState) => ({
        ...prevState,
        birthday_max: birthdayMax,
      }))
  }, [birthdayMax, birthdayMaxStatus])

  useEffect(() => {
    const status =
      checkDateTime(updatedMin) || updatedMin === '' ? 'default' : 'error'
    setUpdatedMinStatus(status)
    if (status === 'error')
      setQueryParams((prevState) => ({ ...prevState, updated_min: null }))
    else
      setQueryParams((prevState) => ({ ...prevState, updated_min: updatedMin }))
  }, [updatedMin, updatedMinStatus])

  useEffect(() => {
    const status =
      checkDateTime(updatedMax) || updatedMax === '' ? 'default' : 'error'
    setUpdatedMaxStatus(status)
    if (status === 'error')
      setQueryParams((prevState) => ({ ...prevState, updated_max: null }))
    else
      setQueryParams((prevState) => ({ ...prevState, updated_max: updatedMax }))
  }, [updatedMax, updatedMaxStatus])

  function setBackButton() {
    setBackButtonVisible(true)
    const offBack = setBackButtonOnClick(() => {
      setSelectedPerson(null)
      setBackButtonVisible(false)
      offBack()
    })
  }

  function checkDate(str: string): boolean {
    return regexDate.test(str) && !isNaN(Date.parse(str))
  }

  function checkDateTime(str: string): boolean {
    return regexDateTime.test(str) && !isNaN(Date.parse(str))
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
        <List>
          <Input
            header="offset"
            value={queryParams.offset}
            required={true}
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? 0 : parseInt(e.target.value, 10)
              setQueryParams((prevState) => ({ ...prevState, offset: value }))
            }}
          />
          <Input
            header="limit"
            value={queryParams.limit}
            required={true}
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? 0 : parseInt(e.target.value, 10)
              setQueryParams((prevState) => ({ ...prevState, limit: value }))
            }}
          />
          <Input
            header="first_name"
            value={queryParams.first_name || ''}
            placeholder={'Pattern for a first name'}
            onChange={(e) => {
              const value = e.target.value.toString()
              setQueryParams((prevState) => ({
                ...prevState,
                first_name: value,
              }))
            }}
          />
          <Input
            header="last_name"
            value={queryParams.last_name || ''}
            placeholder={'Pattern for a last name'}
            onChange={(e) => {
              const value =
                e.target.value.toString().length == 0
                  ? null
                  : e.target.value.toString()
              setQueryParams((prevState) => ({
                ...prevState,
                last_name: value,
              }))
            }}
          />
          <Input
            header="pictures_count_min"
            placeholder="Minimum count of pictures in profile"
            value={
              queryParams.pictures_count_min === null
                ? ''
                : queryParams.pictures_count_min
            }
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? null : parseInt(e.target.value, 10)
              if (value !== null && !isNaN(value))
                setQueryParams((prevState) => ({
                  ...prevState,
                  pictures_count_min: value,
                }))
              else if (value === null)
                setQueryParams((prevState) => ({
                  ...prevState,
                  pictures_count_min: null,
                }))
            }}
          />
          <Input
            header="pictures_count_max"
            placeholder="Maximum count of pictures in profile"
            value={
              queryParams.pictures_count_max === null
                ? ''
                : queryParams.pictures_count_max
            }
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? null : parseInt(e.target.value, 10)
              if (value !== null && !isNaN(value))
                setQueryParams((prevState) => ({
                  ...prevState,
                  pictures_count_max: value,
                }))
              else if (value === null)
                setQueryParams((prevState) => ({
                  ...prevState,
                  pictures_count_max: null,
                }))
            }}
          />
          <Multiselect
            header="topic_ids"
            placeholder="IDs of topics interested for person. Logical and"
            value={topicIds}
            onChange={setTopicIds}
            options={topics.map((topic) => {
              return { value: topic.id, label: `${topic.name} ${topic.id}` }
            })}
          />
          <Input
            header="height_min"
            placeholder="Minimum height of a person"
            value={
              queryParams.height_min === null ? '' : queryParams.height_min
            }
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? null : parseInt(e.target.value, 10)
              if (value !== null && !isNaN(value))
                setQueryParams((prevState) => ({
                  ...prevState,
                  height_min: value,
                }))
              else if (value === null)
                setQueryParams((prevState) => ({
                  ...prevState,
                  height_min: null,
                }))
            }}
          />
          <Input
            header="height_max"
            placeholder="Maximum height of a person"
            value={
              queryParams.height_max === null ? '' : queryParams.height_max
            }
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? null : parseInt(e.target.value, 10)
              if (value !== null && !isNaN(value))
                setQueryParams((prevState) => ({
                  ...prevState,
                  height_max: value,
                }))
              else if (value === null)
                setQueryParams((prevState) => ({
                  ...prevState,
                  height_max: null,
                }))
            }}
          />
          <Input
            header="birthday_min"
            value={birthdayMin}
            placeholder="2010-10-10"
            status={birthdayMinStatus}
            onChange={(e) => setBirthdayMin(e.target.value)}
          />
          <Input
            header="birthday_max"
            value={birthdayMax}
            placeholder="2010-10-10"
            status={birthdayMaxStatus}
            onChange={(e) => setBirthdayMax(e.target.value)}
          />
          <Select
            header="Zodiac"
            onChange={(e) => {
              const value = e.target.value
              if (value === 'not selected')
                setQueryParams((prevState) => ({ ...prevState, zodiac: null }))
              else
                setQueryParams((prevState) => ({ ...prevState, zodiac: value }))
            }}
          >
            <option key={'not selected'}>not selected</option>
            {Object.keys(ZodiacSignMessage).map((z) => (
              <option key={z}>{z}</option>
            ))}
          </Select>
          <Select
            header="facultyId"
            onChange={(e) => {
              const value = e.target.value
              if (value === 'not selected')
                setQueryParams((prevState) => ({
                  ...prevState,
                  facultyId: null,
                }))
              else
                setQueryParams((prevState) => ({
                  ...prevState,
                  facultyId: Number(value),
                }))
            }}
          >
            <option key={'not selected'} value={'not selected'}>
              not selected
            </option>
            {faculties.map((f) => (
              <option key={f.id} value={f.id.toString()}>
                {f.longName}
              </option>
            ))}
          </Select>
          <Input
            header="latitude"
            placeholder="latitude"
            value={
              queryParams.latitude === null
                ? ''
                : queryParams.latitude.toFixed(7)
            }
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? null : parseFloat(e.target.value)
              if (value !== null && !isNaN(value))
                setQueryParams((prevState) => ({
                  ...prevState,
                  latitude: value,
                }))
              else if (value === null)
                setQueryParams((prevState) => ({
                  ...prevState,
                  latitude: null,
                }))
            }}
          />
          <Input
            header="longitude"
            placeholder="longitude"
            value={
              queryParams.longitude === null
                ? ''
                : queryParams.longitude.toFixed(7)
            }
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? null : parseFloat(e.target.value)
              if (value !== null && !isNaN(value))
                setQueryParams((prevState) => ({
                  ...prevState,
                  longitude: value,
                }))
              else if (value === null)
                setQueryParams((prevState) => ({
                  ...prevState,
                  longitude: null,
                }))
            }}
          />
          <Input
            header="radius"
            placeholder="Radius"
            value={queryParams.radius === null ? '' : queryParams.radius}
            onChange={(e) => {
              const value =
                e.target.value.length == 0 ? null : parseInt(e.target.value, 10)
              if (value !== null && !isNaN(value))
                setQueryParams((prevState) => ({ ...prevState, radius: value }))
              else if (value === null)
                setQueryParams((prevState) => ({ ...prevState, radius: null }))
            }}
          />
          <Input
            header="updated_min"
            value={updatedMin}
            placeholder="2024-04-14T13:32:42Z"
            status={updatedMinStatus}
            onChange={(e) => setUpdatedMin(e.target.value)}
          />
          <Input
            header="updated_max"
            value={updatedMax}
            placeholder="2024-04-14T13:32:42Z"
            status={updatedMaxStatus}
            onChange={(e) => setUpdatedMax(e.target.value)}
          />
          <Multiselect
            header="Sorting keys"
            value={selectedSortKeys}
            onChange={setSelectedSortKeys}
            options={sortByKeys.map((k) => {
              return { value: k, label: k }
            })}
          />
        </List>
        <button
          style={{ background: '0', border: 0 }}
          onClick={() => getPeople(queryParams, setFoundPeople)}
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
