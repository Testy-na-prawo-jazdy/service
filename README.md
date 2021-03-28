# Testy na prawo jazdy

### Objectives
- [x] Parsing the csv document
- [x] Entity Relationship Diagram - basic models
- [X] Spring Security with JWT authentication
- [X] Logic related to generating exams
- [ ] Learning mode
- [ ] Statistics
- [ ] Documentation
- [ ] Deployment to Heroku
- [ ] Mobile application
- [ ] Testing

### Demo account
```
POST /auth/login

Request
{
    "username": "DEMO",
    "password": "DEMO123"
}

Response
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJERU1PIiwiZXhwIjoxNjE2MzQ1NTg3fQ.vqbqrhmQTVCesUNsqcDNyu4w5ahefyIyBdNlPxGg0rXetxIrHTX8ItqCHzfM8hNu1a9oCmKglCfnK4zUW67Syg",
    "refreshToken": "IE25dzAOkPoOKRJf7CWA38tdf3FkkzTUdSgcHIA3ynkx8OHN4vtCNng3shVNb11xMab2au2fZXnHxQykMAmThB3pxTsJY0qDW4JqZNWn0SHaBYjOa3latxdHQZTWvjDd"
}
```

### Endpointy

### Dla zalogowanych

#### Generowanie egzaminu
```
POST /exam/start/{category}

Category { A1, A, A2, B, C, PT, D, T, AM, D1, C1, B1 }

Response
{
    "examId": 34,
    "primaryTaskList": [
        {
            "id": 2,
            "primaryTask": {
                "id": 1479,
                "question": "Czy w przedstawionej sytuacji masz obowiązek zrezygnować z wyprzedzania, jeżeli kierujący pojazdem poprzedzającym włączył prawy kierunkowskaz?",
                "correctAnswer": false,
                "filename": "D26_286org.jpg",
                "points": 3,
                "categories": [
                    {
                        "category": "A"
                    },
                    {
                        "category": "B"
                    },
                    {
                        "category": "C"
                    },
                    {
                        "category": "D"
                    },
                    {
                        "category": "A1"
                    },
                    {
                        "category": "A2"
                    },
                    {
                        "category": "C1"
                    },
                    {
                        "category": "D1"
                    }
                ]
            },
            "correct": false
        }, {...}
    ],
    "specialistTaskList": [
        {
            "id": 22,
            "specialistTask": {
                "id": 10894,
                "question": "Jakie dokumenty masz obowiązek mieć przy sobie i okazywać na żądanie uprawnionego organu podczas kontroli drogowej?",
                "answerA": "Orzeczenie psychologiczne, jeżeli wykonujesz zarobkowy przewóz osób.",
                "answerB": "Pokwitowanie zatrzymania prawa jazdy w okresie, w którym upoważnia ono do kierowania.",
                "answerC": "Orzeczenie lekarskie. ",
                "correctAnswer": "B",
                "filename": "",
                "points": 2,
                "categories": [
                    {
                        "category": "B"
                    }
                ]
            },
            "correct": false
        }, {...}
    ]
}
```

#### Wysyłanie odpowiedzi do egzaminu
```
POST /exam/finish/{id}

Request
{
    "examId": 34,
    "primaryTaskList": [
        {
            "id": 2,
            "chosenAnswer": false
        },
        {...}
    ],
    "specialistTaskList": [
        {
            "id": 22,
            "chosenAnswer": "C"
        },
        {...}
    ]
}

Response
{
    "examId": 34,
    "primaryTaskList": [
        {
            "id": 2,
            "primaryTask": {
                "id": 8284,
                "question": "Czy w tej sytuacji masz obowiązek użyć sygnału dźwiękowego, aby skłonić rowerzystę do szybszej jazdy?",
                "correctAnswer": false,
                "filename": "265_1024x576.jpg",
                "points": 3,
                "categories": [
                    {
                        "category": "B"
                    }
                ]
            },
            "correct": true
        },
        {...}
    ],
    "specialistTaskList": [
        {
            "id": 22,
            "specialistTask": {
                "id": 7237,
                "question": "Z jaką prędkością nie możesz poruszać się w strefie zamieszkania?",
                "answerA": "10 km/h.",
                "answerB": "15 km/h.",
                "answerC": "25 km/h.",
                "correctAnswer": "C",
                "filename": "2A118.jpg",
                "points": 3,
                "categories": [
                    {
                        "category": "B"
                    },
                    {
                        "category": "B1"
                    }
                ]
            },
            "correct": true
        },
        {...}
    ]
}
```

### Dla niezalogowanych

#### Logowanie
```
POST /auth/login

Request
{
    "username": "foo",
    "password": "bar"
}

Response
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE2MTI2NTQ2Mzl9.BhwwnmeKkFVplC3iudqi7pATFxjpV9SEVMFqFa-09WEco4MvuD6MBPOKurFt8y8BpCvNSTr8sYRtxrxFEGS0Eg",
    "refreshToken": "FCglofZA0u6PxqZ29mqiQUvjOslNSVZCnPAqrS1Scg76DWuSSPNyZh412LU8bcUe8dx2OuZpQXMhIM0Q3uhNbnWHVKApeej64YvOP32BZumgI6E9Pt2zqaGrPYL30sUu"
}
```

#### Rejestracja
```
POST /auth/register

Request
{
    "username": "foo",
    "password": "bar",
    "email": "foo@bar"
}

Response
{
    "username": "foo",
    "email": "foo@bar",
    "roles": [
        "ROLE_USER"
    ]
}
```

#### Odświeżanie tokena
```
POST /auth/token/refresh

Request
{
    "refreshToken": "FCglofZA0u6PxqZ29mqiQUvjOslNSVZCnPAqrS1Scg76DWuSSPNyZh412LU8bcUe8dx2OuZpQXMhIM0Q3uhNbnWHVKApeej64YvOP32BZumgI6E9Pt2zqaGrPYL30sUu"
}

Response
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE2MTI2NTQ4MTh9.pH_jt4QTGBqLq49wzWFHBtmYGFt7d4n9vY7QoVIKHRCu3bHNpi9BmsjDn8g6sz2uEU_iizcQc99mfg_a9fvHdQ"
}
```

#### Wylogowywanie (usuwanie refresh tokena)
```
POST /auth/logout

Request
{
    "refreshToken": "FCglofZA0u6PxqZ29mqiQUvjOslNSVZCnPAqrS1Scg76DWuSSPNyZh412LU8bcUe8dx2OuZpQXMhIM0Q3uhNbnWHVKApeej64YvOP32BZumgI6E9Pt2zqaGrPYL30sUu"
}

Response
204 - NO CONTENT
```

#### Whoami [do debugowania]
```
GET /auth/me

Response
{
    "id": 2,
    "email": "foo@bar",
    "username": "foo",
    "password": "$2a$10$Hf97x6iEbzfGLHh2Y3VRpOud5OhgUppnbnKto3sVFV6cHPF5BrCXm",
    "roles": [
        {
            "name": "ROLE_USER"
        }
    ],
    "enabled": true,
    "authorities": [
        {
            "authority": "ROLE_USER"
        }
    ],
    "accountNonExpired": true,
    "credentialsNonExpired": true,
    "accountNonLocked": true
}
```


### Przykładowa obsługa błędów

#### Błędne dane logowania
```
{
    "errorCode": 403,
    "errorName": "FORBIDDEN",
    "errorMessage": "Incorrect login details"
}
```

#### Brak autoryzacji
```
{
    "errorCode": "401",
    "errorName": "UNAUTHORIZED",
    "errorMessage": "Full authentication is required to access this resource"
}
```

#### Brak uprawnień
```
{
    "errorCode": 403,
    "errorName": "FORBIDDEN",
    "errorMessage": "Access denied"
}
```

#### Użytkownik nie istnieje
```
{
    "errorCode": "401",
    "errorName": "UNAUTHORIZED",
    "errorMessage": "User not exist"
}
```

#### Walidacja obiektów
```
{
    "errorCode": 400,
    "errorName": "BAD_REQUEST",
    "errorMessage": "Object validation failed. Check fields errors.",
    "fieldsErrorList": [
        "Password cannot be null",
        "Email cannot be null",
        "Username cannot be null"
    ]
}
```

#### Wygasły token i inne wyjątki JWT [do debugowania]
```
{
    "errorCode": "400",
    "errorName": "BAD_REQUEST",
    "errorMessage": "JWT expired at 2020-07-21T22:06:43Z. Current time: 2020-07-21T23:36:57Z, a difference of 5414657 milliseconds.  Allowed clock skew: 0 milliseconds."
}
```