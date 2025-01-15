import { Route } from 'atomic-router-react'
import { routes } from '../api'
import { Registration, Main } from '@/pages'
import { Welcome } from '@/pages/welcome/ui/Welcome.tsx'

export const Routes = () => (
  <>
    <Route route={routes.home} view={Welcome} />
    <Route route={routes.registration} view={Registration} />
    <Route route={routes.main} view={Main} />
  </>
)
