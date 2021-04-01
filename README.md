# Movist App

Movist is a mobile apps to show movies based on category and you can save your favorite movies in local storage.
This apps build using kotlin and use [themoviedb public api](https://developers.themoviedb.org/3)

## Installation

1. Get a free API Key at [themoviedb](https://developers.themoviedb.org/3)
2. Clone the repo
   ```sh
   git clone https://github.com/wahyukharisma/movist-app.git
   ```
3. Enter your API in `graddle.properties`
   ```JS
   API_KEY = 'ENTER YOUR API';
   ```

## Technologies
- Coroutines - Concurrency design pattern that you can use on Android to simplify code that executes asynchronously
- Room - Persistence library provides an abstraction layer over SQLite
- Hilt - Dependency injection
- MVVM - Project architecture

## Resources
- [Api collection](https://www.getpostman.com/collections/32ed28dbe75b25019a99) - api collection in postman that used in this application
- [Mockup](https://www.figma.com/file/yIi865No7CdQwdDcv1J27U/MoList?node-id=0%3A1) - mockup application created using figma
