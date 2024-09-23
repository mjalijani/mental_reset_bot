package echo

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendAudio
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.send.SendVideo
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update

class EchoBot(token: String) : TelegramLongPollingBot(token) {
    private val userChannelMap = mutableMapOf<Long, String>()

    override fun getBotUsername(): String = System.getenv("BOT_USERNAME") ?: "BOT_USERNAME"

    override fun onUpdateReceived(update: Update) {
        val chatId = update.message.chatId
        val receivedMessage = update.message.text
        val userChannelId = userChannelMap[chatId]
        if (update.hasMessage()) {
            if (receivedMessage != null) {
                when {
                    receivedMessage.equals("/start") -> {
                        // نمایش پیام خوش‌آمدگویی
                        val welcomeMessage = """
                        سلام! به بات MentalReset خوش آمدید 🌿

                        این بات به شما کمک می‌کند که محتوای متنی، تصویری و ویدیویی را به راحتی به چنل خود ارسال کنید. لطفاً ابتدا آی‌دی چنل خود را وارد کنید تا بات بتواند در کپشن آیدی چنل شما را اضافه و به چنل شما ارسال کند.

                        برای این کار از دستور زیر استفاده کنید:
                        `/setchannel @YourChannelUsername` یا `/setchannel -1001234567890`

                        سپس هر محتوایی که ارسال کنید (متن، عکس یا ویدیو)، به چنل شما فرستاده خواهد شد.

                        👇 دستورات کاربردی بات:
                        1. `/setchannel [آی‌دی چنل]` - آی‌دی چنل را تنظیم کنید.
                        2. ارسال متن، عکس یا ویدیو - بات محتوای شما را با آی‌دی چنل به چنل‌تان ارسال می‌کند.

                        برای هرگونه سوال یا راهنما، با ارسال `/help` می‌توانید اطلاعات بیشتری دریافت کنید.
                    """.trimIndent()

                        val message = SendMessage(chatId.toString(), welcomeMessage)
                        execute(message)
                    }

                    receivedMessage.equals("/help") -> {
                        // نمایش پیام راهنما
                        val helpMessage = """
                        🔍 راهنمای بات MentalReset

                        برای استفاده از این بات، ابتدا باید آی‌دی چنل خود را با استفاده از دستور زیر ثبت کنید:
                        `/setchannel @YourChannelUsername` یا `/setchannel -1001234567890`

                        پس از ثبت آی‌دی چنل، هر محتوای متنی، تصویری یا ویدیویی که ارسال کنید، به همراه آی‌دی چنل شما به همان چنل مورد نظر ارسال خواهد شد.

                        👨‍💻 دستورات اصلی:
                        - `/setchannel [آی‌دی چنل]`: ثبت یا تغییر آی‌دی چنل برای ارسال محتوا.
                        - ارسال متن، عکس یا ویدیو: بات به طور خودکار محتوای شما را با آی‌دی چنل شما به چنل ارسال می‌کند.

                        ⚠️ توجه: 
                        بات MentalReset باید دسترسی ادمین به چنل داشته باشد تا بتواند محتوا را ارسال کند.
                        
                        برای هر سوالی، می‌توانید با ما تماس بگیرید. 🌟
                    """.trimIndent()

                        val message = SendMessage(chatId.toString(), helpMessage)
                        execute(message)
                    }

                    receivedMessage.equals("/setchannel") -> {
                        sendYourChannelIdMessage(chatId)
                    }

                    receivedMessage.startsWith("@") -> {
                        val channelId = update.message.text
                        userChannelMap[chatId] = channelId
                        channelSaved(chatId)
                    }

                    else -> {
                        val defaultMessage = "دستور نامشخص است. برای راهنما دستور `/help` را ارسال کنید."
                        val message = SendMessage(chatId.toString(), defaultMessage)
                        execute(message)
                    }
                }
            } else{
                // message is text
                if (update.message.hasText()) {
                    if (isChannelAvailable(userChannelId, chatId)) {
                        val sendMessage = SendMessage()
                        sendMessage.chatId = userChannelId!! // ارسال به چنل کاربر
                        sendMessage.text = update.message.text + "\n $userChannelId"

                        execute(sendMessage)
                    }
                }
                //message is audio
                else if (update.message.hasAudio()) {
                    if (isChannelAvailable(userChannelId, chatId)) {
                        val sendAudio = SendAudio()
                        val audio = update.message.audio
                        sendAudio.chatId = userChannelId!!
                        sendAudio.audio = InputFile(audio.fileId)
                        sendAudio.caption = "$userChannelId"
                        execute(sendAudio)
                    }
                }

                // message is photo
                else if (update.message.hasPhoto()) {
                    if (isChannelAvailable(userChannelId, chatId)) {
                        val sendPhoto = SendPhoto()
                        sendPhoto.chatId = userChannelId!! // ارسال به چنل کاربر
                        sendPhoto.photo =
                            InputFile(update.message.photo.last().fileId) // آخرین (بزرگترین) عکس را انتخاب می‌کنیم
                        sendPhoto.caption = " $userChannelId"

                        execute(sendPhoto)
                    }
                }

                // message is video
                else if (update.message.hasVideo()) {
                    if (isChannelAvailable(userChannelId, chatId)) {
                        val sendVideo = SendVideo()
                        sendVideo.chatId = userChannelId!! // ارسال به چنل کاربر
                        sendVideo.video = InputFile(update.message.video.fileId)
                        sendVideo.caption = " $userChannelId"

                        execute(sendVideo)
                    }
                } else {
                    val defaultMessage = "دستور نامشخص است. برای راهنما دستور `/help` را ارسال کنید."
                    val message = SendMessage(chatId.toString(), defaultMessage)
                    execute(message)
                }
            }

        }
    }

    private fun isChannelAvailable(channelId: String?, chatId: Long) =
        if (channelId != null) true
        else {
            sendMessageChannelNotAvailable(chatId)
            false
        }

    private fun sendYourChannelIdMessage(chatId: Long) {
        val channelMessage = SendMessage()
        channelMessage.chatId = chatId.toString()
        channelMessage.text = "آی‌دی چنل خود را با @ وارد کنید."
        execute(channelMessage)
    }

    private fun sendMessageChannelNotAvailable(chatId: Long) {
        val errorMessage = SendMessage()
        errorMessage.chatId = chatId.toString()
        errorMessage.text = "ابتدا آی‌دی چنل خود را با دستور /setchannel وارد کنید."
        execute(errorMessage)
    }

    private fun channelSaved(chatId: Long) {
        val confirmationMessage = SendMessage()
        confirmationMessage.chatId = chatId.toString()
        confirmationMessage.text = "آی‌دی چنل ذخیره شد، از حالا فقط کافیه عکس، فیلم، آهنگ و پیام متنی رو بدون هیچ دستوری برای من بفرستی تا مستقیم تو چنلت انشتار داده بشه برای شروع یه آهنگ برام بفرست :)"
        execute(confirmationMessage)
    }
}


