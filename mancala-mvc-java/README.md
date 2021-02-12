# Mancala MVC Java

Read this readme carefully before you start working on the Mancala MVC assignment.

The project consists of two servers. The front-end uses a nodejs server. It is mainly used to compile your React code into Javascript files during development. This will shorten the feedback loop between changing your code and seeing the results in the browser. The second server is the back-end, which uses a Jetty server. The back-end server allows your Java API to be accessible for other programs, including the front-end server. To prevent [cross-origin request shenanigans (CORS)](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS), all requests from the browser will be sent to the front-end server. That server will then forward to the back-end server if needed.

## Maven

Use Maven to compile/build/run your project

==> [Maven Home](https://maven.apache.org/)

Guide to installing 3rd party JARs

==> [Maven 3rd Party JARs](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html)


## Back-end

The back-end server is defined in the MancalaAPI folder. That folder contains a Maven project defining the service layer of your application. The service layer doesn't contain any business logic, so the game rules are defined in a seperate project. This is usually done via a dependency between different projects. Therefore, the MancalaAPI has a dependency on the MancalaDomain. Make sure you do either of the following:

- Implement the Mancala interface (Mancala.java) in your own Mancala Domain project, or
- Use the MancalaDomainForwardInitializationComplete project provided as a JAR

(both can be found in the root of this repo)

### Using the Mancala interface

Copy the Mancala interface into the right package within your domain project and make a class that implements this interface and calls your domain objects for the specified methods. Then run the command below from within your MancalaDomain project to install the MancalaDomain JAR into your local Maven repository so that the MancalaAPI project can find it:

```bash
mvn clean install
```

If you make a change in the MancalaDomain project rerun this step to make sure the change ends up in your local Maven repository.

### Using the provided JAR

Check the version of your local *maven-install-plugin* with the following command:

```bash
mvn -Dplugin=org.apache.maven.plugins:maven-install-plugin help:describe
```

The version of your local *maven-install-plugin* should be 2.5 or higher. If this is not the case use the link below to download a newer version of this plugin

==> [Maven-Install-Plugin](https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-install-plugin/)

Download both the JAR and the POM file and than use the following command to install this new version:

```bash
mvn install:install-file -Dfile=<path-to-file> -DpomFile=<path-to-pomfile>
```

PS: This wil only install a newer version of the maven-install-plugin to be used on the commandline, and does NOT automatically update Maven to use this new version of the maven-install-plugin (if you want Maven to use this newer version you need to configure Maven to use this newer version, which we won't further discuss here).

Go to the directory where you'll find the MancalaDomainForwardInitializationComplete JAR and run the following command to install this JAR:

```bash
mvn org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:install-file -Dfile=MancalaDomain-1.0.0-SNAPSHOT.jar
```
(this is for version *3.0.0-M1* of the *maven-install-plugin*. You might need to add quotes around the location of the .jar.)

Now the MancalaDomain should be installed in your local Maven repository so that the MancalaAPI project can find it.

### Run the MancalaAPI project

The HTTP port used in this project is defined within the projects POM, so if port 8080 is already in use you can change the portnumber here. From within the MancalaAPI project run the following commands:

```bash
mvn clean package
mvn jetty:run
```

Now that the MancalaAPI project is running you can use an HTTP tool like [Postman](https://www.getpostman.com/) or curl to test it or go to `http://localhost:8080/mancala`.

 ## Front-end

The front-end for this case has been partially set-up. It's a React app that can be found in the `MancalaFrontend` folder.
In the `MancalaFrontend` folder run `npm install` to install all required dependencies.

Running the front-end is as simple as running `npm start` from within the same folder. This starts a server on `localhost:3000`.

Calls to `api/` are forwarded from this server to the Mancala API server (running on `localhost:8080` if you follow the instructions above). If your Mancala API runs on a different port, change the following line in the `webpack.config.js` file:

```json
    proxy: {
        '/api/*': 'http://localhost:8080/mancala/', // <-- change 8080 to a different port if necessary
    }
```

Webpack takes care of compiling the TypeScript code, bundling the output into a single file, etc. A basic configuration is provided, which should take care of most use cases.

Run the command `npm run lint` to see syntax mistakes and common errors.

Tip: Styling of components is handled by Styled Components. Install the `vscode-styled-components` plugin for autocomplete/syntax highlighting.

### POST request to initialize the game

    URL:    http://localhost/mancala/api/players
	Params:	{nameplayer1: "Bla", nameplayer2: "Bliep"}
	        (Body parameters 'application/json' encoded)

### PUT request to play the game

```
URL:    http://localhost/mancala/api/play/{pit_index}
Pit index:

       13 12 11 10  9  8
    14                    7
        1  2  3  4  5  6
```

## Assignment

Make a working Mancala front-end.

- First and foremost, get the application running. It's recommended to use the supplied .jar for this.
- If you start a game, you will see a TODO-screen. Change this screen to a mancala board.
- Make the endpoint that allows a player to make a move when they click on a bowl. 
- Show who wins after a game has been completed.
- Refreshing the page should continue the game.
- Allow for a rematch after completing the game.