# MentalReset Telegram Bot

MentalReset is a Telegram bot that allows users to send their text, image, and video content to a specified Telegram channel. Users first register their channel ID, and then the bot forwards the sent content along with the channel ID in the caption to the designated channel.

## Features
- Register users' channel ID
- Send text, images, and videos to the registered channel
- Display a welcome message
- Provide user guidance

## Commands
- `/start`: Display a welcome message to the user.
- `/setchannel [Channel ID]`: Register the channel ID where your content will be sent.  
  **Example:**  
  For public channels: `/setchannel @yourchannelusername`  
  For private channels: `/setchannel -1001234567890`
- `/help`: Display the bot command guide.

## Installation and Setup

### Clone the Project
First, clone or download the code from the GitHub repository:

```bash
git clone https://github.com/mjalijani/mental_reset_bot.git 
```
### Set Up Bot Token
To use the bot, you must obtain your bot token from BotFather. Then, replace the token in the bot code.

``` kotlin
val token = System.getenv("BOT_TOKEN") ?: "BOT_TOKEN"
```
### Run the Bot
After setting the token, you can run the bot using tools like IntelliJ IDEA or any programming environment that supports Kotlin.

``` bash
./gradlew run
```
### Add the Bot to the Channel
To allow the bot to send content to the channel, it must be added as an admin in the specified channel.

## Usage
After running the bot, users will receive a welcome message upon sending the ```/start``` command. They can register their channel ID using the ```/setchannel``` command. Subsequently, by sending text, images, or videos, the bot will forward their content to the designated channel.

## Contribution
If you would like to contribute to the development of this project, feel free to open a pull request or submit your suggestions through an issue on GitHub.

