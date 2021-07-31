using System;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Confluent.Kafka;
using MessagePack;
using System.Net;

namespace push_message_api.Controllers
{
    [ApiController]
    public class MessageController : ControllerBase
    {
        private readonly ILogger<MessageController> _logger;
        private readonly IProducer<String, byte[]> _producer;

        public MessageController(ILogger<MessageController> logger)
        {
            _logger = logger;
            var config = new ProducerConfig
            {
                BootstrapServers = "localhost:9092",
                ClientId = Dns.GetHostName()
            };
            _producer = new ProducerBuilder<String, byte[]>(config)
                .SetValueSerializer(new DefaultKafkaSerializer())
                .Build();
        }

        [HttpPost]
        [Route("api/v1/message/push")]
        public Boolean Push(MessagePushModel request)
        {
            byte[] messageBytes = MessagePackSerializer.Serialize(request);
            _producer.Produce(
                "message",
                new Message<String, byte[]> { Key = "push", Value = messageBytes });
            return true;
        }
    }

    public class DefaultKafkaSerializer : ISerializer<byte[]>
    {
        public byte[] Serialize(byte[] data, SerializationContext context)
        {
            return data;
        }
    }
}
