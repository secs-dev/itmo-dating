import { useEffectOnce } from '@/shared/api'
import { authenticate } from '@/widgets/auth-test/api/authenticate.ts'
import { redirect } from 'atomic-router'
import {
  $authStore,
  userRegisteredFx,
} from '@/features/authentication/api/authFx.ts'
import { routes } from '@/app/routes/api'
import { getUser } from '@/features/get_user/api/getUser.ts'
import { Spinner } from '@telegram-apps/telegram-ui'

export const Welcome = () => {
  redirect({ clock: userRegisteredFx.doneData, route: routes.main })
  redirect({ clock: userRegisteredFx.failData, route: routes.registration })
  useEffectOnce(() => {
    authenticate().then(() => {
      const user = $authStore.getState()
      getUser(Number(user.userId))
        .then((x) => {
          if (x.data.status === 'ready') {
            userRegisteredFx(true).then()
          } else {
            userRegisteredFx(false).then()
          }
        })
        .catch(() => {
          userRegisteredFx(false).then()
        })
    })
  })

  return (
    <div>
      <Spinner size={'l'} />
    </div>
  )
}
