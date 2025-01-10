import axios from 'axios'
import React from 'react'
import { backendMatchmakerUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'

export function getSuggestions(setPersonIdArray:  React.Dispatch<React.SetStateAction<Array<number>>>, limit: number) {
    const url = `${backendMatchmakerUrl}/api/suggestions?limit=${limit}`;
    const config = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${$authStore.getState().token}`
        }
    }
    axios.get(url, config)
        .then((response) => {
            console.log(response.data);
            setPersonIdArray(response.data)
        })
        .catch((error) => {
            console.error("Error getting suggestions: ", error);
            setPersonIdArray([])
        })
}