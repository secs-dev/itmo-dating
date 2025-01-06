export function prepareInitDataRaw(initDataRaw: string | undefined): {
  tgInitDataRaw: string
  hash: string
} {
  if (initDataRaw == undefined) throw new Error('initDataRaw is undefined')

  let hash: string | undefined

  const pairs: string[] = []

  new URLSearchParams(initDataRaw).forEach((value, key) => {
    if (key === 'hash') {
      hash = value
      return
    }

    pairs.push(`${key}=${value}`)
  })

  if (hash == undefined) throw new Error('hash is undefined')

  pairs.sort()

  const tgInitDataRaw = pairs.join('\n')
  return { tgInitDataRaw, hash }
}
