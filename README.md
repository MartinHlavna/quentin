## Greetings

My name is Quentin Quinn. I am a bot and I would like to help developers to perform faster by providing basic tools and commands that developer often use during programming, like generating different types of hashes, making basic conversions, URL checks and so on. I can even remind you to do something important! In future I would like to be also your favourite Q&A and quick notes provider, or even simplified proxy to your most hated bug or work tracking system (if it has an API of some kind ;)

## Application information

- Quentin uses XMPP protocol for communication
- Quenting is J2SE8, J2EE7 compatible application built as war
- Quentin currently uses only CDI spec so it does not need full J2EE container and can be run from Jetty too
- Quentin is configured using a configuration file. It is expected to be in root of the application server installation directory
- Quentin is written and distributed under GNU GPL v3.0

## Usage information

- create a config file in the root of your application server installation directory. make it readable for user under which your server runs and fill it with:
```
DEBUG_MODE=true
XMPP_HOST=your.xmpp.server.com
XMPP_PORT=5222
XMPP_USER=some.username
XMPP_PASS=topsecretpassword
```
- deploy war to the J2EE container
- Quentin should be accessible on your XMPP server
- to obtain list of all currently implemented commands, type **q:help**
- to obtain man pages of a specific command, type **q:man commandname**
