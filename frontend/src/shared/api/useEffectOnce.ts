import { EffectCallback, useEffect, useRef } from 'react'

export const useEffectOnce = (effect: EffectCallback) => {
  const mounted = useRef(false)
  useEffect(() => {
    if (mounted.current) {
      return
    }
    mounted.current = true
    return effect()
  }, [])
}
