import {useState} from "react";
import {SearchPerson} from "@/entities/person/model/Person.ts";
import {Button, Card, Image, Section} from "@telegram-apps/telegram-ui";
import {Swiper, SwiperSlide} from "swiper/react";
import {Pagination} from "swiper/modules";
import {CardCell} from "@telegram-apps/telegram-ui/dist/components/Blocks/Card/components/CardCell/CardCell";
import {setBackButtonVisible} from "@/entities/back-button";
import {setBackButtonOnClick} from "@/entities/back-button/api/backButtonOnClick.ts";
import {getPeople} from "@/features/get-people/api/getPeople.ts";
import {getPicture} from "@/features/get-picture/api/getPicture.ts";

interface PictureNode {
    name: string,
    base64text: string,
}

export const AdminPanel = () => {
    const [foundPeople, setFoundPeople] = useState<Array<SearchPerson>>([])
    // const [matchesId, setMatchesId] = useState<Array<number>>([])
    const [selectedPerson, setSelectedPerson] = useState<SearchPerson | null>(null)
    const [pictureMap, setPictureMap] = useState<Array<PictureNode>>([])

    function setBackButton() {
        setBackButtonVisible(true)
        const offBack = setBackButtonOnClick(() => {
            setSelectedPerson(null)
            setBackButtonVisible(false)
            offBack()
        })
    }

    return (
        <Section header={'Search people'} style={{ paddingBottom: '20%' }}>
           <Section header={"Parameters"} style={{ paddingBottom: '20%' }}>
               <button style={{background: "0", border: 0}} onClick={() => getPeople(setFoundPeople)}>
                   <Button>Search
           </Button></button>
        </Section>
    <Section header={'Result'} style={{paddingBottom: '20%'}}>
                {!selectedPerson ? (
                    <div key={Math.random()}
                        style={{
                            display: 'grid',
                            gridTemplateColumns: 'repeat(2, 1fr)',
                            gridTemplateRows: 'auto-fill',
                            rowGap: '5px',
                            columnGap: '5px',
                        }}
                    >
                        {foundPeople.map((person) => (
                            <Card
                                key={person.userId}
                                type="ambient"
                                onClick={() => {
                                    setSelectedPerson(person)
                                    setBackButton()
                                }}
                            >
                                <img
                                    src={"https://avatars.githubusercontent.com/u/93886405"}
                                    style={{
                                        width: '100%',
                                        height: '100%',
                                        objectFit: 'cover',
                                    }}
                                />
                                <CardCell
                                    readOnly
                                    style={{
                                        display: 'block',
                                        backgroundColor: 'rgba(0, 0, 0, 0)',
                                    }}
                                >
                                    {person.firstName} {person.lastName}
                                </CardCell>
                            </Card>
                        ))}
                    </div>
                ) : (
                    <Card
                        type="plain"
                        style={{
                            background: 'var(--tgui--secondary_bg_color)',
                            width: '100%',
                        }}
                    >
                        <Swiper slidesPerView={1} rewind={true} modules={[Pagination]}>
                            {selectedPerson.pictures.map((p, index) => {
                                getPicture(selectedPerson.userId, p.id).then((x) => {
                                    setPictureMap((prevState) => {return [...prevState, {name: `photo_${selectedPerson.userId}_${p.id}`, base64text: x }]})
                                })
                                return (
                                <SwiperSlide key={index}>
                                    <img
                                        src={pictureMap.find((value) => value.name === `photo_${selectedPerson.userId}_${p.id}`)?.base64text || ""}
                                        style={{
                                            width: '100%',
                                            height: '100%',
                                            objectFit: 'cover',
                                        }}
                                        alt={`Picture ${p.id}`}
                                    />
                                </SwiperSlide>
                            )})}
                        </Swiper>
                        <CardCell
                            readOnly
                            style={{
                                color: 'var(--tgui--text_color)',
                                display: 'block',
                                textWrap: 'pretty',
                                backgroundColor: 'var(--tgui--bg_color)',
                            }}
                        >
                            <div style={{ textWrap: 'pretty' }}>
                                {selectedPerson.firstName} {selectedPerson.lastName}
                            </div>
                            <div style={{ textWrap: 'pretty' }}>
                                height: {selectedPerson.height}
                            </div>
                            <div style={{ textWrap: 'pretty' }}>
                                location: {JSON.stringify(selectedPerson.location)}
                            </div>
                            <div style={{ textWrap: 'pretty' }}>
                                faculty: {selectedPerson.faculty}
                            </div>
                            <div style={{ textWrap: 'pretty' }}>
                                birthday: {new Date(selectedPerson.birthday).toDateString()}
                            </div>
                            <div style={{ textWrap: 'pretty' }}>
                                lastUpdate: {String(selectedPerson.updateMoment)}
                            </div>
                            <div style={{ textWrap: 'pretty' }}>id: {selectedPerson.userId}</div>
                            <div style={{ textWrap: 'pretty' }}>
                                zodiac sign: {selectedPerson.zodiac}
                            </div>
                            <div style={{ textWrap: 'pretty' }}>
                                interests:{' '}
                                {selectedPerson.interests.map((i) => (
                                    <div key={i.topic.id}>
                                        <div>
                                            <div>
                                                <Image size={20} src={i.topic.icon.small} />
                                                <div>
                                                    {i.topic.id} {i.topic.name} {i.topic.color}
                                                </div>
                                            </div>
                                        </div>
                                        <div style={{ textWrap: 'pretty' }}>level: {i.level}</div>
                                    </div>
                                ))}
                            </div>
                        </CardCell>
                    </Card>
                )}
            </Section>
        </Section>
    )
}