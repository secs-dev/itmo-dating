import { Tabbar } from '@telegram-apps/telegram-ui'
import 'swiper/css'
import { SwiperMenu } from '@/widgets/swiper/ui/SwiperMenu.tsx'
import { ReactNode, useEffect, useState } from 'react'
import { Icon28Heart } from '@telegram-apps/telegram-ui/dist/icons/28/heart'
import { Icon28Edit } from '@telegram-apps/telegram-ui/dist/icons/28/edit'
import { Icon28AddCircle } from '@telegram-apps/telegram-ui/dist/icons/28/add_circle'
import { MatchesMenu } from '@/widgets/matches/ui/MatchesMenu.tsx'
import { ProfileMenu } from '@/widgets/profile/ui/ProfileMenu.tsx'
import {Icon24QR} from "@telegram-apps/telegram-ui/dist/icons/24/qr";
import {AdminPanel} from "@/widgets/admin-panel/ui/AdminPanel.tsx";

interface Tab {
  id: number
  text: string
  Icon: ReactNode
  tabContent: ReactNode
}
const tabs: Tab[] = [
  {
    id: 0,
    text: 'Matches',
    Icon: <Icon28Heart />,
    tabContent: <MatchesMenu />,
  },
  {
    id: 1,
    text: 'Recs',
    Icon: <Icon28AddCircle />,
    tabContent: <SwiperMenu />,
  },
  {
    id: 2,
    text: 'Profile',
    Icon: <Icon28Edit />,
    tabContent: <ProfileMenu />,
  },
  {
    id: 3,
    text: 'Admin panel',
    Icon: <Icon24QR />,
    tabContent: <AdminPanel />,
  },
]
export const Main = () => {
  const [currentTab, setCurrentTab] = useState<number>(tabs[1].id)
  const [currentContent, setCurrentContent] = useState<ReactNode>(<></>)

  useEffect(() => {
    setCurrentContent(tabs[currentTab].tabContent)
  }, [currentTab])

  return (
    <div>
      {currentContent}
      <Tabbar>
        {tabs.map(({ id, text, Icon }) => (
          <Tabbar.Item
            key={id}
            text={text}
            selected={id === currentTab}
            onClick={() => setCurrentTab(id)}
          >
            {Icon}
          </Tabbar.Item>
        ))}
      </Tabbar>
    </div>
  )
}
