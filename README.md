# Anonmes

## General Information

Anonmes is an email-like web-service with strong focus on security and anonymity. It consists of 2 parts: Java Spring Boot backend [messenger] and Next.js frontend [next_chat]. Backend implements OAuth 2.0 protocol for Sign-In using user's email and also supports Google Sign-In. More details [here](https://example.com/).

<img width="800" src="https://github.com/dmytromk/anonymous-messaging/assets/96624185/c53d481e-b0cc-4f7a-b061-c08f3a8e43e4">

### API Documentation

In order to view API Endpoints documentation (parameters, responses, description, etc.) you should launch application in Docker and check out http://localhost:8081/swagger-ui/index.html.

Documentation looks like this:
<img width="800" src="https://github.com/dmytromk/anonymous-messaging/assets/96624185/1a968f35-f20a-4e28-aff5-42157213b3f4">

## Application architecture
Anonmes implements OAuth 2.0 algorithm. Resource Server has only public information about the user, actual authorization is being managed by Authorization Server.

<img width="800" alt="Screenshot 2024-04-28 at 22 06 28" src="https://github.com/dmytromk/anonymous-messaging/assets/96624185/1aa2359d-7ff2-4f49-b177-6791f1b2f1e9">

<img width="800" alt="Screenshot 2024-04-28 at 22 06 28" src="https://github.com/dmytromk/anonymous-messaging/assets/96624185/962f8bd3-1687-4462-b724-f284ec43028c">

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
