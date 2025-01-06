export async function api<T>(url: string): Promise<T> {
  const response = await fetch(url)
  if (!response.ok) {
    throw new Error(response.statusText)
  }
  const data = await ((await response.json()) as Promise<{ data: T }>)
  return data.data
}
export type JwtToken = string

export const backendMatchmakerUrl = 'https://itmodating.3utilities.com:8080'
export const backendPeopleUrl = 'https://itmodating.3utilities.com:8081'
export const backendAuthikUrl = 'https://itmodating.3utilities.com:8082'
