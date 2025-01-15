import { Person } from '@/entities/person/model/Person.ts'
import { backendPeopleUrl } from '@/shared/api'
import axios from 'axios'
import { PersonPatch } from '@/entities/person-patch/model/PersonPatch.ts'

export function editProfile(profile: Person) {
  const url = `${backendPeopleUrl}/api/people/${profile.id}`

  // FIXME jwt token
  const basicAuth = 'test basic auth'
  const config = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: +basicAuth,
    },
  }
  const profilePatch: PersonPatch = {
    status: 'ready',
    firstName: profile.firstName,
    lastName: profile.lastName,
    interests: profile.interests.map((interest) => {
      return { topicId: interest.topic.id, level: interest.level }
    }),
    height: profile.height,
    birthday: `${profile.birthday.getFullYear()}-${profile.birthday.getMonth()}-${profile.birthday.getDay()}`,
    facultyId: 1,
    locationId: 1,
  }
  axios
    .post(url, profilePatch, config)
    .then((response) => {
      console.log(response.data)
    })
    .catch((error) => {
      console.error('Error patching: ', error)
    })
}
