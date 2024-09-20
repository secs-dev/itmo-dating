# Documentation

## Design

- [Excalidraw Board][design-excalidraw]

- [Excalidraw Board File][design-excalidraw-file]

### Entity

```java
class Person {
    Long id; // больше 0, должно быть уникальным, автоген
    String first_name; // не может быть пустой
    String last_name; // не может быть пустой
    List<Picture> pictures; // не меньше 1
    Set<Intereset> interests;
    int height; // больше 50, меньше 300
    Optional<LocalDate> birthday;
    Optional<ZodiacSign> zodiac_sign;
    Optional<Faculty> faculty;
    Optional<Location> location;
    OffsetDateTime creationDate; // автоген
}

class Picture {
  Long id; // больше 0, автоген
  Optional<URI> large; // минимум large, medium или small точно установлены
  Optional<URI> medium;
  Optional<URI> small;
}

class Topic {
    Long id; // больше 0, автоген
    String name; // длина от 3 до 32 символов
    Picture icon;
    Color color;
}

class Interest {
    Topic topic;
    int level; // от 1 до 5
}

class Location {
  String name; // от 3 до 128 символов
  Coordinates coordinates;
}

class Coordinates {
    Double latitude; // from −90° at the south pole to 90° at the north pole
    Double longitude; // from 0° to 180° East and 0° to 180° West
}

enum ZodiacSign {
    ARIES, TAURUS, GEMINI, ...;
}

enum Faculty {
    VT, CT, IS;
}
```

### API

#### People Service

```bash
# Here are also other filters and paging
GET /people?first_name="admin"

GET /people/{person-id}

POST /people

PATCH /people/{person-id}

DELETE /people/{person-id}
```

#### Matchmaker Service

```bash
GET /suggestions/{person-id}

POST /attitudes
{
    "target_id": <person-id>,
    "score": <like|skip>
}

GET /matches/{person-id}

# Alternative API just for a teacher

# посчитать число лайков и дизлайков у всех пользователей
GET /statistics/count-liked
{
    "list": [
        { "person_id": { "attitudes": { "like": <N>, "skip": <M> } } }
    ]
}

# лайкнуть или дизлайкнуть человека
POST /partner/{person-id}/attitude/{like|skip}
{}
```

[design-excalidraw]: https://excalidraw.com/#json=wz5D6d2kiOioUYp5yoa1v,wQOnDjOdMOMRvj6yPgqDPA
[design-excalidraw-file]: ./design-v2.excalidraw
