# Anonmes

## General Information

Anonmes is an email-like web-service with strong focus on security and anonymity. It consists of 2 parts: Java Spring Boot backend and Next.js frontend. Backend implements OAuth 2.0 protocol for Sign-In using user's email and also supports Google Sign-In. More details [here](https://example.com/).

## Local Deployment

### App startup

Inside the repository directory run the following command:
```shell
docker-compose up --detach
```

### App shutdown

#### Regular shutdown
```shell
docker-compose down
```

#### Shutdown with clearing all info
```shell
docker-compose down --volumes
```

