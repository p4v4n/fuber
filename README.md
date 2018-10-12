# fuber

A call-taxi service.

## Installation

#### Software requirements :
- [Git](https://git-scm.com/)
- [Java](https://java.com/en/download/)
- [Leiningen](https://leiningen.org/)

## Usage

`lein run <PORT-NUMBER>`

ex : Run  `lein run 8000`  and go to`http://localhost:8000`

#### Run Tests:

`lein test`

## API

```
 GET  /cabs
   Get list of all available cabs
 POST /ride
   Book the nearest cab
 POST /end
   End the current ride
```

### GET /cabs

Response:

```
   {
    "cabs": [
        {
            "cab-id": "b75dd3bc-d3be-4f84-8676-882b8c39f2e1",
            "location": {
                "latitude": -42.56817837494313,
                "longitude": 100.05467827897291
            },
            "is-pink": false
        },
        {
            "cab-id": "2189b924-c5c7-44b3-9207-9cceb94f31d7",
            "location": {
                "latitude": 2.868460337921448,
                "longitude": 5.528433418214519
            },
            "is-pink": true
        },
        ....
        ....
        ....
         {
            "cab-id": "43a5fa2b-b98b-429f-95e5-918042fb245a",
            "location": {
                "latitude": 42.78454429047221,
                "longitude": 14.123156397886845
            },
            "is-pink": false
        },
        {
            "cab-id": "54adae13-b1c2-4dd5-89df-b4f373fde287",
            "location": {
                "latitude": 50.48401438824632,
                "longitude": -178.4667250899194
            },
            "is-pink": false
        }
    ]
}

```

### POST /ride

Request:

```
{"user":{"user-id":"5555",
            "location":{"latitude": 10,
                        "longitude": -84},
            "is-hipster":true}}

```

Response:

```
{
    "cab": {
        "cab-id": "15c7d353-1741-4e8d-a0c6-d31eae75cb81",
        "location": {
            "latitude": -0.4946920013342293,
            "longitude": 82.46416132459558
        },
        "is-pink": true
    }
}

```

Failure Responses :

```
Stautus: 404
 "You can't book 2 cabs at the same time"
 "Sorry.No cabs are currently available"
```

### POST /end

Request:

```
{"user":{"user-id":"5555",
         "location":{"latitude": 12,
                     "longitude": -72},
         "is-hipster":true}}
```

Response:

```
{
    "total-distance": 13,
    "total-time": 5.383333333333333,
    "total-amount": 23.38333333333333
}

```

Failure Response:

```
Stautus: 404
 "No active ride to end"
```

## Project Structure

```
+-- project.clj
+-- src/fuber
|   +-- core.clj
		Contains Server,Routing and a few Middlewares.
|   +-- handler.clj
		Contains all the Route Handlers
|   +-- model.clj
		Contains Stateful Data and all the functions touching them directly. 
|   +-- helpers.clj
		Contains various helper functions for generating random data, calculating distances,amount e.t.c
+-- test/fuber
|   +-- core_test.clj
		Contains all the tests
```

## License

Copyright © 2018 p4v4n

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
