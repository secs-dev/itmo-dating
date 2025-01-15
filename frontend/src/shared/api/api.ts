export async function api<T>(url: string): Promise<T> {
  const response = await fetch(url)
  if (!response.ok) {
    throw new Error(response.statusText)
  }
  const data = await ((await response.json()) as Promise<{ data: T }>)
  return data.data
}
export type JwtToken = string

export const backendUrl = 'https://localhost:444'
//'https://itmodating.3utilities.com:444'
