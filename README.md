# Multiplatform Kotlin example targeting Javascript on the backend and frontend

This project demonstrates:
 * Sharing runtime independent code between Javascript on both Node.js and in the browser
 * A typed wrapper around the Node.js Express framework 

Requires the following:
* Kotlin 1.2
* JDK 8+ installed
* NPM installed
* Node.js installed
* Gradle installed

## Structure
Multi-module Gradle project.

* ``common`` - common module, shared Kotlin source code, platform independent code
* ``common-js`` - JavaScript runtimes platform dependent code
* ``backend-js`` - Express application transpiled to Node.js JavaScript
* ``frontend-js`` - application transpiled for frontend JavaScript, packed in [WebPack](https://webpack.js.org/), 
   it's only statically served by Node.js
  
##  Compiling
From the root of repo run: 
```bash
gradle build
```
 or change directories to only build a specific subproject, eg.
```bash
cd backend
gradle build
```

### Creating the Express app
```kt
import pappel.Application

val shared = SharedClass(Console(), Math())
val app = Application()

app.get("/primes") { _, _ ->
    shared.platform = "Node.js"
    shared.printMe()
    println(shared.givePrimes(100))
}

app.startHttpServer(3000)
val path = require("path")

val staticWebContentPath = path.join(__dirname, "../frontend-js/src/main/web")
println("Serving content from: $staticWebContentPath")
app.serveStaticContent(staticWebContentPath)

println("Kotlin - Node.js webserver deployed and ready.")
```

## Platform implementation specifics
* prime number calculation is platform independent, single code shared for all platforms 
* text output on screen is platform dependent 
    * **Frontend JavaScript** - it adds elements in the DOM of HTML page to display primes
    * **Node.js JavaScript** - uses `res.send()` to send primes as text in response to a web request

### Type safe use of Express in Node.js

##### Simple HTTP GET call using async - await pattern inside an Express route handler. (Uses a coroutine wrapper of native JS `Promise`s)
```kt
app.get("/async-get") { _, res ->
    println("Async get route pinged!")

    async {
        val resp: Response = await { fetch("https://jsonplaceholder.typicode.com/todos/1") }
        val data: Any? = await { resp.json() }

        data?.also { console.dir(data) }
        res.send(JSON.stringify(data))
    }
}
```
##### HTTP POST call using async - await pattern inside an Express route handler. 
Message body is passed as a Kotlin data class object
Headers are passed as a Map<String, String>
(Uses a coroutine wrapper of native JS `Promise`s)
```kt
app.get("/async-post") { _, res ->
    println("async-post-data route pinging")

    val wade = "{\"name\":\"Wade Jensen\", \"age\": 22, \"address\": {\"streetNum\": 123, \"streetName\": \"Fake street\", \"suburb\": \"Surry Hills\", \"postcode\": 2010}}"
    val person: Person = JSON.parse<Person>(wade)
    async {
        val request = RequestInit(
            method  = Method.POST,
            headers = mapOf("username" to "wjensen", "password" to "1234567"),
            body    = person)

        println("Request object:")
        console.dir(request)

        val resp = await { fetch("https://jsonplaceholder.typicode.com/posts", request) }
        val data: Any? = await { resp.json() }
        data?.also {
            println("Response object:")
            console.dir(data)
        }
        res.send(JSON.stringify(data))
    }
}
```

##### Parse JSON strings into Kotlin objects inside an Express route handler
```
app.get("/parse-json") { _, res ->

    val data = "{\"name\":\"Wade Jensen\", \"age\": 22, \"address\": {\"streetNum\": 123, \"streetName\": \"Fake street\", \"suburb\": \"Surry Hills\", \"postcode\": 2010}}"
    println(data)
    val person: Person = JSON.parse<Person>(data)
    res.send("""
        name    = ${person.name},
        age     = ${person.age},

        address.streetNum  = ${person.address.streetNum},
        address.streetName = ${person.address.streetName},
        address.suburb     = ${person.address.suburb},
        address.postcode   = ${person.address.postcode}
    """.trimIndent())
}
```
##### Simple HTTP GET call handled with native JS `Promise`s inside an Express route handler
```kt
app.get("/promise-get") { _, res ->
    println("promise-get route pinged!")

    val resp: Promise<Response> = fetch("https://jsonplaceholder.typicode.com/todos/1")
    resp
        .then { result: Response -> result.json() }
        .then { json -> JSON.stringify(json) }
        .then { strResult ->
            println(strResult)
            res.send(strResult)
        }
}
```

### Hot reload
To automatically recompile on save to filesystem events:
 
 Install Nodemon (wrapper around `node` which watches for flushes to the filesystem and restarts the node server)
 ```bash
 npm install -g nodemon
 ```

 Open two terminals and run the following:

```bash
/home/wjensen/repos/kotlin-nodejs-example/backend-js > gradle --continuous build
Continuous build is an incubating feature.

BUILD SUCCESSFUL in 10s
10 actionable tasks: 10 up-to-date

Waiting for changes to input files of tasks... (ctrl-d then enter to exit)
new file: /home/wjensen/repos/kotlin-nodejs-example/backend/src/main/kotlin/com/wadejensen/example/main.kt
Change detected, executing build...

BUILD SUCCESSFUL in 18s
10 actionable tasks: 3 executed, 7 up-to-date
```

```bash
/home/wjensen/repos/kotlin-nodejs-example/backend-js > nodemon app.js
[nodemon] 1.18.4
[nodemon] to restart at any time, enter `rs`
[nodemon] watching: *.*
[nodemon] starting `node app.js`
Starting server on port 3000.
Server started successfully
Serving content from: /home/wjensen/repos/kotlin-nodejs-example/frontend-js/src/main/web
Kotlin - Node.js webserver deployed and ready.
[nodemon] restarting due to changes...
[nodemon] restarting due to changes...
[nodemon] starting `node app.js`
Starting server on port 3000.
Server started successfully
Serving content from: /home/wjensen/repos/kotlin-nodejs-example/frontend-js/src/main/web
Kotlin - Node.js webserver ready.
```

The example project was built based on the ideas of (copied blatantly from) several example projects, including:
* https://github.com/techprd/kotlin_node_js_seed (Something using Kotlin JS and express that was simple enough for 
  me to get my head around)
* https://github.com/wojta/hello-kotlin-multiplatform (A super-set of this repo, which includes JVM and android targets
  except the Node example is a console app, rather than an express webserver)
* https://github.com/blazer82/pappel-framework (An experimental Node.js framework for Kotlin and React which 
  wraps express to make it Kotlin-friendly)
