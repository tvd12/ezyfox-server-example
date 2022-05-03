using System;
using com.tvd12.ezyfoxserver.client.codec;

namespace hello_csharp
{
    public class MsgPackExample
    {
        public void Run()
        {
            MsgPackSimpleSerializer serializer = new MsgPackSimpleSerializer();
            byte[] shortBytes = serializer.serialize(-32768);
            Console.Write("short bytes: ");
            printByteArray(shortBytes);
            byte[] intBytes = serializer.serialize(-2147483648);
            Console.Write("int bytes: ");
            printByteArray(intBytes);
            byte[] longBytes = serializer.serialize(-9223372036854775808);
            Console.Write("long bytes: ");
            printByteArray(longBytes);
        }

        private void printByteArray(byte[] bytes)
        {
            for (int i = 0; i < bytes.Length; ++i)
            {
                Console.Write(bytes[i]);
                if (i < bytes.Length - 1)
                {
                    Console.Write(", ");
                }
            }
            Console.WriteLine();
        }
    }
}
