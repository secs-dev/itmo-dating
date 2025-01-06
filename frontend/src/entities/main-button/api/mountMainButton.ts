import { mainButton } from '@telegram-apps/sdk'

export function mountMainButton() {
  if (mainButton.mount.isAvailable()) {
    mainButton.mount()
  }
}
