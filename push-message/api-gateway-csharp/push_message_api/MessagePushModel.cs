using System;
using MessagePack;

namespace push_message_api
{
    [MessagePackObject]
    public class MessagePushModel
    {
        [Key("username")]
        public String username { get; set; }

        [Key("data")]
        public MessagePushData data { get; set; }
    }

    [MessagePackObject]
    public class MessagePushData
    {
        [Key("title")]
        public String title { get; set; }

        [Key("content")]
        public String content { get; set; }
    }
}
