import {
  Input,
  List,
  Section,
  Select,
  Slider,
} from '@telegram-apps/telegram-ui'
import { RegistrationData } from '@/entities'
import { getAmountOfDays, useEffectOnce } from '@/shared/api'
import { useEffect, useState } from 'react'
import { getFaculties } from '@/features/get-faculties/api/getFaculties.ts'
import { Faculty } from '@/entities/registration-data/model/faculty.ts'
import { Location } from '@/entities/registration-data/model/Location.ts'
import { getLocations } from '@/features/get-locations/api/getLocations.ts'
import {
  $registrationDataStore,
  registrationDataFx,
} from '@/entities/registration-data/api/registrationDataStore.ts'

export const RegistrationCommonInfo = () => {
  const actualDate = new Date()
  const [day, setDay] = useState<number>(actualDate.getDay())
  const [month, setMonth] = useState<number>(actualDate.getMonth())
  const [year, setYear] = useState<number>(actualDate.getFullYear())
  const [faculties, setFaculties] = useState<Array<Faculty>>([])
  const [locations, setLocations] = useState<Array<Location>>([])

  useEffectOnce(() => {
    getFaculties(setFaculties)
    getLocations(setLocations)
  })

  const [registrationData, setRegistrationData] = useState<RegistrationData>(
    $registrationDataStore.getState(),
  )

  const z = registrationDataFx.doneData.watch((state) => {
    console.log('regData changed')
    setRegistrationData(state)
    z.unsubscribe()
  })

  useEffect(() => {
    if (!registrationData.facultyId && faculties.length > 0)
      // changeRD((prevState) => {
      //   return { ...prevState, facultyId: faculties[0].id }
      // })
      registrationDataFx({
        ...$registrationDataStore.getState(),
        facultyId: faculties[0].id,
      })
  }, [faculties])

  useEffect(() => {
    if (!registrationData.locationId && locations.length > 0)
      // changeRD((prevState) => {
      //   return { ...prevState, locationId: locations[0].id }
      // })
      registrationDataFx({
        ...$registrationDataStore.getState(),
        facultyId: locations[0].id,
      })
  }, [locations])

  useEffect(() => {
    // changeRD((previous) => ({
    //   ...previous,
    //   birthday: new Date(year, month, day),
    // }))
    registrationDataFx({
      ...$registrationDataStore.getState(),
      birthday: new Date(year, month, day),
    })
  }, [day, month, year])

  return (
    <Section header="Common">
      <List>
        <Input
          header="Name"
          value={registrationData.firstName || ''}
          required={true}
          onChange={(e) =>
            // changeRD((previous) => ({ ...previous, firstName: e.target.value }))
            registrationDataFx({
              ...$registrationDataStore.getState(),
              firstName: e.target.value,
            })
          }
        />
        <Input
          header="Surname"
          value={registrationData.lastName || ''}
          required={true}
          onChange={(e) =>
            // changeRD((previous) => ({ ...previous, lastName: e.target.value }))
            registrationDataFx({
              ...$registrationDataStore.getState(),
              lastName: e.target.value,
            })
          }
        />
        <Section header={`Your height ${registrationData.height} cm`}>
          <Slider
            step={1}
            defaultValue={Number(registrationData.height)}
            onChange={(e) =>
              // changeRD((previous) => ({ ...previous, height: e }))
              registrationDataFx({
                ...$registrationDataStore.getState(),
                height: e,
              })
            }
            min={50}
            max={300}
          />
        </Section>
        <Select
          header="Faculty"
          onChange={(e) =>
            // changeRD((previous) => {
            //   return { ...previous, facultyId: Number(e.target.value) }
            // })
            registrationDataFx({
              ...$registrationDataStore.getState(),
              facultyId: Number(e.target.value),
            })
          }
        >
          {faculties.map((f) => (
            <option key={f.id} value={f.id}>
              {f.longName}
            </option>
          ))}
        </Select>
        <Section header="Your birthday">
          {/*<div style={{display: "grid", gridTemplateColumns: "1fr 6fr 1fr"}}>*/}
          <Select header="Day" onChange={(e) => setDay(Number(e.target.value))}>
            {Array.from(
              { length: getAmountOfDays(month, year) },
              (_, key) => key,
            ).map((i) => (
              <option key={i + 1} value={i + 1}>
                {i + 1}
              </option>
            ))}
          </Select>
          <Select
            header="Month"
            onChange={(e) => setMonth(Number(e.target.value))}
          >
            <option value={1}>January</option>
            <option value={2}>February</option>
            <option value={3}>March</option>
            <option value={4}>April</option>
            <option value={5}>May</option>
            <option value={6}>June</option>
            <option value={7}>Jule</option>
            <option value={8}>August</option>
            <option value={9}>September</option>
            <option value={10}>October</option>
            <option value={11}>November</option>
            <option value={12}>December</option>
          </Select>
          <Select
            header="Year"
            onChange={(e) => setYear(Number(e.target.value))}
          >
            {Array.from({ length: actualDate.getFullYear() }, (_, key) => key)
              .slice(1900 - 1)
              .reverse()
              .map((i) => (
                <option key={i + 1}>{i + 1}</option>
              ))}
          </Select>
          {/*</div>*/}
        </Section>
        <Select
          header="Location"
          onChange={(e) =>
            // changeRD((previous) => {
            //   return { ...previous, locationId: Number(e.target.value) }
            // })
            registrationDataFx({
              ...$registrationDataStore.getState(),
              locationId: Number(e.target.value),
            })
          }
        >
          {locations.map((f) => (
            <option key={f.id} value={f.id}>
              {f.name}
            </option>
          ))}
        </Select>
      </List>
    </Section>
  )
}
