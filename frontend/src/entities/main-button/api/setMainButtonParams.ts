import { mainButton } from '@telegram-apps/sdk'

export function setMainButtonParams(
  text: string,
  hasShineEffect: boolean,
  isEnabled: boolean,
) {
  if (mainButton.isMounted()) {
    if (mainButton.setParams.isAvailable()) {
      mainButton.setParams({
        text: text,
        isEnabled: isEnabled,
        hasShineEffect: hasShineEffect,
      })
    }
  }
}
