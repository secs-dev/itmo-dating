import { createHistoryRouter, createRoute } from 'atomic-router'
import { createBrowserHistory } from 'history'

const basePath = '/itmo-dating-mini-app'
export const routes = {
  home: createRoute(),
  registration: createRoute(),
  main: createRoute(),
}

export const routesMap = [
  { path: '', route: routes.home },
  { path: '/registration', route: routes.registration },
  { path: '/main', route: routes.main },
]

const history = createBrowserHistory()

export const router = createHistoryRouter({ base: basePath, routes: routesMap })

router.setHistory(history)
