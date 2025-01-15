import axios from 'axios'
import React from 'react'
import { backendPeopleUrl } from '@/shared/api'
import { Location } from '@/entities/registration-data/model/Location.ts'

export function getLocations(
  setLocations: React.Dispatch<React.SetStateAction<Array<Location>>>,
) {
  const url = `${backendPeopleUrl}/api/locations`

  axios
    .get(url)
    .then((response) => {
      console.log(response.data)
      //FIXME after integration with backend
      setLocations(response.data)
    })
    .catch((error) => {
      console.error('Error getting locations: ', error)
      //TODO remove after integration with backend
      setLocations([
        {
          id: 1,
          name: 'ITMO University, Kronverkskiy Avenue',
          coordinates: {
            latitude: 59.957478,
            longitude: 30.308014,
          },
        },
      ])
    })
}
